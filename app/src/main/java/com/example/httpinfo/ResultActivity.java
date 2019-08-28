package com.example.httpinfo;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.httpinfo.adapter.ResultAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.model.HttpModel;
import fairy.easy.httpmodel.model.HttpNormalUrlLoader;
import fairy.easy.httpmodel.resource.HttpListener;
import fairy.easy.httpmodel.resource.HttpType;

import static com.example.httpinfo.MainActivity.HTTP_ADDRESS;

public class ResultActivity extends AppCompatActivity {

    private ContentLoadingProgressBar clpbLoading;
    private RecyclerView rvResult;
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("评测结果");
        clpbLoading = findViewById(R.id.result_activity_pb);
        rvResult = findViewById(R.id.result_activity_rv);
        rvResult.setHasFixedSize(true);
        ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.item_activity_result_rv);
        rvResult.setAdapter(resultAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvResult.setLayoutManager(linearLayoutManager);
        clpbLoading.show();

        HttpModelHelper.getInstance()
                .init(getApplicationContext())
                .setChina(false)
                .setModelLoader(new OkHttpUrlLoader())
//                .setModelLoader(new HttpNormalUrlLoader())
                .setFactory()
//                .addType(HttpType.HOST)
                .addAll()
                .build()
                .startAsync(getIntent().getStringExtra(MainActivity.HTTP_ADDRESS), new HttpListener() {
                    @Override
                    public void onSuccess(HttpType httpType, JSONObject result) {
                        ++index;
                        clpbLoading.setProgress((int) (100f/HttpModelHelper.getInstance().getHttpTypeSize()*index));
                        resultAdapter.insertData(new ResultBean(httpType.getName(), result.toString()));
                    }

                    @Override
                    public void onFail(String data) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFinish(JSONObject result) {
                        clpbLoading.hide();
                        try {
                            Toast.makeText(getApplicationContext(), "评测成功 总耗时" + result.getString("totalTime"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
