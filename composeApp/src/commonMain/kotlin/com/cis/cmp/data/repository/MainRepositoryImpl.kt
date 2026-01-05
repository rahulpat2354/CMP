package com.cis.cmp.data.repository

import com.cis.cmp.data.api.ApiService
import com.cis.cmp.data.models.MainDashboardResp
import com.cis.cmp.data.network.ApiResult
import com.cis.cmp.data.preference.PreferenceManager

class MainRepositoryImpl(val apiService: ApiService, val preferenceManager: PreferenceManager): MainRepository {

    override suspend fun getMainDashboardData(): ApiResult<MainDashboardResp> {
        return apiService.getDashboardOverviewData()
    }
}