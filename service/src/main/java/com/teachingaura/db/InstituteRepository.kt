package com.teachingaura.db

import com.teachingaura.db.model.Institute
import org.springframework.data.repository.CrudRepository

interface InstituteRepository : CrudRepository<Institute, String> {

    fun findByNameContaining(searchString: String): List<Institute>
    fun findByPid(id: String): Institute?
}
