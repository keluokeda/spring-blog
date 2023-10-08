package com.ke.springblog.exception

/**
 * 非法资源访问
 */
class IllegalAccessResourceException(override val message: String) : RuntimeException(message)