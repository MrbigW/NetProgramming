package com.wrk.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BlueService service = retrofit.create(BlueService.class);

        Call<BookSearchResponse> call = service.getSearchBooks("小王子", "", 0, 3);

        call.enqueue(new Callback<BookSearchResponse>() {
            @Override
            public void onResponse(Call<BookSearchResponse> call, final Response<BookSearchResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, response.body().getBooks().get(0).getAlt_title(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<BookSearchResponse> call, Throwable t) {

            }
        });

    }

    // 创建业务请求接口
    public interface BlueService {
        @GET("book/search")
        Call<BookSearchResponse> getSearchBooks(@Query("q") String name, @Query("tag") String tag,
                                                @Query("start") int start, @Query("count") int count);
    }


    public void postRequest(View v) {
        startActivity(new Intent(MainActivity.this,PostActivity.class));
    }


}


























