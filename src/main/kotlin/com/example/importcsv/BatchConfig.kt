package com.example.importcsv

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource


@Configuration
@EnableBatchProcessing
class BatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun testJob1(): Job {
        return jobBuilderFactory["job01"]
            .incrementer(RunIdIncrementer())
            .start(testStep1())
            .build()
    }

    @Bean
    fun testStep1(): Step {
        return stepBuilderFactory["step01"]
            .chunk<SyainCsvModel, SyainResultModel>(10)
            .reader(testRead())
            .processor(testProc())
            .writer(testWrite())
            .build()
    }

    @Bean
    fun testRead(): FlatFileItemReader<SyainCsvModel> {
        return FlatFileItemReaderBuilder<SyainCsvModel>()
            .name("testCSV")
            .resource(ClassPathResource("syain1.csv"))
            .delimited().names(*arrayOf("id", "romaji"))
            .fieldSetMapper(object : BeanWrapperFieldSetMapper<SyainCsvModel?>() {
                init {
                    setTargetType(SyainCsvModel::class.java)
                }
            }).build()
    }

    @Bean
    fun testProc(): ProcProcess {
        return ProcProcess()
    }

    @Bean
    fun testWrite(): ProcWrite<SyainResultModel> {
        return ProcWrite<SyainResultModel>()
    }
}