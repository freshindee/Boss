package com.fitscorp.sl.apps.menu.data

import com.google.gson.annotations.SerializedName


data class Leaderboard (

    @SerializedName("response") val response : LeaderboardMainResponse)

data class LeaderboardMainResponse (

    @SerializedName("dataArr") val dataArr : LeaderboardData,
    @SerializedName("message") val message : String,
    @SerializedName("code") val code : Int,
    @SerializedName("success") val success : Boolean
)
data class LeaderboardData (
    @SerializedName("TableBody") val response : List<LeaderboardMainData>
)

data class LeaderboardMainData (

    @SerializedName("Average_Sales") val average_Sales : String,
    @SerializedName("Average_Sales_id") val average_Sales_id : Int,
    @SerializedName("Average_Sales_val") val average_Sales_val : Double,
    @SerializedName("Average_Sales_type") val average_Sales_type : String,
    @SerializedName("Average_Sales_shortName") val average_Sales_shortName : String,
    @SerializedName("Average_Sales_ColorSetting") val average_Sales_ColorSetting : String,
    @SerializedName("Average_Sales_MeasureType") val average_Sales_MeasureType : String,
    @SerializedName("SalesId") val salesId : String,
    @SerializedName("StoreId") val storeId : Int,
    @SerializedName("Region") val region : Int,
    @SerializedName("StoreName") val storeName : String,
    @SerializedName("Average_Count") val average_Count : String,
    @SerializedName("Average_Count_id") val average_Count_id : Int,
    @SerializedName("Average_Count_val") val average_Count_val : Double,
    @SerializedName("Average_Count_type") val average_Count_type : String,
    @SerializedName("Average_Count_shortName") val average_Count_shortName : String,
    @SerializedName("Average_Count_ColorSetting") val average_Count_ColorSetting : String,
    @SerializedName("Average_Count_MeasureType") val average_Count_MeasureType : String,
    @SerializedName("SalesRep") val salesRep : String,
    @SerializedName("Point") val point : String,
    @SerializedName("Point_id") val point_id : Int,
    @SerializedName("Point_val") val point_val : Double,
    @SerializedName("Point_type") val point_type : String,
    @SerializedName("Point_shortName") val point_shortName : String,
    @SerializedName("Point_ColorSetting") val point_ColorSetting : String,
    @SerializedName("Point_MeasureType") val point_MeasureType : String,
    @SerializedName("KPIId") val kPIId : Int,
    @SerializedName("total") val total : Double,
    @SerializedName("position") val position : Int
)
