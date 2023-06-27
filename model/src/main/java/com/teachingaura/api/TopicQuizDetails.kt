package com.teachingaura.api

import com.teachingaura.api.test.QuestionDetails

data class TopicQuizDetails(

    val id: String = "",
    val orderIndex:Int = 0,
    val questions: List<QuestionDetails> = listOf(),
)
