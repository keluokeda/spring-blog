package com.ke.springblog.repository

import com.ke.springblog.model.table.User
import com.ke.springblog.model.table.UserRelationship
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRelationshipRepository : JpaRepository<UserRelationship, Long> {
	/**
	 * 获取一个用户的粉丝
	 */
	fun findAllByToUser(toUser: User, pageable: Pageable): List<UserRelationship>

	/**
	 * 获得一个用户关注的人
	 */
	fun findAllByFromUser(fromUser: User, pageable: Pageable): List<UserRelationship>

	/**
	 * 判断是否已经关注
	 */
	fun findByFromUserAndToUser(fromUser: User, toUser: User): Optional<UserRelationship>

	/**
	 * 获取用户的粉丝总数
	 */
	fun countByToUserId(toUserId: Long): Int

	/**
	 * 获取用户关注的人的总数
	 */
	fun countByFromUserId(fromUserId: Long): Int
}