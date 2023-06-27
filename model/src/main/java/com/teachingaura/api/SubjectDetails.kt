package com.teachingaura.api

data class SubjectDetails(
    val id: String = "",
    val name: String = "",
    val topics: List<TopicDetails> = listOf()
)
