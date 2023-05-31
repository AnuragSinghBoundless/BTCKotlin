package com.crickex.india.crickex.btcwithmvvm.ui.login.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.ServerServices
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewDemand.DemandTypeData
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.loginModels.Login1Model
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewGr.ViewGrModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.States

class CompanyLoginRepo(private var serverServices: ServerServices/*, private var mainDB: MainDB*/) {

    private val companyLoginLiveData = MutableLiveData<States<Login1Model>>()
    val loginCompany: LiveData<States<Login1Model>>
        get() = companyLoginLiveData

    private val employeeLoginLiveData = MutableLiveData<States<Login1Model>>()
    val loginEmployee: LiveData<States<Login1Model>>
        get() = employeeLoginLiveData


    private val totalDemand = MutableLiveData<States<DemandTypeData>>()
    val totalD: LiveData<States<DemandTypeData>>
        get() = totalDemand


    private val dailyDemandList = MutableLiveData<States<DemandTypeData>>()
    val dailyD: LiveData<States<DemandTypeData>>
        get() = dailyDemandList


    private val viewGrDetail = MutableLiveData<States<ViewGrModel>>()
    val viewGrD: LiveData<States<ViewGrModel>>
        get() = viewGrDetail


    suspend fun callViewGrDetail(db: String, grNo: String, blngFirm: String) {
        try {
            val result = serverServices.callViewGrDetail(db, grNo, blngFirm)
            if (result.body() != null) {
                viewGrDetail.postValue(States.SUCCESS(result.body()))
            } else {
                viewGrDetail.postValue(
                    States.FAILURE(
                        result.code().toString() + " " + result.message().toString()
                    )
                )
            }

        } catch (e: Exception) {
            viewGrDetail.postValue(States.FAILURE(e.message))
        }
    }

    suspend fun totalDemand(db: String, billinFirm: String) {

        try {
            val result = serverServices.callTotalDemands(db, billinFirm)

            if (result?.body() != null) {
                totalDemand.postValue(States.SUCCESS(result.body()))
            } else {
                totalDemand.postValue(
                    States.FAILURE(
                        result.code().toString() + " " + result.message().toString()
                    )
                )
            }
        } catch (e: Exception) {
            totalDemand.postValue(States.FAILURE(e.message))
        }


    }

    suspend fun callDemandList(db: String, statusCat: String, billingFirm: String) {
        try {
            val result = serverServices.callDailyDemandList(db, statusCat, billingFirm)
            if (result?.body() != null) {
                dailyDemandList.postValue(States.SUCCESS(result.body()))
            } else {
                dailyDemandList.postValue(
                    States.FAILURE(
                        result.code().toString() + " " + result.message().toString()
                    )
                )
            }
        } catch (e: Exception) {
            dailyDemandList.postValue(States.FAILURE(e.message))
        }


    }

    suspend fun callLoginCompany(db: String, scrctCode: String, flag: String) {
        try {
            val result = serverServices.callCompanyLogin(db, scrctCode, flag)
            if (result.body() != null) {
                companyLoginLiveData.postValue(States.SUCCESS(result.body()))
            } else {

                companyLoginLiveData.postValue(
                    States.FAILURE(
                        result.code().toString() + " " + result.message().toString()
                    )
                )
            }
        } catch (e: Exception) {
            companyLoginLiveData.postValue(States.FAILURE(e.message.toString()))
        }

    }


    suspend fun callLoginEmployee(
        DatabaseName: String, P_EmpName: String, P_Password: String,
        P_ImeiCode: String, P_PhoneModel: String, P_VersionID: String,
    ) {

        try {
            val result = serverServices.callEmployeeLogin(
                DatabaseName,
                P_EmpName,
                P_Password,
                P_ImeiCode,
                P_PhoneModel,
                P_VersionID
            )
            if (result?.body() != null) {
              //  mainDB.mainDao().saveUserData(result.body()!![0])
                employeeLoginLiveData.postValue(States.SUCCESS(result.body()))
            } else {
                companyLoginLiveData.postValue(
                    States.FAILURE(
                        result.code().toString() + " " + result.message().toString()
                    )
                )
            }
        } catch (e: Exception) {
            employeeLoginLiveData.postValue(States.FAILURE(e.message.toString()))
        }

    }
}