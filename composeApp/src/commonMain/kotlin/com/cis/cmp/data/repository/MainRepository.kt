package com.cis.cmp.data.repository

import com.cis.cmp.data.models.MainDashboardResp
import com.cis.cmp.data.network.ApiResult

interface MainRepository {
    suspend fun getMainDashboardData(): ApiResult<MainDashboardResp>
}