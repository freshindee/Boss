package com.fitscorp.sl.apps.register

import android.content.SharedPreferences
import android.util.Log
import com.fitscorp.sl.apps.common.MSG_FAILED_REQUEST
import com.fitscorp.sl.apps.common.MSG_SUCCESS
import com.fitscorp.sl.apps.common.State
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.rest.ApiService
import javax.inject.Inject

class RegisterActivityVM @Inject constructor(val apiService: ApiService,
                    val sharedPref: SharedPreferences
){

    var dataObj : RegisterMainResponse?=null

    var storeDataObj : StoreMainResponse?=null


    fun getStoreData(organizationId:String) = apiService.getStoreData("",organizationId).map {
        if(it.isSuccessful && it.body()!= null){

            storeDataObj=it.body()

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........",it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }





    fun registerUser(user:Register) = apiService.registerUser(user).map {
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





}
