package com.example.httpinfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String HTTP_ADDRESS = "http";
    public static final String HTTP_RB = "rb";

    private EditText etInput;
    private CheckBox radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.main_net));
        //获取手机强度使用
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        etInput = findViewById(R.id.main_activity_et_input);
        radioButton = findViewById(R.id.main_activity_start_rb);

        findViewById(R.id.main_activity_start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Input Address is wrong", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra(HTTP_ADDRESS, etInput.getText().toString());
                intent.putExtra(HTTP_RB, radioButton.isChecked());
                startActivity(intent);
            }
        });
    }

}
