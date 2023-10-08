package com.ke.springblog.model.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class BaseResponse<T>(
		@ApiModelProperty("错误码", required = true,example = "0")
		val errorCode: Int,
		@ApiModelProperty("响应信息", required = true,example = "成功",allowEmptyValue = true)
		val errorMessage: String?,
		@ApiModelProperty("响应体", required = true,allowEmptyValue = true)
		val body: T)