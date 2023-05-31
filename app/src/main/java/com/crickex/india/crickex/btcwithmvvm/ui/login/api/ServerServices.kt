package com.crickex.india.crickex.btcwithmvvm.ui.login.api

import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewDemand.DemandTypeData
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.loginModels.Login1Model
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewGr.ViewGrModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ServerServices {
    @Headers("Accept: application/json")
    @GET("DriverAppCoLogin")
    suspend fun callCompanyLogin(
        @Query("DatabaseName") DatabaseName: String,
        @Query("P_ScrtCodeforDrvrApp") P_ScrtCodeforDrvrApp: String,
        @Query("P_Flag") p_flag: String,
    ): Response<Login1Model>

    @Headers("Accept: application/json")
    @GET("EmployeeLoginNew")
    suspend fun callEmployeeLogin(
        @Query("DatabaseName") DatabaseName: String,
        @Query("P_EmpName") P_EmpName: String,
        @Query("P_Password") P_Password: String,
        @Query("P_ImeiCode") P_ImeiCode: String,
        @Query("P_PhoneModel") P_PhoneModel: String,
        @Query("P_VersionID") P_VersionID: String,
    ): Response<Login1Model>

    @Headers("Accept: application/json")
    @GET("GetDemdCategoryTotals")
    suspend fun callTotalDemands(
        @Query("DatabaseName") DatabaseName: String,
        @Query("BlngFrmCode") BlngFrmCode: String,
    ): Response<DemandTypeData>


    @Headers("Accept: application/json")
    @GET("GetDailyDemandList")
    suspend fun callDailyDemandList(
        @Query("DatabaseName") DatabaseName: String?,
        @Query("StatusCategory") StatusCategory: String?,
        @Query("BlnngFrmCode") BlnngFrmCode: String?,
    ): Response<DemandTypeData>

    @Headers("Accept: application/json")
    @GET("GetGRPrintDetials")
    suspend fun callViewGrDetail(
        @Query("DatabaseName") DatabaseName: String?,
        @Query("GRNo") GRNo: String?,
        @Query("BlngFrmCode") BlngFrmCode: String?,
    ): Response<ViewGrModel>

}