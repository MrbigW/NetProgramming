package com.wrk.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IUserBiz userBiz = retrofit.create(IUserBiz.class);

       userBiz.getUsers().enqueue(new Callback<List<User>>() {
           @Override
           public void onResponse(Call<List<User>> call, Response<List<User>> response) {

           }

           @Override
           public void onFailure(Call<List<User>> call, Throwable t) {

           }
       });

    }


    public interface IUserBiz {
        @GET("users")
        Call<List<User>> getUsers();
    }

}
