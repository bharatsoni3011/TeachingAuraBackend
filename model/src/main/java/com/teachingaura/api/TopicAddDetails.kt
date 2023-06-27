package com.teachingaura.api

data class TopicAddDetails(

    val topicQuizDetails: List<TopicQuizDetails> = listOf(),
    val topicTextDetails: List<TopicTextDetails> = listOf(),
    val topicFileDetails: List<TopicFileDetails> = listOf(),
)
