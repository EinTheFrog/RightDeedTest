package com.example.rightdeedtest.repository

import com.example.rightdeedtest.data.remote.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    private val api: Api,
) {

    suspend fun getToken(phone: String, code: String) = withContext(Dispatchers.IO) {
        try {
            val token = api.getToken(login = phone, password = code)
            return@withContext Result.success(token)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
}