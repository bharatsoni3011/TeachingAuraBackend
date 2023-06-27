package com.teachingaura.roles

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('SUPER') or (hasRole('TEACHER') and principal.username == #teacherId)")
annotation class TeacherOnly
