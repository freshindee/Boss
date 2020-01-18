package com.fitscorp.sl.apps.rest;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;


public class ApiClient {
    //Live

    public static final String BASE_URL_live = "https://livehappy.ayubo.life/custom/service/v5/rest.php/";
    public static final String BASE_URL_live_Old = "https://livehappy.ayubo.life/custom/service/v4_1_custom/rest.php/";
    public static final String BASE_URL = "https://livehappy.ayubo.life/";
    public static final String BASE_URL_entypoint = "https://livehappy.ayubo.life/index.php?entryPoint=";
    public static final String BASE_URL_GOALEX = "http://connect.ayubo.life/api/v1/goal/";
    public static final String BASE_URL_NEW = "https://livehappy.ayubo.life/api.ayubo.life/public/api/v1/";
    public static final String BASE_URL_NEW_SHORT = "https://livehappy.ayubo.life/api.ayubo.life/public/api/";
    public static final String BASE_URL_AUTH = "https://livehappy.ayubo.life/api.ayubo.life/public/";


    public static final String BASE_URL_REPORT = "https://livehappy.ayubo.life/api.ayubo.life/public/api/v1/report/";

    public static final String BASE_URL_NOTIFICATION = "https://livehappy.ayubo.life/api.ayubo.life/public/api/v1/notification";

    public static final String BASE_URL_CONNECT = "http://connect.ayubo.life/api/v1/";



    public static Retrofit getClientBase() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient clientt;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientt = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_NEW_SHORT)
                .client(clientt)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit;
    }

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient clientt;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientt = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_live)
                .client(clientt)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit;
    }





    public static Retrofit getGoalExApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient clientt;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientt = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_GOALEX)
                .client(clientt)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }


    public static Retrofit getReportsApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient clientt;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientt = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_REPORT)
                .client(clientt)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit;
    }

    public static Retrofit getNewApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient clientt;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientt = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_NEW)
                .client(clientt)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit;
    }
    public static Retrofit getNewApiClient_AUTH() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient clientt;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientt = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_AUTH)
                .client(clientt)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit;
    }
    public static Retrofit getLeaderboardClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient clientt;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientt = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_live)
                .client(clientt)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        return retrofit;
    }
}
