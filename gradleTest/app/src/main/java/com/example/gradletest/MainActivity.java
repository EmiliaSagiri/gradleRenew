package com.example.gradletest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
public static final String  TAG = "sb";
static ArrayList<String> sum = new ArrayList<>();
static ArrayList<String> sum2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String version = "0+(\\.9+)+(\\.\\d+)?";
                String pattern = "/distributions/gradle-" +version+ "-all.zip";//会有两个重复数值，因为有两个相同字符串。
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(Fileutil.read("/vr/test/1.txt"));
                while (m.find()) {
                    sum.add(m.group());
                }
                Log.i(TAG, String.valueOf(sum));
               for(int i = 0 ; i<(sum.size())/2;i++){
                   String x = sum.get(2*i+1);
                   sum2.add(x);
               }
                Log.i(TAG, String.valueOf(sum2));
                Log.i(TAG,Getword(sum2.get(2)) );
               Http.download("https://services.gradle.org"+sum2.get(2),Getword(sum2.get(2)));
//                String http =" https://services.gradle.org/"+sum.get(0);
//                Log.i(TAG, http);
//                Http.download(http,"vr/test/3.txt");
            }
        });
       Thread thread2 = new Thread(new Runnable() {
           @Override
           public void run() {
              String x =  Sha256.getFileSHA1(new File("vr/test/0.7.zip"));
               Log.i(TAG, x);
           }
       });
       thread1.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread2.start();
    }
    public String Getword(String x){
        String pattern = "0+(\\.9+)+(\\.\\d+)?" ;
        String y = null ;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(x);
        while (m.find()) {
            y= m.group();
        }
        String ljj = "/vr/compression/"+"http."+y+".zip";
        return ljj;
    }
}