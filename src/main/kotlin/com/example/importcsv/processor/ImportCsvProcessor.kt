package com.example.importcsv.processor

import com.example.importcsv.input.TaskDetailCsv
import com.example.importcsv.exception.BatchException
import com.example.importcsv.input.AppUser
import com.example.importcsv.output.TaskDetailRecord
import org.springframework.batch.core.annotation.OnProcessError
import org.springframework.batch.item.ItemProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ImportCsvProcessor: ItemProcessor<TaskDetailCsv, TaskDetailRecord> {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    override fun process(input: TaskDetailCsv): TaskDetailRecord? {

            //必須要件チェック
            if (input.userId.isNullOrEmpty()) throw BatchException("ユーザIDを入力してください")
            if (input.content.isNullOrEmpty()) throw BatchException("タスク詳細を入力してください")
            if (input.deadline.isNullOrEmpty()) throw BatchException("有効期限を入力してください")

            //アプリユーザ存在チェック
            val appUserRowMapper = BeanPropertyRowMapper<AppUser>(AppUser::class.java)
            val user = jdbcTemplate.query("SELECT * FROM app_user WHERE id = ?", appUserRowMapper, input.userId)
            if (user.isEmpty()) throw BatchException("ユーザが存在しません")

            return TaskDetailRecord(
                taskId = 1, //仮で固定
                userId = input.userId!!.toInt(),
                content = input.content!!,
                deadline = LocalDate.parse(input.deadline, formatter),
                createdAt = OffsetDateTime.now()
            )
    }

    @OnProcessError
    private fun onProcess(input: TaskDetailCsv, ex: Exception) {
        jdbcTemplate.update("INSERT INTO task_import_error (task_id, user_id, error) VALUES (1, ?, ?)", input.userId, ex.message)
    }
}
