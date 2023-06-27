package com.teachingaura.api

import com.teachingaura.api.test.QuestionDetails

data class SectionDetails(

    val name: String,

    val time: Long,

    val questions: List<QuestionDetails> = listOf()
)
