package com.ke.springblog.controller

import com.ke.springblog.service.UserRelationshipService
import com.ke.springblog.toJsonResponse
import com.ke.springblog.toSuccessJsonResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
class UserRelationshipController(private val userRelationshipService: UserRelationshipService) {


    /**
     * 获取一个用户所有的粉丝
     */
    @GetMapping("/users/{userId}/fans")
    fun getUserAllFans(@PathVariable userId: Long, pageable: Pageable): Map<String, Any?> {
        return userRelationshipService.getUserFans(userId, pageable).toJsonResponse()

    }

    /**
     * 获得一个用户关注的人
     */
    @GetMapping("/users/{userId}/follows")
    fun getUserFollows(@PathVariable userId: Long, pageable: Pageable) = userRelationshipService.getUserFollows(userId, pageable).toJsonResponse()

    /**
     * 关注用户
     */
    @PostMapping("/users/{followUserId}/fans")
    fun followUser(@PathVariable followUserId: Long, @RequestHeader userId: Long): Map<String, Any?> {
        userRelationshipService.followUser(userId, followUserId)
        return "关注成功".toSuccessJsonResponse()

    }

    /**
     * 取消关注用户
     */
    @DeleteMapping("/users/{toUserId}/fans")
    fun unFollowUser(@RequestHeader userId: Long, @PathVariable toUserId: Long): Map<String, Any?> {
        userRelationshipService.unFollowUser(userId, toUserId)
        return "取消关注成功".toSuccessJsonResponse()
    }


}