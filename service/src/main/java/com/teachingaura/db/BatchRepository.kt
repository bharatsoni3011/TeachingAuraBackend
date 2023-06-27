package com.teachingaura.db

import com.teachingaura.db.model.Batch
import org.springframework.data.repository.CrudRepository

interface BatchRepository : CrudRepository<Batch, String> {

    fun findByPid(id: String): Batch?
}
