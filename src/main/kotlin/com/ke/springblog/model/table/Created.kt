package com.ke.springblog.model.table

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


/**
 * 有创建日期的
 */
@MappedSuperclass//该类不会创建数据表
@EntityListeners(AuditingEntityListener::class)
abstract class Created {


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at", nullable = false, updatable = false)//创建时间不可更改
	@CreatedDate
	lateinit var createAt: Date
}