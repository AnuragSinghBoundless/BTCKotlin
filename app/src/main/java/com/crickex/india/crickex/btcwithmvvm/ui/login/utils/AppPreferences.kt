package com.crickex.india.crickex.btcwithmvvm.ui.login.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.crickex.india.crickex.btcwithmvvm.ui.login.LoginActivity

class AppPreferences {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var context: Context
    var PRIVATEMODE: Int = 0

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(Constants.APP_PREFERENCES_DATA, PRIVATEMODE)
        editor = pref.edit()
    }

    companion object {
        val USERNAME = "user_name"
        val EMP_ID = "emp_id"
        val PASSWORD1 = "password1"
        val POPUP_STATUS = "popup_status"
        var BILLING_FIRM = "billing_firm"
        val IS_LOGIN = "isLoggedIn"
        val KEY_NAME = "username"
        val PASSWORD = "password"
    }

    fun saveUserName(value: String) {
        editor.putString(USERNAME, value)
        editor.commit()
    }

    fun getUserName(): String? {
        return pref.getString(USERNAME, null)
    }

    fun savePassword(value: String) {
        editor.putString(PASSWORD1, value)
        editor.commit()
    }

    fun getPassword(): String? {
        return pref.getString(PASSWORD1, null)
    }

    fun saveEmpCode(value: String) {
        editor.putString(EMP_ID, value)
        editor.commit()
    }

    fun getEMPID(): String? {
        return pref.getString(EMP_ID, null)
    }

    fun saveSinglePopupStatus(value: String) {
        editor.putString(POPUP_STATUS, value)
        editor.commit()
    }

    fun getPopupStatus(): String? {
        return pref.getString(POPUP_STATUS, null)
    }

    fun saveBillingFirm(value: String) {
        editor.putString(BILLING_FIRM, value)
        editor.commit()
    }

    fun getBillingFirm(): String? {
        return pref.getString(BILLING_FIRM, null)
    }


    fun createLoginSession(name: String, password: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_NAME, name)
        editor.putString(PASSWORD, password)
        editor.commit()
    }

    fun checkLogin() {
        if (this.isLoggedIn()) {
            var i: Intent = Intent(context, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    fun getUserDetails():HashMap<String,String>{
        var user:Map<String,String> = HashMap<String,String>()
        (user as HashMap).put(KEY_NAME,pref.getString(KEY_NAME,null)!!)
        (user as HashMap).put(PASSWORD,pref.getString(PASSWORD,null)!!)
        return user
    }

    fun logoutUser(){
        editor.clear()
        editor.commit()
        var i: Intent = Intent(context, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags= Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }

    fun isLoggedIn():Boolean{
        return pref.getBoolean(IS_LOGIN,false)
    }

}