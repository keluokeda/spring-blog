package com.ke.springblog.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.ke.springblog.Consts
import com.ke.springblog.toFailedJsonResponse
import org.springframework.data.repository.CrudRepository
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * token认证失败会走这里
 */
@Component
class JwtAccessDeniedHandler : AccessDeniedHandler, AuthenticationEntryPoint {
	override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
		response?.let {
			returnErrorMessage(it, "commence")
		}
	}

	override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: AccessDeniedException?) {
		response?.let {
			returnErrorMessage(it, "handle")
		}
	}

	fun returnErrorMessage(response: HttpServletResponse, message: String) {
		response.status = HttpServletResponse.SC_OK
		response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE

		response.writer.print(ObjectMapper().writeValueAsString("未登录或登录已失效！".toFailedJsonResponse(errorCode = Consts.ERROR_CODE_NOT_LOGIN)))
		response.writer.flush()
	}
}
