package com.example.importcsv.listener

import org.springframework.batch.core.ChunkListener
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.scope.context.ChunkContext

class ImportCsvChunkListener: ChunkListener{

    override fun beforeChunk(context: ChunkContext) {
        println("Chunk開始$context")
    }

    override fun afterChunk(context: ChunkContext) {
        println("Chunk終了")
    }

    override fun afterChunkError(context: ChunkContext) {
        println("Chunkでエラーがありました")
    }
}