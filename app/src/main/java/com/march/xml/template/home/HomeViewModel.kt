package com.march.xml.template.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _memoList = MutableStateFlow<List<String>>(emptyList())
    val memoList: StateFlow<List<String>> = _memoList.asStateFlow()

    private val _memoInput = MutableSharedFlow<String>()
    val memoInput: SharedFlow<String> = _memoInput.asSharedFlow()

    private var counter = 1

    private val _countdown = MutableLiveData<Int>(5)
    val countdown: LiveData<Int> get() = _countdown

    init {
        repeatToMakeNewMemo()
    }

    private fun repeatToMakeNewMemo() {
        viewModelScope.launch {
            while (true) {
                for (sec in 10 downTo 1) {
                    _countdown.value = sec
                    delay(1_000)
                }
                val newBatch = (counter until counter + 5).map { "${it}번째 메모입니다." }
                _memoList.value = newBatch
                counter += 5
            }
        }
    }

    fun addMemo(text: String) {
        viewModelScope.launch {
            _memoInput.emit(text)
        }
    }
}