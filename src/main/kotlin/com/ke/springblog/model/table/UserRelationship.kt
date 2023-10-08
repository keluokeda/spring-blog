package com.ke.springblog.model.table

import javax.persistence.*

@Entity
@Table(name = "user_relationship",
		//指定 两个字段 不能一起重复
		uniqueConstraints = [(UniqueConstraint(columnNames = arrayOf("from_user_id", "to_user_id")))]
)
data class UserRelationship(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		val id: Long = -1,

		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "from_user_id", nullable = false)
		val fromUser: User = User(),

		@OneToOne
		@JoinColumn(name = "to_user_id", nullable = false)
		val toUser: User = User()


) : Created()