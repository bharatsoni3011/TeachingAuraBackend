package com.teachingaura.db

import com.teachingaura.db.model.BatchTest
import org.springframework.data.repository.CrudRepository

interface TestV2Repository : CrudRepository<BatchTest,String> {

    fun findByPid(id: String): BatchTest?

}