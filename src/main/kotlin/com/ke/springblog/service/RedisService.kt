package com.ke.springblog.service

import com.ke.springblog.repository.CommentRepository
import com.ke.springblog.repository.PostRepository
import com.ke.springblog.repository.UserRelationshipRepository
import com.ke.springblog.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class RedisService(private val commentRepository: CommentRepository, private val postRepository: PostRepository, private val userRelationshipRepository: UserRelationshipRepository, private val userRepository: UserRepository) {

	private val logger = LoggerFactory.getLogger(RedisService::class.java)!!


	/**
	 * 获取某个用户的评论数量
	 */
	@Cacheable(cacheNames = ["comment_count_by_user"], key = "#userId")
	fun getUserCommentCount(userId: Long): Int {
		//建议分开缓存

		logger.info("获取用户id为$userId 的评论总数")

		return commentRepository.countByUserId(userId)
	}


	/**
	 * 更新某个用户的评论总数
	 */
	@CachePut(cacheNames = ["comment_count_by_user"], key = "#userId")
	fun updateUserCommentCount(userId: Long, newCount: Int): Int {
		logger.info("更新id为$userId 的用户评论总数 $newCount")
		return newCount
	}

	/**
	 * 更新某个文章的评论总数
	 */
	@CachePut(cacheNames = ["comment_count_by_post"], key = "#postId")
	fun updatePostCommentCount(postId: Long, newCount: Int): Int {
		logger.info("更新id为$postId 的文章评论总数 $newCount")
		return newCount
	}

	/**
	 * 获取某个文章的评论数量
	 */
	@Cacheable(cacheNames = ["comment_count_by_post"], key = "#postId")
	fun getPostCommentCount(postId: Long): Int {
		logger.info("获取文章id为$postId 的评论总数")

		return commentRepository.countByPostId(postId)
	}

	@CacheEvict(cacheNames = ["comment_count_by_user"], key = "#userId")
	fun clearUserCommentCountCache(userId: Long) {
	}

	/**
	 * 获取用户发布的文章总数
	 */
	@Cacheable(cacheNames = ["post_count_by_user"], key = "#userId")
	fun getUserPostCount(userId: Long): Int {
		return postRepository.countByUserId(userId)
	}




}