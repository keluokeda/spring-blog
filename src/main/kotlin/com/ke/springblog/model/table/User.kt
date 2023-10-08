package com.ke.springblog.model.table

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		val id: Long = -1,//必须要有默认值 让这个类有无参构造方法

		@Column(name = "account", unique = true, length = 100)
		val account: String = "",

		@Column(name = "password", length = 100)
		val password: String = "",

		@Column(name = "user_name", nullable = false, length = 100)
		val userName: String = "",

		@Column(name = "icon_url", nullable = true)
		val iconUrl: String? = null,

		@Column(name = "description", nullable = true)
		val description: String? = null



) : Created()
