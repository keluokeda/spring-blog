package com.ke.springblog.security

import com.ke.springblog.component.JwtAccessDeniedHandler
import com.ke.springblog.component.JwtAuthenticationTokenFilter
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class WebSecurityConfig(private val jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter, private val jwtAccessDeniedHandler: JwtAccessDeniedHandler) : WebSecurityConfigurerAdapter() {


	override fun configure(http: HttpSecurity?) {


		http?.let {
			it.cors().and().csrf().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/users").permitAll()
					.antMatchers("/sessions").permitAll()

					.antMatchers(HttpMethod.GET, "/images/*").permitAll()

					.antMatchers("/swagger-resources/**").permitAll()
					.antMatchers("/swagger-ui.html/**").permitAll()
					.antMatchers("/v2/**").permitAll()
					.antMatchers("/webjars/**").permitAll()
					.anyRequest().authenticated()


			it.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

			it.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler).authenticationEntryPoint(jwtAccessDeniedHandler)


		}
	}


}