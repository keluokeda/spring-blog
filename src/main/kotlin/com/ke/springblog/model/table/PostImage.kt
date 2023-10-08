package com.ke.springblog.model.table

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "post_images")
data class PostImage(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		val id: Long = -1,

		@Column(name = "image_url")
		val imageUrl: String = "",

		@JsonIgnore
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "post_id", nullable = false)
		val post: Post? = null


) {
	override fun toString(): String {
		return "PostImage(id=$id, imageUrl='$imageUrl')"
	}


	override fun hashCode(): Int {
		return super.hashCode()
	}

	override fun equals(other: Any?): Boolean {
		return super.equals(other)
	}
}