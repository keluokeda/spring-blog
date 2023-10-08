package com.ke.springblog.controller

import com.ke.springblog.service.FileService
import com.ke.springblog.toFailedJsonResponse
import com.ke.springblog.toJsonListResponse
import com.ke.springblog.toSuccessJsonResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class UploadController(private val fileService: FileService) {


	/**
	 * 上传单个图片
	 */
	@PostMapping("/image")
	fun uploadImage(@RequestParam("image") multipartFile: MultipartFile): Any {

		return fileService.handleUploadImage(multipartFile).toSuccessJsonResponse()
	}


	/**
	 * 上传多个图片
	 */
	@PostMapping("/images")
	fun uploadImages(@RequestParam("images", required = true) multipartFiles: List<MultipartFile>): Any {


		if (multipartFiles.isEmpty()) {
			return "图片不能为空".toFailedJsonResponse()
		}

		val imagePathList = multipartFiles.map {
			return@map fileService.handleUploadImage(it)
		}


		return imagePathList.toJsonListResponse()


	}


	/**
	 * 获取图片
	 */
	@GetMapping("/images/{fileName:.+}")
	fun getImage(@PathVariable fileName: String): ResponseEntity<Any> {

		return fileService.getImage(fileName)
	}


}
