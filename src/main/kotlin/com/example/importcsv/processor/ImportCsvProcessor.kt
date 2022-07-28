package com.example.importcsv.processor

import com.example.importcsv.input.TaskDetailCsv
import com.example.importcsv.exception.BatchException
import com.example.importcsv.infra.jpa.entity.TaskImportErrorEntity
import com.example.importcsv.infra.jpa.repository.AppUserRepository
import com.example.importcsv.infra.jpa.repository.TaskImportErrorRepository
import com.example.importcsv.output.TaskDetailRecord
import org.springframework.batch.core.annotation.OnProcessError
import org.springframework.batch.item.ItemProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Scope
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Component
@Scope(value = "step01")
class ImportCsvProcessor: ItemProcessor<TaskDetailCsv, TaskDetailRecord> {

    @Value("#{jobParameters['taskId']}")
    private val taskId: Int? = null

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    @Autowired
    lateinit var taskImportErrorRepository: TaskImportErrorRepository

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    override fun process(input: TaskDetailCsv): TaskDetailRecord? {

        //必須要件チェック
        if (input.userId.isNullOrEmpty()) throw BatchException("ユーザIDを入力してください")
        if (input.content.isNullOrEmpty()) throw BatchException("タスク詳細を入力してください")
        if (input.deadline.isNullOrEmpty()) throw BatchException("締切を入力してください")

        //型変換チェック
        val userIdInt = input.userId?.toIntOrNull() ?: throw BatchException("ユーザIDは数字で指定してください")
        var deadlineDate: LocalDate?
        try {
            deadlineDate = LocalDate.parse(input.deadline, formatter)
        } catch (ex: DateTimeParseException) {
            throw BatchException("有効期限の形式が不正です")
        }

        //ユーザ存在チェック
        appUserRepository.findByIdOrNull(userIdInt) ?: throw BatchException("ユーザが存在しません")

        return TaskDetailRecord(
            taskId = 1, //仮で固定
            userId = userIdInt,
            content = input.content!!,
            deadline = deadlineDate,
            createdAt = OffsetDateTime.now()
        )
    }

    @OnProcessError
    private fun onProcess(input: TaskDetailCsv, ex: Exception) {
        val taskImportErrorEntity = TaskImportErrorEntity(
            taskId = taskId,
            userId = input.userId,
            error =ex.message
        )
        taskImportErrorRepository.saveAndFlush(taskImportErrorEntity)
    }
}
