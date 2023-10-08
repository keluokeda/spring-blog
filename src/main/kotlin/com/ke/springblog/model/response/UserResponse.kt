package com.ke.springblog.model.response

class UserResponse(
		val userId: Long,
		val userName: String,
		val userIcon: String,
		val userDescription: String,
		val commentCount: Int,
		val postCount:Int,
		val fansCount:Int,
		val followsCount:Int


) {
	val follow: Boolean = false
}