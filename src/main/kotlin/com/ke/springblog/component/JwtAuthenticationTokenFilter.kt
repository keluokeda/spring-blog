package com.ke.springblog.component

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilter(private val jwtTokenComponent: JwtTokenComponent) : OncePerRequestFilter() {


	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {


		val header = request.getHeader(jwtTokenComponent.headerString)

		if (header == null || !header.startsWith(jwtTokenComponent.tokenPrefix)) {
			chain.doFilter(request, response)
			return
		}

		SecurityContextHolder.getContext().authentication = jwtTokenComponent.getAuthentication(httpServletRequest = request)


		chain.doFilter(request, response)
	}


}