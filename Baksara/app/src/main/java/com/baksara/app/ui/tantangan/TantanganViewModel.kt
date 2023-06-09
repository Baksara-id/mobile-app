package com.baksara.app.ui.tantangan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baksara.app.repository.BaksaraRepository
import com.baksara.app.response.GraphQLResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TantanganViewModel(private val baksaraRepository: BaksaraRepository): ViewModel() {
    val liveDataTantangan: MutableLiveData<Result<GraphQLResponse>> = MutableLiveData()
    val liveDataDetailTantangan: MutableLiveData<Result<GraphQLResponse>> = MutableLiveData()
    val liveDataTantanganSudah: MutableLiveData<Result<GraphQLResponse>> = MutableLiveData()
    val liveDataResponseSubmit: MutableLiveData<Result<GraphQLResponse>> = MutableLiveData()
    val liveDataResponseUpdateUserEXP: MutableLiveData<Result<GraphQLResponse>> = MutableLiveData()
    val liveDataResponseUpdateUserLevelEXP: MutableLiveData<Result<GraphQLResponse>> = MutableLiveData()
    private val _liveDataIsLoading:MutableLiveData<Boolean> = MutableLiveData()
    val liveDataIsLoading: LiveData<Boolean> = _liveDataIsLoading

    fun fetchAllTantanganUser(userId: Int){
        _liveDataIsLoading.value = true
        viewModelScope.launch {
            getAllTantanganUser(userId).collect{ tantangans->
                liveDataTantangan.value = tantangans
                _liveDataIsLoading.value = false
            }
        }
    }

    fun fetchDetailTantangan(id: Int){
        _liveDataIsLoading.value = true
        viewModelScope.launch {
            getDetailTantangan(id).collect{ tantangan ->
                liveDataDetailTantangan.value = tantangan
                _liveDataIsLoading.value = false
            }
        }
    }

    fun fetchRiwayatTantanganUser(userId: Int){
        _liveDataIsLoading.value = true
        viewModelScope.launch {
            getRiwayatTantangan(userId).collect{ riwayat->
                liveDataTantanganSudah.value = riwayat
                _liveDataIsLoading.value = false
            }
        }
    }

    fun fetchResponseSubmmit(userId: Int, tantanganId: Int, jawaban:String) {
        viewModelScope.launch {
            jawabTantangan(userId,tantanganId, jawaban).collect{ response ->
                liveDataResponseSubmit.value = response
            }
        }
    }

    fun fetchResponseUpdateUserExp(newEXP: Int, userId: Int){
        viewModelScope.launch {
            updateUserEXP(newEXP, userId).collect{ response ->
                liveDataResponseUpdateUserEXP.value = response
            }
        }
    }

    fun fetchResponseUpdateUserLevel(newLevel: Int, userId: Int, newEXP: Int){
        viewModelScope.launch {
            updateUserLevel(newLevel, userId, newEXP).collect{ response ->
                liveDataResponseUpdateUserLevelEXP.value = response
            }
        }
    }

    suspend fun getAllTantanganUser(userId: Int): Flow<Result<GraphQLResponse>> =
        baksaraRepository.getAllTantanganBelum(userId)

    suspend fun getDetailTantangan(id: Int) : Flow<Result<GraphQLResponse>> =
        baksaraRepository.getDetailTantangan(id)

    suspend fun getRiwayatTantangan(userId: Int): Flow<Result<GraphQLResponse>> =
        baksaraRepository.getAllTantanganSudah(userId)

    suspend fun jawabTantangan(userId: Int, tantanganId: Int, jawaban:String): Flow<Result<GraphQLResponse>> =
        baksaraRepository.submitJawabanTantangan(userId, tantanganId, jawaban)

    suspend fun updateUserEXP(newEXP: Int, userId: Int): Flow<Result<GraphQLResponse>> =
        baksaraRepository.updateUserEXP(newEXP, userId)

    suspend fun updateUserLevel(newLevel: Int, userId: Int, newEXP: Int): Flow<Result<GraphQLResponse>> =
        baksaraRepository.updateUserLevel(newLevel, userId, newEXP)
}