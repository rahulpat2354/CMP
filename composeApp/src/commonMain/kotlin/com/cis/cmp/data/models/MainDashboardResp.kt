package com.cis.cmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainDashboardResp(
    @SerialName("data")
    val data: MainDashData = MainDashData(),
    @SerialName("message")
    val message: String = "",
    @SerialName("success")
    val success: Boolean = false
) {
    @Serializable
    data class MainDashData(
        @SerialName("overview")
        val overview: MainOverview = MainOverview()
    ) {
        @Serializable
        data class MainOverview(
            @SerialName("activeTeamMembers")
            val activeTeamMembers: String = "",
            @SerialName("cashDistributed")
            val cashDistributed: String = "",
            @SerialName("cashReturned")
            val cashReturned: String = "",
            @SerialName("cashToCollect")
            val cashToCollect: String = "",
            @SerialName("expenses")
            val expenses: String = "",
            @SerialName("purchases")
            val purchases: String = "",
            @SerialName("reportsToApprove")
            val reportsToApprove: String = "",
            @SerialName("sales")
            val sales: String = ""
        )
    }
}