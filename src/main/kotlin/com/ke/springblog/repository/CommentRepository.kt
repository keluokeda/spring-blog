package com.ke.springblog.repository

import com.ke.springblog.model.table.Comment
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
	fun findByPostId(postId: Long?, pageable: Pageable): Page<Comment>


	fun countByUserId(userId: Long): Int


	fun countByPostId(postId: Long): Int


	@Query(value = "select c.user_id from comments as c where c.post_id = ?1 GROUP BY c.user_id", nativeQuery = true)
	fun findUserIdsByPostId(postId: Long): List<Long>

}