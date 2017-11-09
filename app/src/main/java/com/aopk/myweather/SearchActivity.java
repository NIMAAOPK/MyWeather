package com.aopk.myweather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private String city = "";
    private String location;

    private Button btn_back;
    private EditText edtInput;
    private TextView tvLocation;
    private LinearLayout lyLocation;
    private ListView lvData;
    private List<String> lvList;
    private ArrayAdapter<String> adapter ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.serch_layout);
        location = getIntent().getStringExtra("location");
        initView();
        initViewEvent ();
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
    
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                findCity(city);
                if (lvList.size() == 0&& city.length()>1)
                    findCity(city.substring(0,city.length()-1));
                mHandler.sendEmptyMessage(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private void findCity(String city) throws IOException {
        lvList.clear();
       InputStream in = getResources().openRawResource(R.raw.city);
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        String line;
        try {
            while ((line = br.readLine()) != null){
                if (line.indexOf(city) != -1){
                    String str = line.trim();
                    lvList.add(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            br.close();
            reader.close();
            in.close();
        }
    }

    private void initViewEvent() {
        lvList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,lvList);
        lvData.setAdapter(adapter);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentMainActivity(lvList.get(position));
            }
        });
        lyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMainActivity(location);
            }
        });
        tvLocation.setText(location);
        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                city = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                mHandler.removeCallbacks(runnable);
                mHandler.postDelayed(runnable,200);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void intentMainActivity(String msg){
        Intent intent = new Intent();
        intent.putExtra("city",msg);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        edtInput = (EditText) findViewById(R.id.edt_serch);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        lyLocation = (LinearLayout) findViewById(R.id.location_ll);
        lvData = (ListView) findViewById(R.id.lv_place);
    }
}
