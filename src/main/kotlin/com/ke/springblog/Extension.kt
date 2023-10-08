package com.ke.springblog

fun Any.toJsonResponse(errorCode: Int = 0, errorMessage: String? = null): Map<String, Any?> {
	val responseMap = mutableMapOf<String, Any?>()
	responseMap["errorCode"] = errorCode
	responseMap["errorMessage"] = errorMessage
	responseMap["body"] = this
	return responseMap
}

fun String.toFailedJsonResponse(errorCode: Int = -1): Map<String, Any?> {
	val responseMap = mutableMapOf<String, Any?>()
	responseMap["errorCode"] = errorCode
	responseMap["errorMessage"] = this
	responseMap["body"] = null
	return responseMap
}

fun String.toSuccessJsonResponse(): Map<String, Any?> {
	val responseMap = mutableMapOf<String, Any?>()
	responseMap["errorCode"] = 0
	responseMap["errorMessage"] = this
	responseMap["body"] = null
	return responseMap
}

fun List<Any>.toJsonListResponse(errorCode: Int = 0, errorMessage: String? = null, last: Boolean = false): Map<String, Any?> {
	val responseMap = mutableMapOf<String, Any?>()
	responseMap["errorCode"] = errorCode
	responseMap["errorMessage"] = errorMessage
	responseMap["body"] = this
	responseMap["last"] = last
	return responseMap
}