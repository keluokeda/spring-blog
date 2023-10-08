package com.ke.springblog.controller

import com.ke.springblog.service.PostService
import com.ke.springblog.toJsonResponse
import com.ke.springblog.toSuccessJsonResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
class PostController(private val postService: PostService) {

	@PostMapping("/posts")
	fun newPost(
			@RequestParam title: String,
			@RequestParam content: String,
			@RequestHeader userId: Long,
			@RequestParam(required = false) tags: List<String>?,
			@RequestParam(required = false) images: List<String>?
	): Map<String, Any?> {
		postService.newPost(userId, title, content, tags, images)

		return "发布成功".toSuccessJsonResponse()
	}

	@GetMapping("/posts")
	fun getPosts(pageable: Pageable, @RequestHeader userId: Long) = postService.getAllPosts(pageable, userId)


	@DeleteMapping("/posts/{postId}")
	fun deletePost(@PathVariable postId: Long, @RequestHeader userId: Long): Any {
		postService.deletePost(postId, userId)
		return "文章删除成功".toSuccessJsonResponse()
	}

	@GetMapping("/users/{targetUserId}/posts")
	fun getUserPosts(@PathVariable targetUserId: Long, pageable: Pageable, @RequestHeader userId: Long): Any {
		return postService.getUserPosts(pageable, userId, targetUserId)
	}
}