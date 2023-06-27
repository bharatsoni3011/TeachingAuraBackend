package com.teachingaura.db

import com.teachingaura.db.model.Submission
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SubmissionRepository : CrudRepository<Submission, String> {

    fun findByPid(id: String): Submission?
}
