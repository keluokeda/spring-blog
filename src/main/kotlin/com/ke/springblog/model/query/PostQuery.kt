package com.ke.springblog.model.query

import java.util.*

class PostQuery(
		val postId: Long,
		val postTitle: String,
		val postContent: String,
		val postCreateDate: Date,
		val userId: Long,
		val userName: String,
		val userIconUrl: String?,
		val userDescription: String?,
		val attentionUser: Long
) {
	var commentCount: Int = 0
	var fansCount: Int = 0
	var followCount: Int = 0
	var postCount: Int = 0
	var userCommentCount: Int = 0

}