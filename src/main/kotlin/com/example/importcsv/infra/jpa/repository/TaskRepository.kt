package com.example.importcsv.infra.jpa.repository

import com.example.importcsv.infra.jpa.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<TaskEntity,Int>