package com.teachingaura.api

data class CourseAndBatchDetails(
    val id: String = "",
    val name: String = "",
    val subjects: List<SubjectDetails> = listOf(),
    val instituteId: String = "",
    val batchDetails: BatchDetails,
    val courseDescription: String? = ""
)
