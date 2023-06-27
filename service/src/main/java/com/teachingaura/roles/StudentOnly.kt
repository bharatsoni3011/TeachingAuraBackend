package com.teachingaura.roles

import org.springframework.security.access.prepost.PreAuthorize


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('SUPER') or (hasRole('STUDENT') and principal.username == #studentId)")
annotation class StudentOnly
