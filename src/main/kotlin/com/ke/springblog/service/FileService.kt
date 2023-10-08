package com.ke.springblog.service

import com.ke.springblog.exception.ControllerException
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Transactional
@Service
class FileService {

	private final val rootLocation = Paths.get("upload-dir")!!

	private final val supportImageTypes = arrayListOf(".png", ".jpg", ".PNG", ".JPG", ".jpeg")


	init {
		Files.createDirectories(rootLocation)
	}

	fun handleUploadImage(multipartFile: MultipartFile): String {
		val fileName = multipartFile.originalFilename!!

		val suffixName = fileName.substring(fileName.lastIndexOf("."))


		if (!supportImageTypes.contains(suffixName)) {
			throw ControllerException("非法图片格式")
		}


		val imageName = UUID.randomUUID().toString() + suffixName

		store(multipartFile, imageName)

		return "/images/$imageName"
	}

	fun getImage(fileName: String): ResponseEntity<Any> {

		val path = rootLocation.resolve(fileName)
		val resource = UrlResource(path.toUri())

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"").body(resource)
	}


	private fun store(file: MultipartFile, imageName: String) {
		Files.copy(file.inputStream, rootLocation.resolve(imageName))
	}
}