package com.teachingaura.api

data class TestV2Details(

    val id: String,

    val name: String = "",

    var description: String,

    val startTime: Long,

    val endTime: Long,

    val courseId: String,

    val batchId: String,

    val instituteId: String,

    val sections: List<SectionDetails> = listOf(),

    val submission: List<SubmissionDetails> = listOf(),
)
