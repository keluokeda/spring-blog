package com.ke.springblog.service

import com.ke.springblog.Consts
import com.ke.springblog.exception.IllegalAccessResourceException
import com.ke.springblog.exception.ResourceNotFoundException
import com.ke.springblog.model.response.PostResponse
import com.ke.springblog.model.table.Post
import com.ke.springblog.model.table.PostImage
import com.ke.springblog.model.table.Tag
import com.ke.springblog.repository.PostRepository
import com.ke.springblog.repository.UserRepository
import com.ke.springblog.toJsonListResponse
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


//加了事务之后 出现异常数据可以回滚
@Transactional
@Service
class PostService(private val postRepository: PostRepository, private val userRepository: UserRepository, private val commentService: CommentService, private val redisService: RedisService) {

	private val logger = LoggerFactory.getLogger(PostService::class.java)


	/**
	 * 发布新的文章
	 */
	fun newPost(userId: Long, title: String, content: String, tags: List<String>?, images: List<String>?) {
		val user = userRepository.findById(userId)
				.orElseThrow({ ResourceNotFoundException(Consts.userNotFoundErrorMessage(userId)) })

		val post = Post(
				title = title,
				content = content,
				user = user
		)
		if (tags != null) {
			post.tags = tags.map { Tag(name = it, post = post) }.toSet()
		}
		if (images != null) {
			post.images = images.map { PostImage(imageUrl = it, post = post) }.toHashSet()
		}

		postRepository.save(post)
	}

	fun getAllPosts(pageable: Pageable, userId: Long): Map<String, Any?> {
		val page = postRepository.findAllPost(userId, pageable).map {
			it.commentCount = redisService.getPostCommentCount(it.postId)
			it.userCommentCount = redisService.getUserCommentCount(it.userId)
			return@map it

		}.map { return@map PostResponse.convert(it) }

		return page.toList().toJsonListResponse(last = page.isLast)

//		return postRepository.findAll(pageable).toJsonResponse()
	}

	fun getUserPosts(pageable: Pageable, userId: Long, targetUserId: Long): Any {
		val page = postRepository.findUserPosts(userId, targetUserId, pageable).map { return@map PostResponse.convert(it) }
		return page.toList().toJsonListResponse(last = page.isLast)
	}

	/**
	 * 删除文章
	 */
	@CacheEvict(cacheNames = ["comment_count_by_post"], key = "#postId")
	fun deletePost(postId: Long, userId: Long) {
		val post = postRepository.findById(postId).orElseThrow({ ResourceNotFoundException(Consts.postNotFoundErrorMessage(postId)) })

		if (post.user.id != userId) {
			throw IllegalAccessResourceException("不能操作id为$postId 的文章")
		}

		commentService.onPostDelete(postId)


		postRepository.delete(post)

	}
}