package com.ke.springblog.exception

/**
 * 找不到指定的资源
 */
//不能直接继承 Exception
class ResourceNotFoundException(override val message: String) : RuntimeException(message)

