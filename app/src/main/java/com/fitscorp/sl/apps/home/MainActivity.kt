package com.fitscorp.sl.apps.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.fitscorp.sl.apps.App

import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.login.LoginActivity
import com.fitscorp.sl.apps.login.LoginActivityVM
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.menu.ContactUsFragment
import com.fitscorp.sl.apps.menu.InfoFragment
import com.fitscorp.sl.apps.menu.LeaderboardFragment
import com.fitscorp.sl.apps.menu.TimelineFragment
import com.fitscorp.sl.apps.register.RegisterActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.widget.AdapterView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fitscorp.sl.apps.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : BaseActivity() {

    var periodArray : List<Periods>?=null
    var timePeriodArr : List<TimePeriodArr>?=null
   // var mianDataList:List<Periods>?=null
   var gd :GradientDrawable?=null

    var incentivefield:String?=null
    var selectPeriod:String?=null
    var StartDate:String?=null
    var EndDate:String?=null

    var pinnerMain:Spinner?=null
    var pinnerMonth:Spinner?=null
    var pinnerYear:Spinner?=null


    @Inject
    lateinit var homeVM: HomeVM

    lateinit var webURL:String

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.getInstance().appComponent.inject(this)

         pinnerMain=findViewById(R.id.spinner)
         pinnerMonth=findViewById(R.id.spinnermonth)
         pinnerYear=findViewById(R.id.spinner_year)


        setViewColors()

        setupBottomMenu()

        getAppFilters()




    }

    private fun updateDropDowns(peridesList: List<Periods>,position:Int){

        periodArray=peridesList



        val spinnerAdapter1 = CompanyAdapter(this, peridesList)
        pinnerMain?.adapter = spinnerAdapter1

        timePeriodArr=peridesList[position].timePeriodArr

      //  val spinnerAdapter2 = CompanyAdapter2(this, peridesList[position].timePeriodArr)
      //  pinnerMonth?.adapter = spinnerAdapter2

      //  val spinnerAdapter3 = CompanyAdapter3(this, peridesList[position].timePeriodArr[position].dropDownList)
     //   pinnerYear?.adapter = spinnerAdapter3


        pinnerMain!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                val monthsList=periodArray?.get(position)!!.timePeriodArr
                val spinnerAdapter22 = CompanyAdapter2(this@MainActivity,monthsList)
                pinnerMonth?.adapter = spinnerAdapter22

                val yearList= timePeriodArr?.get(position)!!.dropDownList
                val spinnerAdapter33 = CompanyAdapter3(this@MainActivity,yearList)
                 pinnerYear?.adapter = spinnerAdapter33

                // incentivefield=dataList.
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        pinnerMonth!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val dataList= timePeriodArr?.get(position)!!.dropDownList
                val spinnerAdapter33 = CompanyAdapter3(this@MainActivity,dataList)
                pinnerYear?.adapter = spinnerAdapter33
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }


    private fun setViewColors() {


        val colorData = homeVM.getData()

        if (colorData.isNotEmpty()) {

            var jsonObj: JSONObject? = null
            try {
                jsonObj = JSONObject(colorData)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val grenColorValue1 = jsonObj!!.optString("color1").toString()
            val grenColorValue2 = jsonObj.optString("color2").toString()
            val grenColorValue3 = jsonObj.optString("color3").toString()

            val logoSmall = jsonObj.optString("logoSmall").toString()
            var jsonArry: JSONArray? = null
            try {
                jsonArry = JSONArray(logoSmall)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            for (i in 0 until jsonArry!!.length()) {
                val item = jsonArry.getJSONObject(i)
                val url = item!!.optString("url").toString()
                Glide.with(this).load(url).into(img_logo)
            }

            txt_page_layout!!.setBackgroundColor(Color.parseColor(grenColorValue3))


            val greenColorValue1 = Color.parseColor(grenColorValue1)
            val greenColorValue2 = Color.parseColor(grenColorValue2)

            //  val top_main_menu = findViewById<View>(R.id.top_main_menu)

            val gd = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(greenColorValue2, greenColorValue1)
            )
            gd.gradientType = GradientDrawable.LINEAR_GRADIENT
            gd.cornerRadius = 0f

            top_main_menu.setBackgroundDrawable(gd)
            lay_filter_second.setBackgroundDrawable(gd)

            bottom_menu_view.setBackgroundColor(Color.parseColor(grenColorValue1))
           // bottom_menu_view.setBackgroundDrawable(gd)

            img_home.setBackgroundResource(R.drawable.user)
        }

    }

    class CompanyAdapter(val context: Context, var peridesList: List<Periods>) : BaseAdapter(), SpinnerAdapter {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

            // setting adapter item height programatically.

         //   val params = view.layoutParams
         //   params.height = 60
          //  view.layoutParams = params

            vh.label.text = peridesList.get(position).incentiveName
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

    class CompanyAdapter2(val context: Context, var timePerieodsList: List<TimePeriodArr>) : BaseAdapter() {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

//            val params = view.layoutParams
//            params.height = 60
//              view.layoutParams = params

            vh.label.text = timePerieodsList.get(position).name
//            vh.label.setOnClickListener {
//
//                timePeriodArr=timePerieodsList[position].dropDownList
//
//               // val spinnerAdapter3 = CompanyAdapter3(context, timePerieodsList[position].dropDownList)
//                //updateDropDowns(mainList,0)
//            }
            return view
        }

        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return timePerieodsList.size
        }

        private class ItemRowHolder(row: View?) {

            val label: TextView

            init {
                this.label = row?.findViewById(R.id.dropdown) as TextView
            }
        }
    }

    class CompanyAdapter3(val context: Context, var peridesList: List<DropDownList>) : BaseAdapter() {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

            // setting adapter item height programatically.

          //  val params = view.layoutParams
         //   params.height = 60
            //  view.layoutParams = params

            vh.label.text = peridesList.get(position).name
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


    private fun setupBottomMenu() {

        lay_timeline.setOnClickListener {
            callTimeline()

        }
        layy_timeline.setOnClickListener {
            callTimeline()
        }
        img_timeline.setOnClickListener {
            callTimeline()
        }
        txt_timeline.setOnClickListener {
            callTimeline()
        }


       // loginVM.loginUserMainResponse.response.data.org
        lay_leaderboard.setOnClickListener {
            callLeaderboard()
        }
        layy_leaderboard.setOnClickListener {
            callLeaderboard()
        }
        img_leaderboard.setOnClickListener {
            callLeaderboard()
        }
        txt_leaderboard.setOnClickListener {
            callLeaderboard()
        }



        lay_info_us.setOnClickListener {
            callInfo()
        }
        layy_info_us.setOnClickListener {
            callInfo()
        }
        img_info_us.setOnClickListener {
            callInfo()
        }
        txt_info_us.setOnClickListener {
            callInfo()
        }

        lay_contact_us.setOnClickListener {
            callContact()
        }
        layy_contact_us.setOnClickListener {
            callContact()
        }
        img_contact_us.setOnClickListener {
            callContact()
        }
        txt_contact_us.setOnClickListener {
            callContact()
        }
    }
    fun callTimeline(){
        txt_page_heading.text="Timeline"
        top_main_menu.visibility=View.VISIBLE
        lay_filter_second.visibility=View.VISIBLE
        navigateToFragment(TimelineFragment.newInstance(this@MainActivity,"",""))
    }
    fun callContact(){
        img_home.setBackgroundResource(R.drawable.sendbutton)
        txt_page_heading.text="Contact Us"
        top_main_menu.visibility=View.GONE
        lay_filter_second.visibility=View.GONE
        navigateToFragment(ContactUsFragment.newInstance("",""))
    }
    fun callLeaderboard(){
        txt_page_heading.text="Leaderboard"
        top_main_menu.visibility=View.VISIBLE
        lay_filter_second.visibility=View.VISIBLE
        navigateToFragment(LeaderboardFragment.newInstance(this@MainActivity,"",""))
    }
    fun callInfo(){
        txt_page_heading.text="Info"
        top_main_menu.visibility=View.VISIBLE
        lay_filter_second.visibility=View.GONE
        navigateToFragment(InfoFragment.newInstance(webURL,""))
    }
    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_frame, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }



    private fun getAppFilters() {

        subscription.add(homeVM.getAppFilters().subscribeOn(
            Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                    val res:HomeMainResponse= homeVM.dataObj!!
                    if(res.response.code==200){

                        val mainList= res.response.periods

                        webURL=mainList[0].url

                        updateDropDowns(mainList,0)




                    }else{
                        showMessage(R.string.service_loading_fail)
                    }


                } else {
                    showMessage(R.string.service_loading_fail)
                }
            }, {
               Log.d("====0======",it.stackTrace.toString())
                progressBar.visibility = View.GONE
                showMessage(R.string.service_loading_fail)
            })

        )
    }




}
