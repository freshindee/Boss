package com.fitscorp.sl.apps.home

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import javax.inject.Inject

class HomeVM @Inject constructor(val apiService: ApiService,
                                 val sharedPref: SharedPreferences
){

    var dataObj : HomeMainResponse?=null



    fun getAppFilters() = apiService.getAppFilters(sharedPref.getAuthToken()).map {
        if(it.isSuccessful && it.body()!= null){

            dataObj=it.body()

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........",it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }



    @TypeConverter
    fun detailsToJson(value: LoginUserMainResponse): String {
        return Gson().toJson(value)
    }

    fun getData():String{
        return  sharedPref.getCashedDataByName("color_data")
    }

}