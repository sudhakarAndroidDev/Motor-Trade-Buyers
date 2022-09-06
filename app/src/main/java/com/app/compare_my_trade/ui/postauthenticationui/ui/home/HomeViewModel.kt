package com.app.compare_my_trade.ui.postauthenticationui.ui.home

import android.app.Application
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.base.BaseNavigator
import com.app.compare_my_trade.ui.base.BaseViewModel
import org.koin.core.KoinComponent

data class HomeViewModel( val img1: String,val text1: String,val text2: String,val text3: String,val id: String,val fuel_type: String,val transmission: String,val odomewter: String,val drive_type: String,val favorite_status:String,val model:String) {

}