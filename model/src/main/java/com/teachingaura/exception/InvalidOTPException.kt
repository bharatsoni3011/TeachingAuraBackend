package com.teachingaura.exception

class InvalidOTPException : Exception {
    constructor(message: String?) : super(message) {}
    constructor(message: String?, e: Throwable?) : super(message, e) {}
}