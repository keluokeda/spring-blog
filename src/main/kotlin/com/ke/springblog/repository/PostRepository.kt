package com.ke.springblog.repository

import com.ke.springblog.model.query.PostQuery
import com.ke.springblog.model.table.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

	// localhost:8080/posts/1?page=0&size=2 分页url
	@Query(value = "select\n" +
			"  p.id as postId,\n" +
			"  p.title as postTitle,\n" +
			"  p.content as postContent,\n" +
			"  p.create_at as postCreateDate,\n" +
			"  u.id as userId,\n" +
			"  u.user_name as userName,\n" +
			"  u.icon_url as userIconUrl,\n" +
			"  u.description as userDescription,\n" +
			"  u.fans_count as userFansCount,\n" +
			"  u.follow_count as userFollowCount,\n" +
			"  u.post_count as userPostCount,\n" +
			"  u.comment_count as userCommentCount,\n" +
			"  count(r.id) as attentionUser\n" +
			"  from posts as p\n" +
			"  left join users as u on p.user_id = u.id\n" +
			"  left join user_relationship r\n" +
			"  on r.from_user_id = ?1 and r.to_user_id = p.user_id\n" +
			"  group by p.id",
			nativeQuery = true)
	fun findAll(userId: Long, pageable: Pageable): List<Map<String, Any?>>


	@Query(value = "select\n" +
			" new com.ke.springblog.model.query.PostQuery(" +
			"p.id," +
			"p.title," +
			"p.content," +
			"p.createAt," +
			"u.id," +
			"u.userName," +
			"u.iconUrl," +
			"u.description," +
			"count(r.id)) " +
			"from Post as p\n" +
			"  left join User as u on p.user.id = u.id\n" +
			"  left join UserRelationship r\n" +
			"on r.fromUser.id = ?1 and r.toUser.id = p.user.id\n" +
			"group by p.id order by p.createAt DESC")
	fun findAllPost(userId: Long, pageable: Pageable): Page<PostQuery>


	@Query(value = "select\n" +
			" new com.ke.springblog.model.query.PostQuery(" +
			"p.id," +
			"p.title," +
			"p.content," +
			"p.createAt," +
			"u.id," +
			"u.userName," +
			"u.iconUrl," +
			"u.description," +
			"count(r.id)) " +
			"from Post as p\n" +
			"  left join User as u on p.user.id = u.id\n" +
			"  left join UserRelationship r\n" +
			"on r.fromUser.id = ?1 and r.toUser.id = p.user.id\n" +
			" where u.id = ?2 group by p.id order by p.createAt DESC")
	fun findUserPosts(userId: Long, targetUserId: Long, pageable: Pageable): Page<PostQuery>



	fun countByUserId(userId: Long):Int


}