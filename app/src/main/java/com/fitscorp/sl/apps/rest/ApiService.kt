package com.fitscorp.sl.apps.rest



import com.fitscorp.sl.apps.home.HomeMainResponse
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.menu.data.Contact
import com.fitscorp.sl.apps.menu.data.Leaderboard

import com.fitscorp.sl.apps.menu.data.TimelineMainRespone
import com.fitscorp.sl.apps.register.Register
import com.fitscorp.sl.apps.register.RegisterMainResponse
import com.fitscorp.sl.apps.register.StoreMainResponse
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


       @GET("patients_of_doctor")
    fun getPatientOfDoctor(@Header("Authorization") authorization: String): Flowable<Response<Any>>



    @POST("auth/mobile/token")
    @Headers("Content-Type: application/json")
    fun getUserToken(@Body post: UserAuthModel): Flowable<Response<LoginServiceMainResponse>>



    @GET("user/incentive")
    fun getAppFilters(@Header("Authorization") authorization: String): Flowable<Response<HomeMainResponse>>


    @POST("merchant/contact")
    @Headers("Content-Type: application/json")
    fun sendMessage(@Header("Authorization") authorization: String,
                    @Body post: Contact): Flowable<Response<RegisterMainResponse>>

    @GET("user/kpi/tbl/result?")
    fun getLeaderBoardData(@Header("Authorization") authorization: String,
                       @Query("incentivefield") incentivefield: String,
                       @Query("selectPeriod") selectPeriod: String,
                       @Query("StartDate") StartDate: String,
                       @Query("EndDate") EndDate: String,
                       @Query("moduleType") moduleType: String,
                       @Query("tableDisplay") tableDisplay: String): Flowable<Response<Leaderboard>>

    @GET("user/kpi/tbl/result?")
    fun getTimeineData(@Header("Authorization") authorization: String,
                           @Query("incentivefield") incentivefield: String,
                           @Query("selectPeriod") selectPeriod: String,
                           @Query("StartDate") StartDate: String,
                           @Query("EndDate") EndDate: String): Flowable<Response<TimelineMainRespone>>

//    incentivefield=106&
//    //selectPeriod=MONTHLY&
//    //StartDate=2019-06-01T00:00:00.000Z&
//    //EndDate=2019-06-30T11:59:59.000Z
    //https://dev-incentive-api-vc.azurewebsites.net/api/v1/

    // user/kpi/tbl/result?
    // incentivefield=106&
    // selectPeriod=MONTHLY&
    // StartDate=2019-07-01T00:00:00.000Z&
    // EndDate=2019-07-31T11:59:59.000Z&
    // moduleType=rep&
    // tableDisplay=true
//    @POST("merchant/mobile/user")
//    @Headers("Content-Type: application/json")
//    fun registerUser(@Header("Authorization") authorization: String,
//                    @Body post: Contact): Flowable<Response<RegisterMainResponse>>


    @POST("merchant/mobile/user")
    @Headers("Content-Type: application/json")
    fun registerUser(@Body post: Register): Flowable<Response<RegisterMainResponse>>


    //    {
//        "lastName" : "yes",
//        "email" : "as3@mailinator.com",
//        "firstName" : "harsh",
//        "storeId" : 42,
//        "organizationId" : 75,
//        "mobileNo" : 3222233333,
//        "salesId" : 333,
//        "currentStatus" : "pending",
//        "userRole" : "STORE_MANAGER",
//        "userType" : "ORGANIZATION",
//        "salesIdList" : []
//    }

    @POST("merchant/mobile/stores")
    @FormUrlEncoded
    fun getStoreData(@Header("Authorization") authorization: String,
                     @Field("organizationId") organizationId: String): Flowable<Response<StoreMainResponse>>




    @GET("user/mobile/profile")
    fun getUserLogin(@Header("Authorization") authorization: String): Flowable<Response<LoginUserMainResponse>>

}