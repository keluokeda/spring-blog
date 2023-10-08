package com.ke.springblog.controller

import com.ke.springblog.component.JwtTokenComponent
import com.ke.springblog.model.response.BaseResponse
import com.ke.springblog.model.response.LoginResponse
import com.ke.springblog.service.UserService
import com.ke.springblog.toJsonResponse
import io.swagger.annotations.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Api(value = "/sessions", tags = ["会话"], position = 1)
@RestController
class SessionController(private val userService: UserService, private val jwtTokenComponent: JwtTokenComponent) {

	@ApiOperation("创建会话", notes = "使用账号密码登录")
	@ApiResponse(code = 200, response = BaseResponse::class, message = "登录成功")
	@PostMapping("/sessions")
	@ApiImplicitParams(
			ApiImplicitParam(name = "account", value = "账号", required = true, paramType = "form", dataType = "String",defaultValue = "13428934452"),
			ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String",defaultValue = "123456")

	)
	fun login(@RequestParam(name = "account", required = true)
//			  @ApiParam(name = "account", value = "账号", required = true, example = "13428934452")
			  account: String,
			  @RequestParam(name = "password", required = true)
//			  @ApiParam(value = "密码", required = true, example = "123456")
			  password: String,
			  httpServletResponse: HttpServletResponse, httpServletRequest: HttpServletRequest):
			BaseResponse<LoginResponse> {

		val userId = userService.validateAccountPassword(account.trim(), password.trim())
		val token = jwtTokenComponent.createToken(userId.toString())

		val cookie = Cookie("name", "value")
		cookie.maxAge = Int.MAX_VALUE
		cookie.path = "/" //不加会报错

		httpServletRequest.session.setAttribute("name", "value")



		httpServletResponse.addCookie(cookie)

		val response = LoginResponse(userId, token)

		return BaseResponse(0, "", response)
	}
}
