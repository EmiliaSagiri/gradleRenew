package com.example.gradletest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
public static final String  TAG = "sb";
static ArrayList<String> sum = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String version = "7.2";
                String pattern = "/distributions/gradle-"+ version + "-all.zip.sha256";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(File.read("/vr/test/1.txt"));
                while (m.find()) {
                    sum.add(m.group());
                }
                String http =" https://services.gradle.org/"+sum.get(0);
                Log.i(TAG, http);
                Http.download(http,"vr/test/2.txt");
            }
        }).start();
    }
}