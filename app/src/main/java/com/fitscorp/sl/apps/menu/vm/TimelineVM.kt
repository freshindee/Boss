package com.fitscorp.sl.apps.menu.vm

import android.content.SharedPreferences
import android.util.Log
import com.fitscorp.sl.apps.common.MSG_FAILED_REQUEST
import com.fitscorp.sl.apps.common.MSG_SUCCESS
import com.fitscorp.sl.apps.common.State
import com.fitscorp.sl.apps.common.getAuthToken
import com.fitscorp.sl.apps.menu.data.Leaderboard

import com.fitscorp.sl.apps.menu.data.TimelineMainRespone
import com.fitscorp.sl.apps.rest.ApiService
import javax.inject.Inject

class TimelineVM @Inject constructor(val apiService: ApiService,
val sharedPref: SharedPreferences
) {

    var dataObj : TimelineMainRespone?=null

    fun getTimeLineStore() = apiService.getTimeineData(sharedPref.getAuthToken(),"106","MONTHLY","2019-07-01T00:00:00.000Z","2019-07-31T11:59:59.000Z").map {
        if (it.isSuccessful && it.body() != null) {

            val dataOb = it.body()

            dataObj=dataOb

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }


}
