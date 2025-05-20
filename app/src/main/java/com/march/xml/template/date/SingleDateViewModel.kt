package com.march.xml.template.date

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class SingleDateViewModel : ViewModel() {
    val dateText = MutableLiveData<String>()

    fun setDateText(date: LocalDate) {
        dateText.value = date.format(
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일", Locale.KOREA)
        )
    }
}