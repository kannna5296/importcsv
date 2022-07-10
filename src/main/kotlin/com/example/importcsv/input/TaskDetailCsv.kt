package com.example.importcsv.input

data class TaskDetailCsv(
    //割り当てるユーザ
    var userId: String? = null,
    //タスク詳細
    var content: String? = null,
    //有効期限
    var deadline: String? = null,
)
