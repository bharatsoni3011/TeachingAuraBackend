package com.teachingaura.service

import com.teachingaura.db.BatchRepository
import com.teachingaura.db.model.Batch
import com.teachingaura.exception.BatchDoesNotExistException
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class BatchService @Inject constructor(val batchRepository: BatchRepository) {

    fun getBatchById(id: String): Batch {
        return batchRepository.findByPid(id) ?: throw BatchDoesNotExistException()
    }

    fun insert(dbBatch: Batch): Batch {
        return batchRepository.save(dbBatch)
    }
}
