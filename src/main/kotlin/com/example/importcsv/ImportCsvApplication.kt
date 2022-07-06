package com.example.importcsv

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class ImportCsvApplication

fun main(args: Array<String>) {
	runApplication<ImportCsvApplication>(*args)
}
