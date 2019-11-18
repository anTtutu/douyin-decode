package com.example.demo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author xinyan
 * @title: WeiShiDecodeMain
 * @projectName demo
 * @description: TODO
 * @date 2019/11/14 15:57
 */
public class WeiShiDecodeMain {

    private static final Log LOG = LogFactory.getLog(HtmlPage.class);
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        while (true) {
        try {
//                System.out.println("请输入您要解析的视频连接(可以直接复制到窗框):");
//                String inputText = inputText();
            // 输入判断需要解析的抖音地址
//                String url2 = decodeHttpUrl(inputText);
            String url2 = "https://h5.weishi.qq.com/weishi/feed/6YFkw9nna1IuyrrpS/wsfeed?wxplay=1&id=6YFkw9nna1IuyrrpS&spid=1528967546907220&qua=v1_iph_weishi_6.1.1_178_app_a&chid=100081014&pkg=3670&attach=cp_reserves3_1000370011\n";
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);              // 关闭JS解释器，不然会报错
            webClient.getOptions().setCssEnabled(true);                    // 关闭css支持
            webClient.setJavaScriptTimeout(10000);
            HtmlPage page = webClient.getPage(url2);
            if (page.asXml().contains("//<![CDATA[")) {
                System.out.println(page.asXml());
            }
            String s = page.asXml();


            //打印日志出现video_url数据，实际调试没有，需要log日志处理
            //String pageAsXml = page.asXml();
            // Jsoup解析处理

//                Document doc = Jsoup.parse(pageAsXml);
//                Elements script = doc.getElementsByTag("script");
//                String url1 = script.toString();
//                //System.out.println(url1);
//                String ws = "video_url\":\"(.*)\"";
//                Pattern r = Pattern.compile(ws);
//                Matcher m = r.matcher(url1);
////                String url="";
//                while (m.find()) {
//                    url1 = m.group();
//                }
            //.replaceAll("video_url\":", "").replaceAll("\"", "")
            //System.out.println(url1);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
    }
}
