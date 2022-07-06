package com.example.importcsv.job

import com.example.importcsv.input.TaskDetailCsv
import com.example.importcsv.listener.ImportCsvChunkListener
import com.example.importcsv.listener.ImportCsvJobListener
import com.example.importcsv.output.TaskDetailRecord
import com.example.importcsv.processor.ImportCsvProcessor
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.sql.DataSource

@Configuration
class BatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun importJob(step: Step): Job {
        return jobBuilderFactory["job01"]
            .incrementer(RunIdIncrementer())
            .listener(ImportCsvJobListener())
            .start(step)
            .build()
    }

    @Bean
    fun importStep1(writer: JdbcBatchItemWriter<TaskDetailRecord>): Step {
        return stepBuilderFactory["step01"]
            .chunk<TaskDetailCsv, TaskDetailRecord>(3)
            .reader(importCsvReader())
            .processor(importCsvProcessor())
            .writer(writer)
            .listener(ImportCsvChunkListener())
            .build()
    }

    @Bean
    fun importCsvReader(): FlatFileItemReader<TaskDetailCsv> {
        return FlatFileItemReaderBuilder<TaskDetailCsv>()
            .name("importCsvReader")
            .linesToSkip(1) //ヘッダ行飛ばす
            .resource(ClassPathResource("test.csv"))
            .delimited()
            .names(*arrayOf("userId", "content", "deadline"))
            .fieldSetMapper(object : BeanWrapperFieldSetMapper<TaskDetailCsv>() {
                init {
                    setTargetType(TaskDetailCsv::class.java)
                }
            })
            .build()
    }

    @Bean
    fun importCsvProcessor(): ImportCsvProcessor {
        return ImportCsvProcessor()
    }

    @Bean
    fun importCsvWriter(dataSource: DataSource): JdbcBatchItemWriter<TaskDetailRecord> {

        return JdbcBatchItemWriterBuilder<TaskDetailRecord>()
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
            .sql("INSERT INTO task_detail" +
            " (task_id, user_id, content, deadline, created_at) " +
            " VALUES " +
            " (:taskId,:userId,:content,:deadline,:createdAt)")
            .dataSource(dataSource)
            .build()
    }
}