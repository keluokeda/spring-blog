package com.ke.springblog

object Consts {
	const val ERROR_CODE_NOT_LOGIN = -1
	const val ERROR_CODE_LOGIN_FAILED = -2
	const val ERROR_CODE_RESOURCE_NOT_FOUND = -3
	const val ERROR_CODE_RESOURCE_DUPLICATE = -4

	const val TOKEN_KEY = "Authorization"
	const val HEADER_USER_ID="userId"

	fun userNotFoundErrorMessage(userId: Long) = "id为$userId 的用户不存在"

	fun postNotFoundErrorMessage(postId: Long) = "id为$postId 的文章不存在"

}