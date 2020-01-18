package com.fitscorp.sl.apps.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.login.LoginActivity
import com.fitscorp.sl.apps.login.LoginActivityVM
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.register.RegisterActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var loginVM: LoginActivityVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        App.getInstance().appComponent.inject(this)

        //LoginActivity.startActivity(this@SplashActivity)
        if(loadLocalData()){
            MainActivity.startActivity(this@SplashActivity)
        }
        else {
            LoginActivity.startActivity(this@SplashActivity)
        }


    }
    
    fun loadLocalData(): Boolean {
        var isDataAvailable:Boolean = false
        val data = loginVM.getData()

        val gson = Gson()
        val type = object : TypeToken<LoginUserMainResponse>() {}.type

        if(gson.fromJson<LoginUserMainResponse>(data, type)==null){

        }else{
            isDataAvailable=true
        }

        return isDataAvailable
    }

}
