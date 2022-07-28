package com.example.importcsv.infra.jpa.repository

import com.example.importcsv.infra.jpa.entity.TaskDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskDetailRepository : JpaRepository<TaskDetailEntity,Int>{

    fun deleteByTaskId(taskId: Int)
}