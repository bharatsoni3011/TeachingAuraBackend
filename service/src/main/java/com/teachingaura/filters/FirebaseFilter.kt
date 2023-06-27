package com.teachingaura.filters

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import com.teachingaura.contexts.FirebaseUserInfo
import com.teachingaura.contexts.UserContext
import com.teachingaura.roles.Role
import com.teachingaura.roles.RoleService
import com.teachingaura.service.InstituteService
import com.teachingaura.service.StudentService
import com.teachingaura.service.TeacherService
import com.teachingaura.spring.configuration.SecurityProperties
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.inject.Inject
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class FirebaseFilter @Inject constructor(
    val securityProperties: SecurityProperties,
    val roleService: RoleService,
    val firebaseProvider: FirebaseProvider,
    val studentService: StudentService,
    val teacherService: TeacherService,
    val instituteService: InstituteService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (verifyToken(request)) {
            filterChain.doFilter(request, response)
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access token invalid.")
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val matcher = AntPathMatcher()
        return ignoredUrls.stream().anyMatch { p -> matcher.match(p, request.servletPath) }
    }

    private fun verifyToken(request: HttpServletRequest): Boolean {
        val decodedToken: FirebaseToken
        val token = getBearerToken(request)
        try {
            if (token != null && !token.equals("undefined", ignoreCase = true)) {
                decodedToken = firebaseProvider.getFirebase().verifyIdToken(token)
                passUID(decodedToken)
                setUserContext(decodedToken, token, request)
                return true
            }
        } catch (e: FirebaseAuthException) {
            log.error(e) { "Firebase Exception:: ${e.localizedMessage}" }
        }
        return false
    }

    private fun setUserContext(decodedToken: FirebaseToken, token: String, request: HttpServletRequest) {
        val authorities = mutableListOf<GrantedAuthority>()
        if (securityProperties.superAdmins.contains(decodedToken.email)) {
            if (!decodedToken.claims.containsKey(Role.ROLE_SUPER.toString())) {
                try {
                    roleService.addRoleToUser(decodedToken.uid, Role.ROLE_SUPER)
                } catch (e: Exception) {
                }
            }
            authorities.add(SimpleGrantedAuthority(Role.ROLE_SUPER.toString()))
        } else {
            if (decodedToken.claims.containsKey(Role.ROLE_STUDENT.toString())
                    .or(decodedToken.claims.containsKey(Role.ROLE_TEACHER.toString()))
                    .or(decodedToken.claims.containsKey(Role.ROLE_INSTITUTE.toString()))
                    .or(decodedToken.claims.containsKey(Role.ROLE_SUPER.toString()))
            ) {
                authorities.addAll(decodedToken.claims.map { (k, _) -> SimpleGrantedAuthority(k) })
            } else {
                // if by any reason role is not set, add it here :)
                logger.error("Cant find any role for uid : ${decodedToken.uid}")
                runBlocking {
                    try {
                        instituteService.getInstituteById(decodedToken.uid)
                        roleService.addRoleToUser(decodedToken.uid, Role.ROLE_INSTITUTE)
                        authorities.add(SimpleGrantedAuthority(Role.ROLE_INSTITUTE.toString()))
                    } catch(e:Exception) {

                    }
                    try {
                        teacherService.getTeacherById(decodedToken.uid)
                        roleService.addRoleToUser(decodedToken.uid, Role.ROLE_TEACHER)
                        authorities.add(SimpleGrantedAuthority(Role.ROLE_TEACHER.toString()))
                    } catch(e:Exception) {
                    }
                    try {
                        studentService.getStudent(decodedToken.uid)
                        roleService.addRoleToUser(decodedToken.uid, Role.ROLE_STUDENT)
                        authorities.add(SimpleGrantedAuthority(Role.ROLE_STUDENT.toString()))
                    } catch(e:Exception) {
                    }
                }
            }
        }
        logger.info("Roles for current request are : $authorities for user : ${decodedToken.uid}")
        val user = org.springframework.security.core.userdetails.User(decodedToken.uid, decodedToken.uid, authorities)
        val authentication = UsernamePasswordAuthenticationToken(
            user,
            Credentials(CredentialType.ID_TOKEN, decodedToken, token), authorities
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun getBearerToken(request: HttpServletRequest): String? {
        var bearerToken: String? = null
        val authorization = request.getHeader("Authorization")
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7)
        }
        return bearerToken
    }

    private fun passUID(decodedToken: FirebaseToken) {
        val info = FirebaseUserInfo(decodedToken.uid)
        UserContext.setInfo(info)
    }

    companion object {
        private val log = KotlinLogging.logger {}
        private val ignoredUrls: List<String> = listOf(
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/swagger-config/**",
            "/swagger-config",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/h2"
        )
    }
}
