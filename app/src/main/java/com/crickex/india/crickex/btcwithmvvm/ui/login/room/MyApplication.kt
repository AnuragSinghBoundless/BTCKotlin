package com.crickex.india.crickex.btcwithmvvm.ui.login.room

import android.app.Application
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.RetrofitHelper
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.ServerServices
import com.crickex.india.crickex.btcwithmvvm.ui.login.repo.CompanyLoginRepo

class MyApplication /*: Application()*/ {
  /*  lateinit var loginRepo: CompanyLoginRepo
    override fun onCreate() {
        super.onCreate()
        initialization()
    }

    private fun initialization() {
        val loginService = RetrofitHelper.getInstance().create(ServerServices::class.java)
        val database = MainDB.getDB(applicationContext)
        loginRepo = CompanyLoginRepo(loginService, database)
    }*/
}