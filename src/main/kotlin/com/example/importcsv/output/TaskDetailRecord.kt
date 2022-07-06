package com.example.importcsv.output

import java.time.LocalDate
import java.time.OffsetDateTime

data class TaskDetailRecord(
    //親レコードID
    val taskId: Int,
    //割り当てるユーザ
    val userId: Int,
    //タスク詳細
    val content: String,
    //有効期限
    val deadline: LocalDate,
    val createdAt: OffsetDateTime
)