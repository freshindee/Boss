package com.fitscorp.sl.apps.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.App.context
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.home.DropDownList
import com.fitscorp.sl.apps.home.MainActivity
import com.fitscorp.sl.apps.login.LoginActivity
import com.fitscorp.sl.apps.login.LoginActivityVM
import com.fitscorp.sl.apps.menu.data.Contact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.component_register_feild.*
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    var userRole:String?=null
    var storeId:Int?=null

    @Inject
    lateinit var registerActivityVM: RegisterActivityVM

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        App.getInstance().appComponent.inject(this)




        (layout_email.findViewById(R.id.register_feild_lable) as TextView).text = "Email"
        (layout_firstname.findViewById(R.id.register_feild_lable) as TextView).text = "First Name"
        (layout_lastname.findViewById(R.id.register_feild_lable) as TextView).text = "Last Name"
        (layout_mobilenumber.findViewById(R.id.register_feild_lable) as TextView).text = "Mobile Number"
        (layout_saleid.findViewById(R.id.register_feild_lable) as TextView).text = "2Degrees Store ID"
        (layout_storeid.findViewById(R.id.register_feild_lable) as TextView).text = "2Degrees Sales ID"

        spinner_view.visibility=View.GONE


        val colors = arrayOf("SalesRep","Store")
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,colors)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        edt_title_user_spinner.adapter = adapter;
        edt_title_user_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

                if(parent.getItemAtPosition(position)=="Store"){
                    getStoreData("75")
                    userRole="STORE_MANAGER"



                }else{
                    userRole="SALES_REP"
                    spinner_view.visibility=View.GONE
                    (layout_storeid.findViewById(R.id.register_feild_text) as EditText).setText("")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>){
            }
        }

        btn_register.setOnClickListener {
            registerUser()
        }

    }



    private fun registerUser() {


       val email= (layout_email.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val fname= (layout_firstname.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val lname= (layout_lastname.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val mobilenumber= (layout_mobilenumber.findViewById(R.id.register_feild_text) as TextView).text.toString()
      // val storeId= (layout_storeid.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val salesid=(layout_saleid.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val userType="ORGANIZATION"
       val currentStatus="pending"
       val organizationId="75"


       var salelist=ArrayList<String>()
     //  salelist.add("")

        val salesIdList=salelist.toString()
        if(email.isEmpty() || fname.isEmpty() || lname.isEmpty() || mobilenumber.isEmpty() || salesid.isEmpty()){
            Toast.makeText(this@RegisterActivity,"Please enter required details",Toast.LENGTH_LONG).show()
            return
        }

        var user: Register = Register()
        user.email=email
        user.firstName=fname
        user.lastName=lname
        user.mobileNo=mobilenumber.toInt()
        user.salesId=salesid.toInt()
        user.salesIdList=salelist
        user.userType=userType
        user.currentStatus=currentStatus
        user.organizationId=organizationId.toInt()
        user.userRole=userRole
        user.storeId=storeId

        subscription.add(registerActivityVM.registerUser(user).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                    LoginActivity.startActivity(this@RegisterActivity)

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


    private fun getStoreData(organizationId: String) {

        subscription.add(registerActivityVM.getStoreData(organizationId).subscribeOn(
            Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                 val storeDataObj=registerActivityVM.storeDataObj

                    spinner_view.visibility=View.VISIBLE

                    val subDataList= storeDataObj!!.response.data
                    val spinnerAdapter3 = CompanyAdapter3(this@RegisterActivity, subDataList)
                     edt_title_user_spinner2?.adapter = spinnerAdapter3


                    edt_title_user_spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            val storeName= subDataList.get(position).storeName

                            storeId =subDataList.get(position).id

                            (layout_storeid.findViewById(R.id.register_feild_text) as EditText).setText(storeName)
                           // (layout_storeid.findViewById(R.id.register_feild_text) as EditText).text = storeName
                          //  val spinnerAdapter3 = MainActivity.CompanyAdapter3(this@MainActivity, dataList)
                          //  spinner_year?.adapter = spinnerAdapter3
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {
                        }
                    }


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
    class CompanyAdapter3(val context: Context, var peridesList: List<StoreMain>) : BaseAdapter() {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_store_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

            // setting adapter item height programatically.

            val params = view.layoutParams
            params.height = 60
            //  view.layoutParams = params

            vh.label.text = peridesList.get(position).storeName
            return view
        }

        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return peridesList.size
        }

        private class ItemRowHolder(row: View?) {

            val label: TextView

            init {
                this.label = row?.findViewById(R.id.dropdown) as TextView
            }
        }
    }
}
