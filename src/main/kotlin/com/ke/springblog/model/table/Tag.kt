package com.ke.springblog.model.table

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
@Table(name = "post_tags")
data class Tag(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		val id: Long = -1,

		@Column(name = "name")
		val name: String = "",

		@JsonIgnore
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "post_id", nullable = false)
		val post: Post? = null
) {
	override fun toString(): String {
		return "Tag(id=$id, name='$name')"
	}

	override fun hashCode(): Int {
		return super.hashCode()
	}

	override fun equals(other: Any?): Boolean {
		return super.equals(other)
	}
}