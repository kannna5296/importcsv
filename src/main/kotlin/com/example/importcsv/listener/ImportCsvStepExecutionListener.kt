package com.example.importcsv.listener

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener

class ImportCsvStepExecutionListener: StepExecutionListener {
    override fun beforeStep(stepExecution: StepExecution) {
        println("Step開始$stepExecution")
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        println("Step終了")
        return ExitStatus.COMPLETED
    }

}