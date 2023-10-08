package com.ke.springblog.model.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class LoginResponse(
		@ApiModelProperty(value = "用户id",example = "1")
		val userId: Long,
		@ApiModelProperty(value = "鉴权token",example = "asd234f")
		val token: String
)