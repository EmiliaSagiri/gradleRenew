package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
//    public static final String  TAG = "/root/test1/compress";//压缩包下载位置
    public static final String  PATTERN = "6+(\\.6+)+(\\.\\d+)?";//控制下载版本
    static ArrayList<String> sum = new ArrayList<>();
    static ArrayList<String> sum2 = new ArrayList<>();
    public static ArrayList<File> allFile = new ArrayList<>();
    public static ArrayList<String> changeFile = new ArrayList<>();
    public static ArrayList<String> newFile = new ArrayList<>();
    public static void main(String[] args) {//传参
	// write your code here
        String  TAG = args[0];
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //获得想要下载的所有网站链接
                for(int i = 0 ; i<(sum.size())/2;i++){
                    String ljj = getWord(String.valueOf(sum.get(2*i+1)),TAG);
                    sum2.add(ljj);
                }
                //获取本地下载的文件路径，转换成字符串数组
                readFile(new File(TAG));
                for(int i = 0;i<allFile.size();i++){
                    changeFile.add(String.valueOf(allFile.get(i)));
                }
                //比较两个数组，不同的存到newFile数组中，并下载
                newFile = compare(changeFile,sum2) ;
                System.out.println(sum2);
                System.out.println(changeFile);
                System.out.println(newFile);
                for(int j = 0; j<newFile.size(); j++){
                    Http.download(getHttp(newFile.get(j)),newFile.get(j));
                }

            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String http = Http.get("https://services.gradle.org/distributions/");
                String version = PATTERN;
                String pattern = "/distributions/gradle-" +version+ "-all.zip";//会有两个重复数值，因为有两个相同字符串。
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(http);
                while (m.find()) {
                    sum.add(m.group());
                }
                System.out.println(sum);
            }
        });
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread2.start();
    }

    /*
     *获得下载链接
     */
    public static String getHttp(String x){
        String pattern = PATTERN ;
        String y = null ;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(x);
        while (m.find()) {
            y= m.group();
        }
        String ljj = "https://services.gradle.org/distributions/gradle-"+y+"-all.zip";
        return ljj;
    }
    /*
     *获得文件保存链接
     */
    public static String getWord(String x,String position){
        String pattern = PATTERN ;
        String y = null ;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(x);
        while (m.find()) {
            y= m.group();
        }
        String ljj = position+"/"+"gradle-"+y+".zip";
        return ljj;
    }
    public static void readFile(File directory) {
        if(directory.listFiles()!=null)
            for (File file : directory.listFiles()) {
                if (file.isDirectory())
                    readFile(file);
                else
                    allFile.add(file);//将文件夹数据写入集合中
            }

    }//读值到集合
    public static  ArrayList<String> compare(ArrayList<String> t1, ArrayList<String> t2) {
        ArrayList <String> list2 = new ArrayList<>();
        for (String t : t2) {
            if (!t1.contains(t)) {
                list2.add(t);
            }
        }
        return list2;
    }
}
