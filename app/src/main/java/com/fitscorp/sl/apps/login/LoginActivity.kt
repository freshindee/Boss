package com.fitscorp.sl.apps.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.home.MainActivity
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.register.RegisterActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class LoginActivity : BaseActivity() {


    @Inject
    lateinit var loginVM: LoginActivityVM

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        App.getInstance().appComponent.inject(this)

        btn_signup.setOnClickListener {
            submitLogin()
        }
        txt_donthve_signupnow.setOnClickListener {
            RegisterActivity.startActivity(this)
        }
        txt_donthve_account1.setOnClickListener {
            RegisterActivity.startActivity(this)
        }

    }

    private fun submitLogin(){

        if(txt_email.text.isEmpty())
        {
            Toast.makeText(this,"Pleas enter email",Toast.LENGTH_LONG).show()
            return
        }
        if(txt_password.text.isEmpty())
        {
            Toast.makeText(this,"Pleas enter password",Toast.LENGTH_LONG).show()
            return
        }

        var email=txt_email.text.toString()
        var pass=txt_password.text.toString()



        email="malstore@mailinator.com"
        pass="Test@123"

        var p: UserAuthModel = UserAuthModel()
        p.Username = email
        p.Password = pass

        getLoginToken(p)

    }

    private fun getLoginToken(p: UserAuthModel) {

        subscription.add(loginVM.getUserToken(p).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                      getLoginUser()

                } else {
                    showMessage(R.string.service_loading_fail)
                }
            }, {
                Log.d("====0======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
                showMessage(R.string.service_loading_fail)
            })

        )
    }



    private fun getLoginUser() {

        subscription.add(loginVM.getLoginUser().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {
//

                    MainActivity.startActivity(this@LoginActivity)
//                    RegisterActivity.startActivity(this@LoginActivity)

                } else {
                    showMessage(R.string.service_loading_fail)
                }
            }, {
                Log.d("====1======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
                showMessage(R.string.service_loading_fail)
            })

        )
//
    }


}