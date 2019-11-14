package com.example.demo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLSocketFactory;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.demo.DouYinDecodeMain.decodeHttpUrl;
import static com.example.demo.DouYinDecodeMain.inputText;

/**
 * @author xinyan
 * @title: WeiShiDecodeMain
 * @projectName demo
 * @description: TODO
 * @date 2019/11/14 15:57
 */
public class WeiShiDecodeMain {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        while (true) {
            try {
                System.out.println("请输入您要解析的视频连接(可以直接复制到窗框):");
                String inputText = inputText();
                // 输入判断需要解析的抖音地址
                String url2 = decodeHttpUrl(inputText);
                WebClient webClient = new WebClient(BrowserVersion.CHROME);
                webClient.getOptions().setJavaScriptEnabled(false);              // 关闭JS解释器，不然会报错
                webClient.getOptions().setCssEnabled(false);                    // 关闭css支持
                HtmlPage page = webClient.getPage(url2);
                webClient.waitForBackgroundJavaScript(30 * 1000);
                //打印日志出现video_url数据，实际调试没有，需要log日志处理
                String pageAsXml = page.asXml();
                // Jsoup解析处理
                Document doc = Jsoup.parse(pageAsXml);
                Elements script = doc.getElementsByTag("script");
                String url1 = script.toString();
                //System.out.println(url1);
                String ws = "video_url\":\"(.*)\"";
                Pattern r = Pattern.compile(ws);
                Matcher m = r.matcher(url1);
//                String url="";
                while (m.find()) {
                    url1 = m.group();
                }
                //.replaceAll("video_url\":", "").replaceAll("\"", "")
                System.out.println(url1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
