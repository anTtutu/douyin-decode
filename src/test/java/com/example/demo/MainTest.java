package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainTest {

    public static void main(String[] args) throws IOException {


//        Ftp ftp=new Ftp("119.27.161.118",21,"xxx","xxx");
//        //进入远程目录
//        ftp.cd("/root");
////上传本地文件
//        ftp.upload("/root", FileUtil.file("C:/Users/zaqhr/Desktop/微信sdk.txt"));
////下载远程文件
//        ftp.download("/root/", "微信sdk.txt", FileUtil.file("E:/MyFile/下载的文件.txt"));
//
////关闭连接
//        ftp.close();
        try {
            // HtmlUnit 模拟浏览器
            String str = "https://h5.weishi.qq.com/weishi/feed/6YFkw9nna1IuyrrpS/wsfeed?wxplay=1&id=6YFkw9nna1IuyrrpS&spid=1528967546907220&qua=v1_iph_weishi_6.1.1_178_app_a&chid=100081014&pkg=3670&attach=cp_reserves3_1000370011";

            Document doc = null;
            try {
                doc = Jsoup.connect(str).cookie("cookie", "tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                        //模拟手机浏览器
                        //.header("user-agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                        //.header("cookie","tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                        .timeout(12138).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 解析网页标签
            String s = doc.body().toString();
            System.out.println(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}