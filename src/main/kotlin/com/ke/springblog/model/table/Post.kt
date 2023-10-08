package com.ke.springblog.model.table

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "posts")
data class Post(

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		val id: Long = -1,

		@NotNull
		@Column(name = "title", length = 100, nullable = false)
		val title: String = "",

		@Lob
		@Column(name = "content", nullable = false)
		val content: String = "",


		@ManyToMany(
				fetch = FetchType.LAZY,
				cascade = [(CascadeType.PERSIST), (CascadeType.MERGE)]
		)
		@JoinTable(
				name = "post_tag",
				joinColumns = [JoinColumn(name = "post_id")],
				inverseJoinColumns = [(JoinColumn(name = "tag_id"))]
		)
		//必须要用 mutableSetOf Operation is not supported for read-only collection
		var tags: Set<Tag> = mutableSetOf(),//标签


		@OneToMany(
				fetch = FetchType.LAZY,
				cascade = [CascadeType.ALL],
				mappedBy = "post"//维护方 属性名称
		)
		var images: Set<PostImage> = mutableSetOf(),

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "user_id", nullable = false)
		@OnDelete(action = OnDeleteAction.CASCADE)
		val user: User = User()


) : Updated()