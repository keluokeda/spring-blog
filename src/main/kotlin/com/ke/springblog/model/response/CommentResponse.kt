package com.ke.springblog.model.response

data class CommentResponse(
		val commentId: Long,
		val content: String,
		val createTime: String,
		val userId: Long,
		val userName: String,
		val userIconUrl: String,
		val userDescription: String,
		val userFansCount: Int,
		val userFollowsCount: Int,
		val userCommentCount: Int,
		val userPostCount: Int,
		val attentionUser: Boolean
)