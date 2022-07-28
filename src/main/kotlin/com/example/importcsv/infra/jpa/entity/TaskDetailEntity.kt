package com.example.importcsv.infra.jpa.entity

import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "task_detail")
class TaskDetailEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id :Int? = null,
    var taskId: Int? = null,
    var userId: Int? = null,
    var content: String? = null,
    var deadline: OffsetDateTime? = null,
    var createdAt: OffsetDateTime? = null,
)