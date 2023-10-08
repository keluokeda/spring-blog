package com.ke.springblog.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class Swagger2Config {

	@Bean
	fun defaultApi(): Docket {
		return Docket(DocumentationType.SWAGGER_2)
				//请求body格式
				.consumes(setOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
				//响应body格式
				.produces(setOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.apiInfo(apiInfo()).groupName("默认分组").select()

				.apis(RequestHandlerSelectors.basePackage("com.ke.springblog.controller")).paths(PathSelectors.any()).build()
	}

	//构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	// 预览地址:swagger-ui.html
	private fun apiInfo(): ApiInfo {
		return ApiInfoBuilder()
				.title("利用swagger构建测试系统api文档")
				.description("blog api")
				.contact(Contact("余艳辉", "", ""))
				.version("1.0")
				.build()
	}


}