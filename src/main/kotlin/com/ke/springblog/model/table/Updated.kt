package com.ke.springblog.model.table

import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType

@MappedSuperclass//该类不会创建数据表
//@EntityListeners(AuditingEntityListener::class)
abstract class Updated : Created() {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_at", nullable = false, updatable = true)
	@LastModifiedDate
	lateinit var updatedAt: Date
}