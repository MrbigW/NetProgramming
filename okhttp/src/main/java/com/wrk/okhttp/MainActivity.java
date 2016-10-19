package com.wrk.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();


    private final static int SUCCESS_STATUS = 1;
    private final static int FAIL_STATUS = 0;

    private String image_path = "http://h.hiphotos.baidu.com/image/h%3D360/sign=a0798692211f95cab9f594b0f9167fc5/72f082025aafa40f7c884d31af64034f79f0198b.jpg";
    private String json_path = "http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.2.8/0-20.json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8";
    private String login_path = "http://192.168.1.74:8080/webproject/LoginAction";

    private Request mRequest;

    private OkHttpClient mClient;

    private OkManager okManager;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SUCCESS_STATUS:

                    byte[] result = (byte[]) msg.obj;
//                    Bitmap bm = BitmapFactory.decodeByteArray(result, 0, result.length);
                    Bitmap bm = new CropSquareTrans().transform(BitmapFactory.decodeByteArray(result, 0, result.length));
                    ivShowimage.setImageBitmap(bm);

                    break;
                case FAIL_STATUS:
                    break;
            }

        }
    };

    @BindView(R.id.iv_showimage)
    ImageView ivShowimage;
    @BindView(R.id.btn_downimage)
    Button btnDownimage;
    @BindView(R.id.btn_getjsonstring)
    Button btnGetjsonstring;
    @BindView(R.id.btn_showimage)
    Button btnShowimage;
    @BindView(R.id.btn_post)
    Button btnPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mClient = new OkHttpClient();

        okManager = OkManager.getInstance();

        // 使用Get请求
        mRequest = new Request.Builder().get().url(image_path).build();

    }

    @OnClick(R.id.btn_downimage)
    public void downImage() {

        mClient.newCall(mRequest).enqueue(new Callback() {
            /**
             * 失败的情况下
             * @param call
             * @param e
             */
            @Override
            public void onFailure(Call call, IOException e) {

            }

            /**
             * 请求成功的情况
             * @param call
             * @param response  返回结果
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = mHandler.obtainMessage();
                if (response.isSuccessful()) {
                    message.what = SUCCESS_STATUS;
                    message.obj = response.body().bytes();
                    mHandler.sendMessage(message);
                } else {
                    mHandler.sendEmptyMessage(FAIL_STATUS);
                }
            }
        });

    }


    @OnClick(R.id.btn_getjsonstring)
    public void getJsonString() {
        okManager.asyncJsonStrngByURL(json_path, new OkManager.Func1() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, result); // 获取json字符串
            }
        });
    }

    @OnClick(R.id.btn_showimage)
    public void showImage() {
        okManager.asyncBitmapByURL(image_path, new OkManager.Func3() {
            @Override
            public void onResponse(Bitmap bitmap) {
                ivShowimage.setImageBitmap(bitmap);
            }
        });
    }

    @OnClick(R.id.btn_post)
    public void postFrom() {

        Map<String, String> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "12345");

        okManager.sendComplexFrom(login_path, map, new OkManager.Func4() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, "jsonObject:" + jsonObject, Toast.LENGTH_SHORT).show();
            }
        });
    }
}



































