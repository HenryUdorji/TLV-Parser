package com.ifechukwu.tlvparser.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ifechukwu.tlvparser.data.ParserState
import com.ifechukwu.tlvparser.data.TLVParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @Author: ifechukwu.udorji
 * @Date: 8/15/2024
 */
class TlvViewModel: ViewModel() {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _parserStateFlow = MutableStateFlow<ParserState>(ParserState.Idle)
    val parserStateFlow = _parserStateFlow.asStateFlow()

    fun parseTlv(tlv: String) = coroutineScope.launch {
        TLVParser.decodeTLV(tlv) {
            _parserStateFlow.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}