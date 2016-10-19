package com.network.netprogramming;
/**
 * 1、Volley的get和post请求
 * <p>
 * 2、Volley的网络请求建立和取消队列请求
 * <p>
 * 3、Volley与Activity生命周期的联动
 * <p>
 * 4、Volley的简单的二次回调封装
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //  StringRequest:1. 创建一个RequestQueue对象。
    //              2. 创建一个StringRequest对象。
    //            3. 将StringRequest对象添加到RequestQueue里面。


    public void get_String(View view) {
        //  获取url对象
        String url = "http://apis.juhe.cn/mobile/get?phone=13519154565&key=e41f369c9f7dd98be5a641e53222dc13";

        //  获取StringRequest对象
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            //  服务器响应成功的回调
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            //  服务器响应失败的回调
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("stringGet");
        MyApplication.getHttpQueues().add(request);
    }

    public void post_String(View view) {
        String url = "http://apis.juhe.cn/mobile/get";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        })
                //Post中的提供请求参数
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", "13519154565");
                map.put("key", "e41f369c9f7dd98be5a641e53222dc13");
                return map;
            }
        };
        request.setTag("stringPost");
        MyApplication.getHttpQueues().add(request);
    }


    //JsonRequest解析效率高

    public void get_Json(View view) {
        String url = "http://apis.juhe.cn/mobile/get?phone=13519154565&key=e41f369c9f7dd98be5a641e53222dc13";

        //Get方式请求，JsonObject为null;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("JsonGet");
        MyApplication.getHttpQueues().add(request);
    }

    public void post_Json(View view) {
        String url = "http://apis.juhe.cn/mobile/get";

        //  该object对象就是请求参数
        Map<String, String> map = new HashMap<>();
        map.put("phone", "13519154565");
        map.put("key", "e41f369c9f7dd98be5a641e53222dc13");
        JSONObject object = new JSONObject(map);

        Toast.makeText(this, object.toString(), Toast.LENGTH_LONG).show();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        request.setTag("JsonPost");
        MyApplication.getHttpQueues().add(request);

    }


}