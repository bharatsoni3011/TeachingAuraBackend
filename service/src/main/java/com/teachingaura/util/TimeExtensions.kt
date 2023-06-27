package com.teachingaura.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

val Long.hours: Long
    get() = this * 60 * minutes

val Long.minutes: Long
    get() = this * 60 * seconds

val Long.seconds: Long
    get() = this * 1000

val Int.hours: Int
    get() = this * 60 * minutes

val Int.minutes: Int
    get() = this * 60 * seconds

val Int.seconds: Int
    get() = this * 1000

fun LocalDateTime.toMillis(): Long {
    return this.toEpochSecond(ZoneOffset.UTC) * 1000
}

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)
}