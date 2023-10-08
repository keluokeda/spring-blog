package com.ke.springblog.exception

/**
 * 资源重复
 */
class ResourceDuplicateException(override val message: String) : RuntimeException(message)

