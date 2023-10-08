package com.ke.springblog.model.response

import com.ke.springblog.model.query.PostQuery
import com.ke.springblog.util.DateUtil
import java.util.*

data class PostResponse(
		val postId: Long,
		val postTitle: String,
		val postContent: String,
		val postCreateTime: String,
		val postCommentCount: Int,
		val userId: Long,
		val userName: String,
		val userIconUrl: String,
		val userDescription: String,
		val userFansCount: Int,
		val userFollowCount: Int,
		val userPostCount: Int,
		val userCommentCount: Int,
		val attentionUser: Boolean
) {
	companion object {
		fun convert(postQuery: PostQuery) = PostResponse(
				postId = postQuery.postId,
				postTitle = postQuery.postTitle,
				postContent = postQuery.postContent,
				postCreateTime = DateUtil.dateformatTime(postQuery.postCreateDate),
				postCommentCount = postQuery.commentCount.toInt(),
				userId = postQuery.userId,
				userName = postQuery.userName,
				userIconUrl = postQuery.userIconUrl?:"",
				userDescription = postQuery.userDescription?:"",
				userFansCount = postQuery.fansCount,
				userFollowCount = postQuery.followCount,
				userPostCount = postQuery.postCount,
				userCommentCount = postQuery.userCommentCount,
				attentionUser = postQuery.attentionUser == 1L

		)
	}
}