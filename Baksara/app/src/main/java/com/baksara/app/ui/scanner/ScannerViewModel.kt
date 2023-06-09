package com.baksara.app.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baksara.app.repository.BaksaraRepository
import com.baksara.app.response.GraphQLResponse
import com.baksara.app.response.ScannerResponse
import com.baksara.app.response.Translatorv2Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.File

class ScannerViewModel(private val baksaraRepository: BaksaraRepository): ViewModel() {
    val liveDataResponseUpdateUser: MutableLiveData<Result<GraphQLResponse>> = MutableLiveData()
    val liveDataResponseScanner: MutableLiveData<Result<ScannerResponse>> = MutableLiveData()
    val liveDataTranslatorResponse: MutableLiveData<Result<Translatorv2Response>> = MutableLiveData()
    private val _liveDataIsLoading:MutableLiveData<Boolean> = MutableLiveData()
    val liveDataIsLoading: LiveData<Boolean> = _liveDataIsLoading

    fun fetchUserResponse(newJumlahScan: Int, userId: Int){
        viewModelScope.launch {
            updateUserJumlahScan(newJumlahScan, userId).collect{ response ->
                liveDataResponseUpdateUser.value = response
            }
        }
    }

    fun fetchScannerResponse(img:File){
        _liveDataIsLoading.value = true
        viewModelScope.launch {
            performScanner(img).collect{ response->
                liveDataResponseScanner.value = response
                _liveDataIsLoading.value = false
            }
        }
    }

    fun fetchTranslatorResult(text: String){
        viewModelScope.launch {
            getTranslator(text).collect{ response ->
                liveDataTranslatorResponse.value = response
            }
        }
    }

    suspend fun updateUserJumlahScan(newJumlahScan: Int, userId: Int): Flow<Result<GraphQLResponse>> =
        baksaraRepository.updateUserScan(newJumlahScan, userId)

    suspend fun performScanner(img: File): Flow<Result<ScannerResponse>> =
        baksaraRepository.scannerResult(img)

    suspend fun getTranslator(text:String): Flow<Result<Translatorv2Response>> =
        baksaraRepository.translator2(text)
}