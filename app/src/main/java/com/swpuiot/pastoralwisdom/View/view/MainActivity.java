package com.swpuiot.pastoralwisdom.View.view;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.swpuiot.pastoralwisdom.R;
import com.swpuiot.pastoralwisdom.View.controller.BannerLoader;
import com.swpuiot.pastoralwisdom.View.controller.ParameterEntity;
import com.swpuiot.pastoralwisdom.View.utils.JsonUtils;
import com.youth.banner.Banner;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int SHOWPARAMATER = 0;
    //控制命令
    public static final String TURNONFAN = "turnOnFan";
    public static final String TURNDOWNFAN = "turnDownFan";
    public static final String TURNDOWNLIGHT = "turnDownLight";
    public static final String TURNONLIGHT = "turnOnLight";
    public static final String TURNONWATERPUMP = "turnOnWaterPump";
    public static final String TURNDOWNWATERPUMP = "turnDownWaterPump";

    private List<Integer> listOfImageId;

    private Boolean isFSOpened = false;
    private Boolean isDPOpened = false;
    private Boolean isWTOpened = false;
    //控制台参数
    private RadioButton rbFensan;
    private RadioButton rbDengPao;
    private RadioButton rbWater;
    //空气参数
    private RadioButton rbAirTemp;
    private RadioButton rbAirHump;
    private RadioButton rbAirLingt;
    //土壤参数
    private RadioButton earthTemp;
    private RadioButton earthHump;
    private long[] shock;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOWPARAMATER:
                    ParameterEntity parameterEntity = (ParameterEntity) msg.obj;
                    desPlayParameter(parameterEntity);
            }
        }
    };
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initialize();
        //图片轮播
        if (banner != null)
            banner.setImageLoader(new BannerLoader())
                    .setImages(listOfImageId)
                    .isAutoPlay(true)
                    .start();
        getParameter();

    }

    public void initialize() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_sendfile));
        listOfImageId = new ArrayList<Integer>();
        {
            listOfImageId.add(R.drawable.ic_abc_image_turn1);
            listOfImageId.add(R.drawable.ic_abc_image_turn2);
//           listOfImageId.add(R.drawable.ic_abc_image_turn3);
            listOfImageId.add(R.drawable.ic_abc_image_turn4);
//           listOfImageId.add(R.drawable.ic_abc_image_turn5);
            listOfImageId.add(R.drawable.ic_abc_image_turn6);
            listOfImageId.add(R.drawable.ic_abc_image_turn7);
            listOfImageId.add(R.drawable.ic_abc_image_turn8);
            listOfImageId.add(R.drawable.ic_abc_image_turn9);
            listOfImageId.add(R.drawable.ic_abc_image_turn10);
            listOfImageId.add(R.drawable.ic_abc_image_turn11);
        }
        //初始化空气指数显示器
        rbAirTemp = (RadioButton) findViewById(R.id.betton_airtemp);
        rbAirHump = (RadioButton) findViewById(R.id.betton_airhump);
