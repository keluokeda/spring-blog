package com.ke.springblog.model.table

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "comments")
data class Comment(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		val id: Long = -1,

		@Column(name = "content")
		val content: String = "",

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "user_id", nullable = false)
		@OnDelete(action = OnDeleteAction.CASCADE)
		var user: User? = null,

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "post_id", nullable = false)
		@OnDelete(action = OnDeleteAction.CASCADE)
		var post: Post? = null
) : Updated()