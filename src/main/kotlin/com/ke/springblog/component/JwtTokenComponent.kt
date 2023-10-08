package com.ke.springblog.component

import com.ke.springblog.Consts
import com.ke.springblog.exception.LoginFailedException
import com.ke.springblog.exception.NoLoginException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenComponent {
	val secret = "P@MaYzkSjmkzPC57L11aassssggaaass"            // JWT密码
	val tokenPrefix = "Bearer "        // Token前缀
	val headerString = Consts.TOKEN_KEY// 存放Token的Header Key

	/**
	 * 生成token
	 */
	fun createToken(userId: String): String {
		return Jwts.builder()
				.setSubject(userId)//保存用户账号
				.setId("")
				.setExpiration(Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))//设置过期时间
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact()
	}

	fun getAuthentication(httpServletRequest: HttpServletRequest): UsernamePasswordAuthenticationToken? {
		val token = httpServletRequest.getHeader(headerString)

		val user = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.replace(tokenPrefix, ""))
				.body.subject

		val userId = httpServletRequest.getHeader(Consts.HEADER_USER_ID)

		if (userId == null || userId != user) {
			throw NoLoginException("没有登录！")
		}

		return UsernamePasswordAuthenticationToken(user, null, arrayListOf())

	}

	fun getUserIdFromToken(token: String): String = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.replace(tokenPrefix, ""))
			.body.subject

}