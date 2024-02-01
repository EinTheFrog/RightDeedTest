package com.example.rightdeedtest.model

import com.example.rightdeedtest.model.data.CodeResponseData
import com.example.rightdeedtest.model.domain.CodeResponseDomain
import com.example.rightdeedtest.model.domain.UserStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CodeResponseMapper @Inject constructor() {

    fun dataToDomain(codeResponseData: CodeResponseData): CodeResponseDomain {
        return CodeResponseDomain(
            code = codeResponseData.code,
            status = UserStatus.fromString(codeResponseData.status),
        )
    }
}