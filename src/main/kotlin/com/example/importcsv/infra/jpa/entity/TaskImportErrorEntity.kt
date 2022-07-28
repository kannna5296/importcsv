package com.example.importcsv.infra.jpa.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "task_import_error")
class TaskImportErrorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id :Int? = null,
    var taskId: Int? = null,
    var userId: String? = null,
    var error: String? = null,
)