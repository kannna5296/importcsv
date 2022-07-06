package com.example.importcsv.listener

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class ImportCsvJobListener: JobExecutionListener {

    override fun beforeJob(jobExecution: JobExecution) {
        println("JOB開始$jobExecution")
    }

    override fun afterJob(jobExecution: JobExecution) {
        println("JOB終了")
    }
}