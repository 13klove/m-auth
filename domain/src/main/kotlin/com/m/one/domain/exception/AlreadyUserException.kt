package com.m.one.domain.exception

class AlreadyUserException: RuntimeException {

    constructor(msg: String): super(msg)
    constructor(msg: String, cause: Throwable): super(msg, cause)

}