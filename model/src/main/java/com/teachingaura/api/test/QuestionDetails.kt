package com.teachingaura.api.test

import com.teachingaura.api.OptionDetails

data class QuestionDetails(

    val question: String = "",
    val options: List<OptionDetails> = listOf(),
    val questionFormat: DataFormat = DataFormat.TEXT,
    val questionType: QuestionType = QuestionType.OBJECTIVE,
    val questionMarks: Int = 1
)
