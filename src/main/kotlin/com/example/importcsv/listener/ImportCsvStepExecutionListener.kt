package com.example.importcsv.listener

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class ImportCsvStepExecutionListener: StepExecutionListener {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    override fun beforeStep(stepExecution: StepExecution) {
        println("Step開始$stepExecution")
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        jdbcTemplate.update("DELETE FROM task_detail WHERE task_id = ?", 1)
        println("Step終了")
        return ExitStatus.COMPLETED
    }

}