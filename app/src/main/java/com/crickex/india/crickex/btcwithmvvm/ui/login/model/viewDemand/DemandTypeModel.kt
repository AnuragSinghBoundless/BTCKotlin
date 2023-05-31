package com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewDemand

data class DemandTypeModel(
    val DemdCategory: Int,
    val DemdCategoryParticular: String,
    val TotalDemdAmt: Double,
    val DemandID: String,
    val DemdTypeID: String,
    val DemdTypeParticulars: String,
    val DemdFor: String,
    val DemdAmt: String,
    val AutoNarration: String,
    val DemdStatusParticular: String,
    val DemdStatus: Int,
    val MsgCount: String,
    val DemdDate: String,
    val Remarks: String,
    val CreatedBy: String,
    val ApprovedBy: String,
)