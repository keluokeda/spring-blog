package com.ke.springblog.service

import com.ke.springblog.Consts
import com.ke.springblog.exception.ResourceDuplicateException
import com.ke.springblog.exception.ResourceNotFoundException
import com.ke.springblog.exception.ServiceException
import com.ke.springblog.model.table.UserRelationship
import com.ke.springblog.repository.UserRelationshipRepository
import com.ke.springblog.repository.UserRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserRelationshipService(private val userRepository: UserRepository, private val userRelationshipRepository: UserRelationshipRepository) {

	/**
	 * 获取用户粉丝
	 * @param userId 用户id
	 */
	fun getUserFans(userId: Long, pageable: Pageable): List<Any> {
		val user = userRepository.findById(userId).orElseThrow({ ResourceNotFoundException(Consts.userNotFoundErrorMessage(userId)) })
		return userRelationshipRepository.findAllByToUser(user, pageable).map { return@map it.fromUser }
	}

	/**
	 * 获取用户关注的人
	 */
	fun getUserFollows(userId: Long, pageable: Pageable): List<Any> {
		return userRepository.findById(userId).map {
			return@map userRelationshipRepository.findAllByFromUser(it, pageable).map { it.toUser }
		}.orElseThrow({ ResourceNotFoundException(Consts.userNotFoundErrorMessage(userId)) })
	}

	/**
	 * 关注用户
	 */
	@Caching(evict = [CacheEvict(cacheNames = ["fans_count_by_user"], key = "#toUserId"), CacheEvict(cacheNames = ["follow_count_by_user"], key = "#fromUserId")])
	fun followUser(fromUserId: Long, toUserId: Long) {
		if (fromUserId == toUserId) {
			throw ServiceException("不能自己关注自己")
		}

		val fromUser = userRepository.findById(fromUserId).orElseThrow({ ResourceNotFoundException(Consts.userNotFoundErrorMessage(fromUserId)) })
		val toUser = userRepository.findById(toUserId).orElseThrow({ ResourceNotFoundException(Consts.userNotFoundErrorMessage(toUserId)) })

		if (userRelationshipRepository.findByFromUserAndToUser(fromUser, toUser).isPresent) {
			throw ResourceDuplicateException("你已经关注过用户了")
		}

		val userRelationship = UserRelationship(
				fromUser = fromUser,
				toUser = toUser
		)

		userRelationshipRepository.save(userRelationship)
	}

	/**
	 * 取消关注
	 */
	@Caching(evict = [CacheEvict(cacheNames = ["fans_count_by_user"], key = "#toUserId"), CacheEvict(cacheNames = ["follow_count_by_user"], key = "#fromUserId")])
	fun unFollowUser(fromUserId: Long, toUserId: Long) {
		val fromUser = userRepository.findById(fromUserId).orElseThrow { ResourceNotFoundException(Consts.userNotFoundErrorMessage(fromUserId)) }
		val toUser = userRepository.findById(toUserId).orElseThrow { ResourceNotFoundException(Consts.userNotFoundErrorMessage(toUserId)) }


		userRelationshipRepository.findByFromUserAndToUser(fromUser, toUser).map {
			userRelationshipRepository.delete(it)
		}.orElseThrow({ ResourceNotFoundException("你还没关注过用户呢！") })
	}


	@Cacheable(cacheNames = ["fans_count_by_user"], key = "#userId")
	fun getUserFansCount(userId: Long) = userRelationshipRepository.countByToUserId(userId)


	@Cacheable(cacheNames = ["follow_count_by_user"], key = "#userId")
	fun getUserFollowCount(userId: Long) = userRelationshipRepository.countByFromUserId(userId)

}