package com.baksara.app.repository

import com.baksara.app.local.UserPreferences
import com.baksara.app.network.ApiService

class AuthRepository(
    private val userpref: UserPreferences,
    private val service: ApiService
) {
    suspend fun register(){

    }

    suspend fun login(){

    }

    suspend fun logout(){

    }

    suspend fun getAllBadge(){

    }

    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null

        fun getInstance(userpref: UserPreferences, apiService: ApiService): AuthRepository {
            return INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = AuthRepository(userpref,apiService)
                }
                return INSTANCE as AuthRepository
            }
        }
    }
}