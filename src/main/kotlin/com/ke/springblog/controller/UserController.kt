package com.ke.springblog.controller

import com.ke.springblog.service.UserService
import com.ke.springblog.toJsonResponse
import com.ke.springblog.toSuccessJsonResponse
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest


@RestController
class UserController(private val userService: UserService) {

	private val logger = LoggerFactory.getLogger(this.javaClass)!!

	/**
	 * 用户注册
	 */
	@PostMapping("/users")
	fun register(
			@RequestParam account: String,
			@RequestParam password: String,
			@RequestParam(name = "user_name", required = false) userName: String?,
			@RequestParam(required = false) iconUrl: String?,
			@RequestParam(required = false) description: String?
	): Map<String, Any?> {

		userService.saveUser(
				account.trim(), password.trim(), userName ?: account, iconUrl, description
		)

		val map = mutableMapOf<String, Any>()
		map["token"] = "token"
		map["userId"] = 10

		return map.toJsonResponse()


	}


	@GetMapping("/users")
	fun getAllUser(pageable: Pageable) = userService.getAllUser(pageable).toJsonResponse()


	@PutMapping("/users")
	fun updateUser(@RequestParam userName: String,
				   @RequestParam(required = false) description: String?,
				   @RequestHeader userId: Long,
				   @RequestParam(required = false) userIcon: String?
	): Map<String, Any?> {

		userService.updateUser(userName, description, userIcon, userId)


		return "user name = $userName , description = $description , userIcon = $userIcon".toSuccessJsonResponse()
	}

	@GetMapping("/users/{targetUserId}")
	fun getUser(@PathVariable targetUserId: Long, @RequestHeader userId: Long, httpServletRequest: HttpServletRequest): Any {

		httpServletRequest.cookies.forEach {
			logger.info("cookie name = ${it.name} , cookie value = ${it.value}")
		}

		val value = httpServletRequest.session.getAttribute("name")

		logger.info("session value = $value")

		return userService.getUserInfo(targetUserId, userId).toJsonResponse()
	}

}