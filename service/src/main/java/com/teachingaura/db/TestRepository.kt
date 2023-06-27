package com.teachingaura.db

import com.teachingaura.db.model.Batch
import com.teachingaura.db.model.Institute
import com.teachingaura.db.model.Schedule
import com.teachingaura.db.model.Test
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository : CrudRepository<Test, String> {

    fun findByPid(id: String): Test?

    @Query("select t from Test t where t.batch in ?1")
    fun findByBatchPid(batches: List<Batch>): List<Test>
}