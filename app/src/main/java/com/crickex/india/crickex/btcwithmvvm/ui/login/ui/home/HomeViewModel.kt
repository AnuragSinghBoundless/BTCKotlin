package com.crickex.india.crickex.btcwithmvvm.ui.login.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewDemand.DemandTypeData
import com.crickex.india.crickex.btcwithmvvm.ui.login.repo.CompanyLoginRepo
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppConstants
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.States
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: CompanyLoginRepo) : ViewModel() {
    private val appConstants = AppConstants()

    fun callTotalDemands(billingFirm: String) {
        viewModelScope.launch {
            repo.totalDemand(appConstants.DBTesting, billingFirm)
        }
    }

    fun callDailyDemand(status: String, billingFirm: String) {
        viewModelScope.launch {
            repo.callDemandList(appConstants.DBTesting, status, billingFirm)
        }

    }

    val totalData: LiveData<States<DemandTypeData>>
        get() = repo.totalD

    val dailyDemandList: LiveData<States<DemandTypeData>>
        get() = repo.dailyD

}