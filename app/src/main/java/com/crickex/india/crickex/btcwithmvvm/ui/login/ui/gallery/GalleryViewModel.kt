package com.crickex.india.crickex.btcwithmvvm.ui.login.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewGr.ViewGrModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.repo.CompanyLoginRepo
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppConstants
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.States
import kotlinx.coroutines.launch

class GalleryViewModel(private val repo: CompanyLoginRepo) : ViewModel() {

    val appConstants=AppConstants()

    fun callViewGrDetatils(grNo:String,BillingFirm:String){
        viewModelScope.launch {
            repo.callViewGrDetail(appConstants.DBTesting,grNo,BillingFirm)
        }
    }

    val grData:LiveData<States<ViewGrModel>>
    get() = repo.viewGrD
}