package com.crickex.india.crickex.btcwithmvvm.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.loginModels.Login1Model
import com.crickex.india.crickex.btcwithmvvm.ui.login.repo.CompanyLoginRepo
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppConstants
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.States
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: CompanyLoginRepo) : ViewModel() {
    private val appConstants = AppConstants()


    fun callEmpLoin(empName: String, pass: String, imei: String, model: String, version: String) {
        viewModelScope.launch {
            repo.callLoginEmployee(
                appConstants.DBName,
                empName,
                pass,
                imei,
                model,
                version
            )
        }
    }

    fun callCompanyLogin(scrctCode: String, flag: String) {
        viewModelScope.launch {
            repo.callLoginCompany(appConstants.DBName, scrctCode, flag)
        }
    }

    //company login....
    val login: LiveData<States<Login1Model>>
        get() = repo.loginCompany

    //employee login....
    val loginEmp: LiveData<States<Login1Model>>
        get() = repo.loginEmployee


}