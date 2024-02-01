package com.example.rightdeedtest.repository

import com.example.rightdeedtest.data.remote.Api
import com.example.rightdeedtest.model.CodeResponseMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val api: Api,
    private val codeResponseMapper: CodeResponseMapper,
) {

    suspend fun login(phone: String) = withContext(Dispatchers.IO)  {
        try {
            val codeResponse = api.getCode(login = phone)
            return@withContext Result.success(codeResponseMapper.dataToDomain(codeResponse))
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
}