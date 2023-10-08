package com.ke.springblog.exception

import com.ke.springblog.Consts
import com.ke.springblog.toFailedJsonResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice(annotations = [(RestController::class)])
class ExceptionHandler {


	private val logger = LoggerFactory.getLogger(com.ke.springblog.exception.ExceptionHandler::class.java)
	/**
	 * 拦截不能处理的异常
	 */
	@ResponseBody
	@ExceptionHandler
	fun handleException(exception: Exception): Map<String, Any?> {
		logger.info("get error", exception)

		return (exception.message ?: "").toFailedJsonResponse()
	}

	/**
	 * 找不到指定的资源
	 */
	@ResponseBody
	@ExceptionHandler(ResourceNotFoundException::class)
	fun handleResourceNotFoundException(exception: ResourceNotFoundException): Map<String, Any?> {

		return exception.message.toFailedJsonResponse(Consts.ERROR_CODE_RESOURCE_NOT_FOUND)

	}

	/**
	 * 资源重复
	 */
	@ResponseBody
	@ExceptionHandler(ResourceDuplicateException::class)
	fun handleResourceDuplicateException(exception: ResourceDuplicateException): Map<String, Any?> {
		return exception.message.toFailedJsonResponse(Consts.ERROR_CODE_RESOURCE_DUPLICATE)
	}

	/**
	 * 非法资源访问
	 */
	@ResponseBody
	@ExceptionHandler(IllegalAccessResourceException::class)
	fun handleIllegalAccessResourceException(exception: IllegalAccessResourceException): Any {
		return exception.message.toFailedJsonResponse()
	}
}



