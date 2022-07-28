package com.example.importcsv.listener

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class ImportCsvStepExecutionListener: StepExecutionListener {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    override fun beforeStep(stepExecution: StepExecution) {
        println("Step開始$stepExecution")
        println("パラメータ: " + stepExecution.jobParameters)
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        println("Step終了$stepExecution")
        val taskId = stepExecution.jobParameters.getString("taskId")

        //エラー時は全ROLLBACK
        if (stepExecution.exitStatus.exitCode == "FAILED") {
            jdbcTemplate.update("DELETE FROM task_detail WHERE task_id = ?", taskId)
            println("Step終了")
        }
        return ExitStatus.COMPLETED
    }

}