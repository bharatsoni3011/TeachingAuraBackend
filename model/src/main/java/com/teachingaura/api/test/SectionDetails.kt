package com.teachingaura.api.test

data class SectionDetails(
    val sectionName: String = "",
    val sectionMarks: Int = 1,
    val sectionDuration: Int = 0,
    val orderIndex: Int = 0,
    val questions: List<QuestionDetails> = listOf()
)
