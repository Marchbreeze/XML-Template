package com.march.xml.template.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class DateViewModel : ViewModel() {
    // 오늘 전후 ±7일 범위
    private val _dates: List<LocalDate> = generateDateList(7)
    val dates: List<LocalDate> get() = _dates

    // 현재 페이지 인덱스 (초기값: 오늘 날짜 위치)
    private val _currentIndex = MutableLiveData<Int>().apply {
        value = _dates.indexOf(LocalDate.now()).coerceAtLeast(0)
    }
    val currentIndex: LiveData<Int> = _currentIndex

    fun goNext() {
        val next = (_currentIndex.value ?: 0) + 1
        _currentIndex.value = next.coerceAtMost(_dates.lastIndex)
    }

    fun goPrev() {
        val prev = (_currentIndex.value ?: 0) - 1
        _currentIndex.value = prev.coerceAtLeast(0)
    }

    fun setCurrent(index: Int) {
        _currentIndex.value = index.coerceIn(0, _dates.lastIndex)
    }

    companion object {
        private fun generateDateList(days: Int): List<LocalDate> {
            val today = LocalDate.now()
            return (-days..days).map { today.plusDays(it.toLong()) }
        }
    }
}