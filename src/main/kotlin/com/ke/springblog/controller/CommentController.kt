package com.ke.springblog.controller

import com.ke.springblog.service.CommentService
import com.ke.springblog.toJsonListResponse
import com.ke.springblog.toJsonResponse
import com.ke.springblog.toSuccessJsonResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*


@RestController
class CommentController(private val commentService: CommentService) {

	@PostMapping("/posts/{postId}/comments")
	fun newComment(@PathVariable postId: Long, @RequestHeader userId: Long, @RequestParam content: String): Map<String, Any?> {
		commentService.newComment(userId, postId, content)
		return "发布评论成功".toSuccessJsonResponse()
	}

	@GetMapping("/posts/{postId}/comments")
	fun getComments(@PathVariable postId: Long, pageable: Pageable) = commentService.getComments(postId, pageable).toJsonListResponse()
}