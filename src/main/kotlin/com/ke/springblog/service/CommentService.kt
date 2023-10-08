package com.ke.springblog.service

import com.ke.springblog.Consts
import com.ke.springblog.exception.ResourceNotFoundException
import com.ke.springblog.model.response.CommentResponse
import com.ke.springblog.model.table.Comment
import com.ke.springblog.repository.CommentRepository
import com.ke.springblog.repository.PostRepository
import com.ke.springblog.repository.UserRepository
import com.ke.springblog.util.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional
@Service
class CommentService(private val commentRepository: CommentRepository, private val userRepository: UserRepository, private val postRepository: PostRepository, private val redisService: RedisService) {

	private val logger = LoggerFactory.getLogger(CommentService::class.java)!!

	/**
	 * 新建评论
	 */
	fun newComment(userId: Long, postId: Long, content: String) {
		val user = userRepository.findById(userId).orElseThrow({ ResourceNotFoundException(Consts.userNotFoundErrorMessage(userId)) })

		val post = postRepository.findById(postId).orElseThrow({ ResourceNotFoundException(Consts.postNotFoundErrorMessage(postId)) })
		val comment = Comment(
				content = content
		)
		comment.user = user
		comment.post = post

		commentRepository.save(comment)


		redisService.updatePostCommentCount(postId, redisService.getPostCommentCount(postId) + 1)
		redisService.updateUserCommentCount(userId, redisService.getUserCommentCount(userId) + 1)

	}


	fun getComments(postId: Long, pageable: Pageable) = commentRepository.findByPostId(postId, pageable).map {

		return@map CommentResponse(
				commentId = it.id,
				content = it.content,
				userId = it.user!!.id,
				userName = it.user!!.userName,
				userDescription = it.user!!.description!!,
				userIconUrl = it.user!!.iconUrl!!,
				userCommentCount = 0,
				userPostCount = 0,
				userFansCount = 0,
				userFollowsCount = 0,
				attentionUser = false,
				createTime = DateUtil.dateformatTime(it.updatedAt)
		)
	}.toList()


	fun onPostDelete(postId: Long) {
		val list = commentRepository.findUserIdsByPostId(postId)
		logger.info("id为$postId 的文章下包含以下人的评论 $list")

		list.forEach { redisService.clearUserCommentCountCache(it) }

	}


}