package com.example.importcsv

import org.springframework.batch.item.ItemProcessor
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ImportCsvProcessor: ItemProcessor<TaskDetailCsv, TaskDetailRecord> {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    override fun process(input: TaskDetailCsv): TaskDetailRecord {

        return TaskDetailRecord(
            taskId = 1, //仮で固定
            userId = input.userId!!.toInt(),
            content = input.content!!,
            deadline = LocalDate.parse(input.deadline,formatter),
            createdAt = OffsetDateTime.now()
        )
    }
}
