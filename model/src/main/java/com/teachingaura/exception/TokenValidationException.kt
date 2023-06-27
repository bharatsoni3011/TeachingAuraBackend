package com.teachingaura.exception

class TokenValidationException : Exception {
    constructor(message: String?) : super(message) {}
    constructor(message: String?, e: Throwable?) : super(message, e) {}
}