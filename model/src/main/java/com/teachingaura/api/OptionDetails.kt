package com.teachingaura.api

import com.teachingaura.api.test.DataFormat

data class OptionDetails(

    val option: String = "",
    val isAnswer: Boolean = false,
    val optionType: DataFormat = DataFormat.TEXT
)
