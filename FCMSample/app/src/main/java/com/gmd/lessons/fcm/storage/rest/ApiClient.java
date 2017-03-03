package com.gmd.lessons.fcm.storage.rest;


import com.gmd.lessons.fcm.storage.model.BaseResponse;
import com.gmd.lessons.fcm.storage.model.DeviceRaw;
import com.gmd.lessons.fcm.storage.model.NotificationRaw;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by em on 8/06/16.
 */
public class ApiClient {

    private static final String TAG = "ApiClient";
    private static final String API_BASE_URL="http://api.backendless.com";

    private static ServicesApiInterface servicesApiInterface;
    private static OkHttpClient.Builder httpClient;


    public static ServicesApiInterface getMyApiClient() {

        if (servicesApiInterface == null) {

            Retrofit.Builder builder =new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            httpClient =new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor());

            Retrofit retrofit = builder.client(httpClient.build()).build();
            servicesApiInterface = retrofit.create(ServicesApiInterface.class);
        }
        return servicesApiInterface;
    }

    public interface ServicesApiInterface {

        @Headers({
                "Content-Type: application/json",
                "application-id: 74C97EAE-51FE-A1F2-FFD7-8289F70EDA00",
                "secret-key: 4FC950FE-9125-43B9-FF54-96BE821A8C00",
                "application-type: REST"
        })
        //v1/users/login
        @POST("/v1/users/login")
        Call<Object> login(@Body Object raw);

        /**
         * https://api.backendless.com/<version-name>/messaging/registrations
         */
        @Headers({
                "Content-Type: application/json",
                "application-id: 74C97EAE-51FE-A1F2-FFD7-8289F70EDA00",
                "secret-key: 4FC950FE-9125-43B9-FF54-96BE821A8C00",
                "application-type: REST"
        })
        @POST("/v1/messaging/registrations")
        Call<BaseResponse> registerDevice(@Body DeviceRaw deviceRaw);

        //Devices
        @Headers({
                "Content-Type: application/json",
                "application-id: 74C97EAE-51FE-A1F2-FFD7-8289F70EDA00",
                "secret-key: 4FC950FE-9125-43B9-FF54-96BE821A8C00",
                "application-type: REST"
        })
        @GET("/v1/messaging/registrations")
        Call<BaseResponse> devices();

        //send Notifications
        //https://api.backendless.com/v1/messaging/Default
        @Headers({
                "Content-Type: application/json",
                "application-id: 74C97EAE-51FE-A1F2-FFD7-8289F70EDA00",
                "secret-key: 4FC950FE-9125-43B9-FF54-96BE821A8C00",
                "application-type: REST"
        })
        @POST("/v1/messaging/Default")
        Call<BaseResponse> sendNotification(@Body NotificationRaw notificationRaw);

    }

    /*private static OkHttpClient.Builder client(){
        if(httpClient==null)httpClient=new OkHttpClient.Builder();
        return httpClient;
    }*/
    private  static  HttpLoggingInterceptor interceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor= new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
