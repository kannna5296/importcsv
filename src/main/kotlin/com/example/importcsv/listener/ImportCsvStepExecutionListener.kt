package com.example.importcsv.listener

import com.example.importcsv.infra.jpa.repository.TaskDetailRepository
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class ImportCsvStepExecutionListener: StepExecutionListener {

    @Autowired
    lateinit var taskDetailRepository: TaskDetailRepository

    override fun beforeStep(stepExecution: StepExecution) {
        println("Step開始$stepExecution")
        println("パラメータ: " + stepExecution.jobParameters)
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        println("Step終了$stepExecution")
        val taskId = stepExecution.jobParameters.getString("taskId") ?: throw Exception("")

        //エラー時は全ROLLBACK
        if (stepExecution.exitStatus.exitCode == "FAILED") {
            taskDetailRepository.deleteByTaskId(taskId.toInt())
            println("Step終了")
        }
        return ExitStatus.COMPLETED
    }

}