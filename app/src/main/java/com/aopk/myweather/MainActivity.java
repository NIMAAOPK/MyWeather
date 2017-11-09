package com.aopk.myweather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.aopk.myweather.RxFactory.BaseObserver;
import com.aopk.myweather.RxFactory.RetrofitFactory;
import com.aopk.myweather.RxFactory.RxSchedulers;
import com.aopk.myweather.javabeanXml.BaseEntity;
import com.aopk.myweather.javabeanXml.Day;
import com.aopk.myweather.javabeanXml.Day_1;
import com.aopk.myweather.javabeanXml.Night;
import com.aopk.myweather.javabeanXml.Night_1;
import com.aopk.myweather.javabeanXml.Weather;
import com.aopk.myweather.javabeanXml.Yesterday;
import com.aopk.myweather.javabeanXml.Zhishu;
import com.aopk.myweather.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends CheckPermissionsActivity {
    private static final String TAG = "MainActivity";
    private TextView tvPlace, tvUpdateTime, tvTemp, tvType, tvTempRange,
            tvFengxiang, tvHumidity, tvFire, tvSunrise, tvSunset;
    private MyGridView gvPrediction, gvLifeTip;
    private ImageView iv_location;
    private List<Map<String, Object>> predictionList;
    private List<Map<String, Object>> lifeList;
    private SimpleAdapter predictionAdapter, lifeAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String rainTip;
    private String type;
    private String tempRange;
    private boolean isToday = true;
    private String city = "";
    private String location = "--";

    public AMapLocationClient aMapLocationClient = null;
    public AMapLocationClientOption aMapLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        //getWindow().setBackgroundDrawableResource(R.mipmap.home_bg);
        initView();
        initViewEvent();
        //loadData();
        getLocation();
        setPermissionListener(new PermissionListener() {
            @Override
            public void onSuccess() {
                getLocation();
            }
        });
    }

    private void getLocation() {
        aMapLocationClient = new AMapLocationClient(getApplicationContext());
        aMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        city = amapLocation.getDistrict();//城区信息
                        location = amapLocation.getDistrict();//城区信息
                        loadData(city);
                        Log.i(TAG, "onLocationChanged: " + city);
                        aMapLocationClient.stopLocation();
                        aMapLocationClient.onDestroy();
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        aMapLocationClient.stopLocation();
                        aMapLocationClient.onDestroy();
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    aMapLocationClient.stopLocation();
                    aMapLocationClient.onDestroy();
                }
            }
        });
        aMapLocationOption = new AMapLocationClientOption();
        aMapLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        aMapLocationOption.setOnceLocation(true);
        aMapLocationOption.setOnceLocationLatest(true);
        aMapLocationOption.setLocationCacheEnable(true);
        aMapLocationClient.setLocationOption(aMapLocationOption);
        aMapLocationClient.startLocation();
    }

    private void loadData(final String city) {
        RetrofitFactory.getInstance().getWeatherInfo(city)
                .compose(RxSchedulers.<BaseEntity>compose())
                .doOnNext(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        fillData(baseEntity);
                    }
                })
                .subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    protected void onSuccess(BaseEntity baseEntity) {
                        //String city1 = city;
                        Log.i(TAG, "onSuccess: " + baseEntity.toString());
                        lifeAdapter.notifyDataSetChanged();
                        predictionAdapter.notifyDataSetChanged();
                        tvPlace.setText(city);
                        tvUpdateTime.setText(baseEntity.getUpdatetime());
                        tvTemp.setText(baseEntity.getWendu());
                        tvType.setText(type);
                        tvTempRange.setText(tempRange);
                        tvFengxiang.setText(baseEntity.getFengxiang() + baseEntity.getFengli());
                        tvHumidity.setText(baseEntity.getShidu());
                        tvFire.setText(rainTip);
                        tvSunrise.setText(baseEntity.getSunrise_1());
                        tvSunset.setText(baseEntity.getSunset_1());
                        isToday = true;
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                        tvUpdateTime.setText(R.string.update_fail);
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, R.string.update_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fillData(BaseEntity baseEntity) {
        if (baseEntity != null) {
            //天气预报数据
            //"day", "temp", "dayTypeImg", "dayType", "dayTemp", "nightTemp", "nightTypeImg", "fengxiang", "fengli"
            predictionList.clear();
            Yesterday yesterday = baseEntity.getYesterday();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("day", getString(R.string.yesterday));
            map1.put("temp", yesterday.getDate_1().substring(yesterday.getDate_1().length() - 3, yesterday.getDate_1().length()));
            Day_1 day_1 = yesterday.getDay_1();
            map1.put("dayTypeImg", switchPic(day_1.getType_1()));
            map1.put("dayType", day_1.getType_1());
            map1.put("dayTemp", yesterday.getHigh_1().substring(3, yesterday.getHigh_1().length()));
            map1.put("nightTemp", yesterday.getLow_1().substring(3, yesterday.getLow_1().length()));
            Night_1 night_1 = yesterday.getNight_1();
            map1.put("nightType", night_1.getType_1());
            map1.put("nightTypeImg", switchPic(night_1.getType_1()));
            map1.put("fengxiang", night_1.getFx_1());
            map1.put("fengli", night_1.getFl_1());
            predictionList.add(map1);

            List<Weather> listWeather = baseEntity.getForecast();
            for (Weather weather : listWeather) {
                Map<String, Object> map = new HashMap<>();
                map.put("day", weather.getDate().substring(0, weather.getDate().length() - 3));
                map.put("temp", weather.getDate().substring(weather.getDate().length() - 3, weather.getDate().length()));
                Day day = weather.getDay();
                map.put("dayTypeImg", switchPic(day.getType()));
                map.put("dayType", day.getType());
                map.put("dayTemp", weather.getHigh().substring(3, weather.getHigh().length()));
                map.put("nightTemp", weather.getLow().substring(3, weather.getLow().length()));
                Night night = weather.getNight();
                map.put("nightType", night.getType());
                map.put("nightTypeImg", switchPic(night.getType()));
                map.put("fengxiang", night.getFengxiang());
                map.put("fengli", night.getFengli());
                predictionList.add(map);
                if (isToday) {
                    type = day.getType();
                    tempRange = weather.getHigh().substring(3, weather.getHigh().length() - 1) + "/"
                            + weather.getLow().substring(3, weather.getLow().length());
                    isToday = false;
                }
            }

            //生活指数数据
            List<Zhishu> list = baseEntity.getZhishus();
            lifeList.clear();
            for (Zhishu zhishu : list) {
                Map<String, Object> map = new HashMap<>();
                Log.i(TAG, "accept: " + zhishu.getName());
                switch (zhishu.getName()) {

                    case "晨练指数":
                        map.put("icon", R.mipmap.zs_ic_chenlian);
                        map.put("tip", zhishu.getValue() + "晨练");
                        lifeList.add(map);
                        break;
                    case "舒适度":
                        map.put("icon", R.mipmap.zs_ic_shushi);
                        map.put("tip", "感觉"+zhishu.getValue());
                        lifeList.add(map);
                        break;
                    case "紫外线强度":
                        map.put("icon", R.mipmap.zs_ic_ziwaixian);
                        map.put("tip", "紫外线"+zhishu.getValue());
                        lifeList.add(map);
                        break;
                    case "洗车指数":
                        map.put("icon", R.mipmap.zs_ic_xiche);
                        map.put("tip", zhishu.getValue()+"洗车");
                        lifeList.add(map);
                        break;
                    case "穿衣指数":
                        map.put("icon", R.mipmap.zs_ic_chuanyi);
                        map.put("tip", zhishu.getValue());
                        lifeList.add(map);
                        break;
                    case "感冒指数":
                        map.put("icon", R.mipmap.zs_ic_ganmao);
                        map.put("tip", zhishu.getValue());
                        lifeList.add(map);
                        break;
                    case "旅游指数":
                        map.put("icon", R.mipmap.zs_ic_lvyou);
                        map.put("tip", zhishu.getValue()+"旅游");
                        lifeList.add(map);
                        break;
                    case "晾晒指数":
                        map.put("icon", R.mipmap.zs_ic_liangshai);
                        map.put("tip", zhishu.getValue()+"晾晒");
                        lifeList.add(map);
                        break;
                    case "雨伞指数":
                        rainTip = zhishu.getDetail();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private int switchPic(String str) {
        int id;
        switch (str) {
            case "多云":
                id = R.mipmap.duoyun;
                break;
            case "晴":
                id = R.mipmap.qing;
                break;
            case "小雪":
                id = R.mipmap.xiaoxue;
                break;
            case "小雨":
                id = R.mipmap.xiaoyu;
                break;
            case "雪":
                id = R.mipmap.xue;
                break;
            case "阴":
                id = R.mipmap.yin;
                break;
            case "雨夹雪":
                id = R.mipmap.yujiaxue;
                break;
            case "阵雪":
                id = R.mipmap.zhenxue;
                break;
            case "阵雨":
                id = R.mipmap.zhenyu;
                break;
            case "中雨":
                id = R.mipmap.zhongyu;
                break;
            default:
                id = R.mipmap.duoyun;
                break;
        }
        return id;
    }

    private void initViewEvent() {
        tvPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("location", location);
                startActivityForResult(intent, 1);
            }
        });

        predictionList = new ArrayList<>();
        lifeList = new ArrayList<>();
        predictionAdapter = new SimpleAdapter(this, predictionList, R.layout.prediction_gv_item,
                new String[]{"day", "temp", "dayTypeImg", "dayType", "dayTemp", "nightTemp", "nightType", "nightTypeImg", "fengxiang", "fengli"},
                new int[]{R.id.prediction_rv_item_day, R.id.prediction_rv_item_temp,
                        R.id.prediction_rv_item_img_daytype, R.id.prediction_rv_item_tv_daytype,
                        R.id.prediction_rv_item_tv_daytemp, R.id.prediction_rv_item_tv_nighttemp,
                        R.id.prediction_rv_item_tv_nighttype, R.id.prediction_rv_item_img_nighttype,
                        R.id.prediction_rv_item_tv_fengxiang, R.id.prediction_rv_item_tv_fengli});
        lifeAdapter = new SimpleAdapter(this, lifeList, R.layout.life_gv_item, new String[]{"icon", "tip"}, new int[]{R.id.life_gv_item_img, R.id.life_gv_item_tv});
        gvPrediction.setAdapter(predictionAdapter);
        gvLifeTip.setAdapter(lifeAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (location.equals("--") || location.equals(""))
                    getLocation();
                else if (tvPlace.getText().toString().equals(location)){
                    loadData(location);
                }else{
                    loadData(tvPlace.getText().toString());
                }

            }
        });
    }

    private void initView() {
        iv_location = (ImageView) findViewById(R.id.iv_location);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        tvPlace = (TextView) findViewById(R.id.tv_place);
        tvUpdateTime = (TextView) findViewById(R.id.today_tv_updatetime);
        tvTemp = (TextView) findViewById(R.id.today_tv_temp);
        tvType = (TextView) findViewById(R.id.today_tv_type);
        tvTempRange = (TextView) findViewById(R.id.today_tv_temp_range);
        tvFengxiang = (TextView) findViewById(R.id.today_tv_fengxiang);
        tvHumidity = (TextView) findViewById(R.id.today_tv_temp_shidu);
        tvFire = (TextView) findViewById(R.id.today_tv_fire);
        tvSunrise = (TextView) findViewById(R.id.tv_sunrise);
        tvSunset = (TextView) findViewById(R.id.tv_sunset);

        gvPrediction = (MyGridView) findViewById(R.id.prediction_grilview);
        gvLifeTip = (MyGridView) findViewById(R.id.life_grilview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String res = data.getStringExtra("city");
                    predictionList.clear();
                    lifeList.clear();
                    predictionAdapter.notifyDataSetChanged();
                    lifeAdapter.notifyDataSetChanged();
                    if (res.equals(location))
                        iv_location.setVisibility(View.VISIBLE);
                    else
                        iv_location.setVisibility(View.GONE);
                    if (res.equals("--"))
                        getLocation();
                    else {
                        loadData(res);
                    }
                }
                break;
            default:
                break;
        }
    }
}
