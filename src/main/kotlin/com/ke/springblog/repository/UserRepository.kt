package com.ke.springblog.repository

import com.ke.springblog.model.table.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

	/**
	 * 账号是否已经被注册
	 */
	fun existsByAccount(account: String): Boolean


	/**
	 * 根据账号查找用户
	 */
	fun findByAccount(account: String): Optional<User>
}