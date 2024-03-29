package com.wms.wms.data.api.login

import com.wms.wms.data.model.response.LoginResponse
import com.wms.wms.data.model.response.ApiResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginApi(val dataSource: LoginDataSource) {
    suspend fun login(username: String, password: String): ApiResult<LoginResponse> {
        return dataSource.login(username, password)
    }
}