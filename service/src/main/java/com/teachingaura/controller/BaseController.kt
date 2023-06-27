package com.teachingaura.controller

import com.teachingaura.api.CustomErrorType
import com.teachingaura.exception.InstituteAlreadyExistsException
import com.teachingaura.exception.InstituteDoesNotExistsException
import com.teachingaura.exception.InvalidParameterValueException
import com.teachingaura.exception.UserAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler


open class BaseController {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserException(exception: Exception): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(CustomErrorType(listOf("Unable to create. User already exists.")))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(exception: Exception): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(CustomErrorType(listOf("Bad request")))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class, InvalidParameterValueException::class)
    fun handleDataException(exception: Exception): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(CustomErrorType(listOf("Request body parameters are incorrect")))
    }

    @ExceptionHandler(InstituteAlreadyExistsException::class)
    fun handleInstitueException(exception: Exception): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(CustomErrorType(listOf("Unable to create. Institute already exists.")))
    }

    @ExceptionHandler(InstituteDoesNotExistsException::class)
    fun handleDoesNotInstitueException(exception: Exception): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(CustomErrorType(listOf("Institute does not exists.")))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<*> {
        //val errors = mutableMapOf<String, String>()
        //val errors = mutableListOf<String>()
        val errors =  exception.bindingResult.allErrors.map { error ->
            error.getDefaultMessage()
        }
        return ResponseEntity(CustomErrorType(errors), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(exception: AccessDeniedException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(CustomErrorType(listOf("Access Denied")))
    }
}