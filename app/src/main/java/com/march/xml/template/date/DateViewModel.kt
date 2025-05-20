package com.march.xml.template.date

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class DateViewModel : ViewModel() {
    // 오늘 전후 ±7일 범위
    private val _dates: List<LocalDate> = generateDateList(7)
    val dates: List<LocalDate> get() = _dates

    // 현재 페이지 인덱스 (초기값: 오늘 날짜 위치)
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    init {
        setInitialIndex()
    }

    fun goNext() {
        val next = _currentIndex.value + 1
        _currentIndex.value = next.coerceAtMost(_dates.lastIndex)
    }

    fun goPrev() {
        val prev = _currentIndex.value - 1
        _currentIndex.value = prev.coerceAtLeast(0)
    }

    fun setCurrent(index: Int) {
        _currentIndex.value = index.coerceIn(0, _dates.lastIndex)
    }

    private fun setInitialIndex() {
        _currentIndex.value = _dates.indexOf(LocalDate.now()).coerceAtLeast(0)
    }

    companion object {
        private fun generateDateList(days: Int): List<LocalDate> {
            val today = LocalDate.now()
            return (-days..days).map { today.plusDays(it.toLong()) }
        }
    }
}