//        earthstoon= (RadioButton) findViewById(R.id.betton_earthkuangwu);

        //初始化控制台
        rbFensan = (RadioButton) findViewById(R.id.radiobutton_fengsan_controler);
        if (rbDengPao != null)
            rbDengPao.setOnClickListener(this);
        rbWater = (RadioButton) findViewById(R.id.radiobutton_water_controler);
        if (rbWater != null)
            rbWater.setOnClickListener(this);
        //震动
        shock = new long[]{500, 1000, 500, 1000};

        banner = (Banner) findViewById(R.id.banner);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radiobutton_fengsan_controler:
                if (!isFSOpened) {
                    RequestParams requestparams = new RequestParams();
                    requestparams.add("order", TURNONFAN);
                    control(requestparams);
                } else {
                    RequestParams requestparams = new RequestParams();
                    requestparams.add("order", TURNDOWNFAN);
                    control(requestparams);
                }
                break;
            case R.id.radiobutton_water_controler:
                if (!isWTOpened) {
                    RequestParams requestparams = new RequestParams();
                    requestparams.add("order", TURNONWATERPUMP);
                    control(requestparams);

                } else {
                    RequestParams requestparams = new RequestParams();
                    requestparams.add("order", TURNDOWNWATERPUMP);
                    control(requestparams);
                }
        }
    }

    //显示参数
    public void desPlayParameter(ParameterEntity parameterEntity) {
        //显示空气参数
//        if (parameterEntity.getDate().getGreenhouseTemperature() > 15) {
//            rbAirTemp.setText("温度:" + parameterEntity.getDate().getGreenhouseTemperature() + "℃");
//            Toast.makeText(this, "警告：空气温度过高", Toast.LENGTH_SHORT).show();
////            shockNotice(this, shock, true);
//        }
//        if (parameterEntity.getDate().getGreenhouseTemperature() <= 15) {
//            rbAirTemp.setText("温度:" + parameterEntity.getDate().getGreenhouseTemperature() + "℃");
////            stopShock(this);
//        }
//        rbAirHump.setText("湿度:" + parameterEntity.getDate().getGreenhouseHumidity() + "RH");
//        rbAirLingt.setText("光照" + parameterEntity.getDate().getLightIntensity() + "lx");
//        //显示土壤参数
//        earthTemp.setText("温度:" + parameterEntity.getDate().getSoilTemperature() + "℃");
//        if (parameterEntity.getDate().getSoilHumidity() < 50) {
//            earthHump.setText("湿度:" + parameterEntity.getDate().getSoilHumidity() + "RH");
//            Toast.makeText(this, "警告：土壤湿度过低", Toast.LENGTH_SHORT).show();
////            shockNotice(this, shock, true);
//        }
//        if (parameterEntity.getDate().getSoilHumidity() >= 50) {
//            earthHump.setText("湿度:" + parameterEntity.getDate().getSoilHumidity() + "RH");
////            stopShock(this);
//        }


    }

    //手机震动
    public void shockNotice(final Activity activity, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    public void stopShock(final Activity activity) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }

    private SyncHttpClient syncHttpClient = new SyncHttpClient();

    //访问网络获得参数
    public void getParameter() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                syncHttpClient.get("http://www.bug666.cn:8060/getInf", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        ParameterEntity parameterEntity = JsonUtils.fromJson(new String(bytes), ParameterEntity.class);
                        Message message = new Message();
                        message.what = SHOWPARAMATER;
                        message.obj = parameterEntity;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
            }
        };
        new Timer().schedule(timerTask, 0, 500);

    }


    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final static String URL = "http://www.bug666.cn:8060/control";

    //操作控制台
    public void control(RequestParams requestparams) {
        asyncHttpClient.post(URL, requestparams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                ParameterEntity parameterEntity = JsonUtils.fromJson(new String(bytes), ParameterEntity.class);

//                switch (parameterEntity.getMessage()) {
//                    case "风扇开启成功":
//                        isFSOpened = true;
//                        rbFensan.setChecked(true);
////                        Toast.makeText(MainActivity.this, "风扇开启成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "风扇关闭成功":
//                        isFSOpened = false;
//                        rbFensan.setChecked(false);
////                        Toast.makeText(MainActivity.this, "风扇关闭成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "灯泡开启成功":
//                        isDPOpened = true;
//                        rbDengPao.setChecked(true);
////                        Toast.makeText(MainActivity.this, "灯泡开启成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "灯泡关闭成功":
//                        isDPOpened = false;
//                        rbDengPao.setChecked(false);
////                        Toast.makeText(MainActivity.this, "灯泡关闭成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "水泵开启成功":
//                        isWTOpened = true;
//                        rbWater.setChecked(true);
////                        Toast.makeText(MainActivity.this, "水泵开启成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "水泵关闭成功":
//                        isWTOpened = false;
//                        rbWater.setChecked(false);
////                        Toast.makeText(MainActivity.this, "水泵关闭成功", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
//                        break;
//                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(MainActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
