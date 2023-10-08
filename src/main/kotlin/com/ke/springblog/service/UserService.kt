package com.ke.springblog.service

import com.ke.springblog.Consts
import com.ke.springblog.exception.LoginFailedException
import com.ke.springblog.exception.ResourceDuplicateException
import com.ke.springblog.exception.ResourceNotFoundException
import com.ke.springblog.model.response.UserResponse
import com.ke.springblog.model.table.User
import com.ke.springblog.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(private val userRepository: UserRepository, private val bCryptPasswordEncoder: BCryptPasswordEncoder,private val redisService: RedisService,private val userRelationshipService: UserRelationshipService) {

	/**
	 * 注册用户
	 */
	fun saveUser(account: String, password: String, userName: String, iconUrl: String?, description: String?) {
		if (userRepository.existsByAccount(account)) {
			throw ResourceDuplicateException("$account 已经被注册了")
		}

		val user = User(
				account = account,
				password = bCryptPasswordEncoder.encode(password),
				userName = userName,
				iconUrl = iconUrl,
				description = description
		)


		userRepository.save(user)
	}

	fun getAllUser(pageable: Pageable) = userRepository.findAll(pageable).toList()


	/**
	 * 验证用户
	 * @return 登录成功返回用户id
	 */
	fun validateAccountPassword(account: String, password: String): Long {
		return userRepository.findByAccount(account).map {
			if (!bCryptPasswordEncoder.matches(password, it.password)) {
				throw LoginFailedException("账号或密码错误")
			}
			return@map it
		}.orElseThrow({ LoginFailedException("账号或密码错误") }).id

	}


	fun updateUser(userName: String, description: String?, userIcon: String?, userId: Long) {
		val user = userRepository.findById(userId).orElseThrow({ ResourceNotFoundException(Consts.userNotFoundErrorMessage(userId)) })

		val updateUser = User(
				id = user.id,
				userName = userName,
				description = description ?: user.description,
				iconUrl = userIcon ?: user.iconUrl,
				account = user.account,
				password = user.password
		)

		userRepository.save(updateUser)
	}

	fun getUserInfo(targetUserId: Long, currentUserId: Long): UserResponse {
		val user = userRepository.findById(targetUserId).orElseThrow({ ResourceNotFoundException(Consts.userNotFoundErrorMessage(targetUserId)) })
		return UserResponse(
				userId = user.id,
				userName = user.userName,
				userDescription = user.description ?: "",
				userIcon = user.iconUrl ?: "",
				commentCount = redisService.getUserCommentCount(targetUserId),
				postCount = redisService.getUserPostCount(targetUserId),
				fansCount = userRelationshipService.getUserFansCount(targetUserId),
				followsCount = userRelationshipService.getUserFollowCount(targetUserId)

		)
	}
}