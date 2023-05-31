package com.crickex.india.crickex.btcwithmvvm.ui.login.view_modelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crickex.india.crickex.btcwithmvvm.ui.login.repo.CompanyLoginRepo
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.gallery.GalleryViewModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.home.HomeViewModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.viewModel.LoginViewModel

class LoginFactory(private val repo: CompanyLoginRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)){
            return GalleryViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}