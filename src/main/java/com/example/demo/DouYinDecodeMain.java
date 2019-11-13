package com.example.demo;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * tips：根据群里的同学：Change QQ：11848313 分享出来的链接：https://github.com/zbfzn/douyin-clear-php得到思路
 * 大家可以去看一下php的源码（能看懂一部分）
 *
 * @Author lovelyhedong
 * @Date 2019年9月11日 13:57:02
 */

@Slf4j
@SuppressWarnings("all")
public class DouYinDecodeMain {

    //复制下面链接测试吧，帅气的小姐姐，重要的事情说三遍
    //#在抖音，记录美好生活##开动就现在 #轮滑 好厉害的小姐姐 http://v.douyin.com/kPJm4r/ 复制此链接，打开【抖音短视频】，直接观看视频！
    //#在抖音，记录美好生活##开动就现在 #轮滑 好厉害的小姐姐 http://v.douyin.com/kPJm4r/ 复制此链接，打开【抖音短视频】，直接观看视频！
    //#在抖音，记录美好生活##开动就现在 #轮滑 好厉害的小姐姐 http://v.douyin.com/kPJm4r/ 复制此链接，打开【抖音短视频】，直接观看视频！
    static final String API[] = {
            //            "https://aweme.snssdk.com/aweme/v1/aweme/detail/?origin_type=link&retry_type=no_retry&iid=74655440239&device_id=57318346369&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=140&version_name=1.4.0&device_platform=android&ssmix=a&device_type=MI+8&device_brand=xiaomi&os_api=22&os_version=5.1.1&uuid=865166029463703&openudid=ec6d541a2f7350cd&manifest_version_code=140&resolution=1080*1920&dpi=1080&update_version_code=1400&as=a13520b0e9c40d9cbd&cp=064fdf579fdd07cae1&aweme_id=",
            //            "https://aweme.snssdk.com/aweme/v1/aweme/detail/?origin_type=link&retry_type=no_retry&iid=74655440239&device_id=57318346369&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=140&version_name=1.4.0&device_platform=android&ssmix=a&device_type=MI+8&device_brand=xiaomi&os_api=22&os_version=5.1.1&uuid=865166029463703&openudid=ec6d541a2f7350cd&manifest_version_code=140&resolution=1080*1920&dpi=1080&update_version_code=1400&as=a13510902a54ed1cad&cp=0a40dc5ba5db09cee1&aweme_id=",
            //            "https://aweme.snssdk.com/aweme/v1/aweme/detail/?origin_type=link&retry_type=no_retry&iid=43619087057&device_id=57318346369&ac=wifi&channel=update&aid=1128&app_name=aweme&version_code=251&version_name=2.5.1&device_platform=android&ssmix=a&device_type=MI+8&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166029463703&openudid=ec6d541a2f7350cd&manifest_version_code=251&resolution=1080*1920&dpi=480&update_version_code=2512&as=a1e500706c54fd8c8d&cp=004ad55fc8d60ac4e1&aweme_id=",
            //            "https://aweme.snssdk.com/aweme/v1/aweme/detail/?origin_type=link&retry_type=no_retry&$device&ac=wifi&channel=update&aid=1128&app_name=aweme&version_code=$version_code&version_name=$version_name&device_platform=android&ssmix=a&device_type=MI+8&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166029463703&openudid=ec6d541a2f7350cd&manifest_version_code=$version_code&resolution=1080*1920&dpi=480&update_version_code=2512&ts=1561136204&as=a1e500706c54fd8c8d&cp=004ad55fc8d60ac4e1&aweme_id=",
            //            "https://aweme.snssdk.com/aweme/v1/aweme/detail/?origin_type=link&retry_type=no_retry&iid=75364831157&device_id=68299559251&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=650&version_name=6.5.0&device_platform=android&ssmix=a&device_type=xiaomi+8&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&openudid=2e5c5ff4ce710faf&manifest_version_code=660&resolution=1080*1920&dpi=480&update_version_code=6602&mcc_mnc=46000&js_sdk_version=1.16.2.7&as=a1257080aec45ddcad&cp=0b4cd25fe4d00ccfe1&aweme_id=",
            //            "https://aweme.snssdk.com/aweme/v1/aweme/detail/?origin_type=link&retry_type=no_retry&iid=75364831157&device_id=68299559251&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=650&version_name=6.5.0&device_platform=android&ssmix=a&device_type=xiaomi+8&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&openudid=2e5c5ff4ce710faf&manifest_version_code=660&resolution=1080*1920&dpi=480&update_version_code=6602&mcc_mnc=46000&js_sdk_version=1.16.2.7&as=a125a0b01f946d2cdd&cp=0744d553ffd60cc3e1&aweme_id=",
            /**
             * 以上接口已经失效
            */
            //        "https://aweme.snssdk.com/aweme/v1/aweme/detail/?origin_type=link&retry_type=no_retry&$device&ac=wifi&channel=update&aid=1128&app_name=aweme&version_code=$version_code&version_name=$version_name&device_platform=android&ssmix=a&device_type=MI+8&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166029463703&openudid=ec6d541a2f7350cd&manifest_version_code=$version_code&resolution=1080*1920&dpi=480&update_version_code=2512&ts=1561136204&as=a1e500706c54fd8c8d&cp=004ad55fc8d60ac4e1&aweme_id="
            "https://aweme.snssdk.com/aweme/v1/aweme/detail/?origin_type=link&retry_type=no_retry&$device&ac=wifi&channel=update&aid=1128&app_name=aweme&version_code=$version_code&version_name=$version_name&device_platform=android&ssmix=a&device_type=MI+8&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166029463703&openudid=ec6d541a2f7350cd&manifest_version_code=$version_code&resolution=1080*1920&dpi=480&update_version_code=2512&ts=1561136204&as=a1e500706c54fd8c8d&cp=004ad55fc8d60ac4e1&aweme_id=",
            "https://api-hl.amemv.com/aweme/v1/aweme/detail/?retry_type=no_retry&iid=43619087057&device_id=57318346369&ac=wifi&channel=update&aid=1128&app_name=aweme&version_code=251&version_name=2.5.1&device_platform=android&ssmix=a&device_type=MI+8&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166029463703&openudid=ec6d541a2f7350cd&manifest_version_code=251&resolution=1080*1920&dpi=480&update_version_code=2512&_rticket=1559206461097&ts=1559206460&as=a115996edcf39c7adf4355&cp=9038c058c7f6e4ace1IcQg&mas=01af833c02eb8913ecc7909389749e6d89acaccc2c662686ecc69c&aweme_id="
            , "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=ITEM_IDS&dytk=DYTK"
    };

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        while (true) {
            try {
                System.out.println("请输入您要解析的视频连接(可以直接复制到窗框):");
                String inputText = inputText();
                // 输入判断需要解析的抖音地址
                String url2 = decodeHttpUrl(inputText);
                Document doc = null;
                try {
                    doc = Jsoup.connect(url2).cookie("cookie", "tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                            //模拟手机浏览器
                            .header("user-agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                            //.header("cookie","tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                            .timeout(12138).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 解析网页标签
                Elements elem = doc.getElementsByTag("script");
                String url1 = elem.toString();
                //正则
                String aweme_id = "itemId: \"([0-9]+)\"";
                String dytk = "dytk: \"(.*)\"";
                Pattern r = Pattern.compile(aweme_id);
                Matcher m = r.matcher(url1);
                while (m.find()) {
                    aweme_id = m.group().replaceAll("itemId: ", "").replaceAll("\"", "");
                }
                System.out.println(aweme_id);
                Pattern r1 = Pattern.compile(dytk);
                Matcher m1 = r1.matcher(url1);
                while (m1.find()) {
                    dytk = m1.group().replaceAll("dytk: ", "").replaceAll("\"", "");
                }
                System.out.println(dytk);
                /**
                 * 一个api解析接口
                 */
                String result2 = HttpRequest.get(API[2].replaceAll("ITEM_IDS", aweme_id).replaceAll("DYTK", dytk))
                        //模拟手机浏览器
                        .header(Header.USER_AGENT, "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")//头信息，多个头信息多次调用此方法即可
                        .timeout(12138)//超时，毫秒
                        .execute().body();
                //System.out.println("我是result2：" + result2);
                try {
                    //GOSN解析
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(result2.toString()).getAsJsonObject();
                    //之前获取参数的方法
                    //String url = jsonObject.get("aweme_detail").getAsJsonObject().get("long_video").getAsJsonArray().get(0).getAsJsonObject().get("video").getAsJsonObject().get("play_addr").getAsJsonObject().get("url_list").getAsJsonArray().get(0).toString().replaceAll("\"", "");
                    String url = jsonObject.get("item_list").getAsJsonArray().get(0).getAsJsonObject().get("video").getAsJsonObject().get("play_addr").getAsJsonObject().get("url_list").getAsJsonArray().get(1).getAsString();
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .get().addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
//                            .build();
//                    Response response = client.newCall(request).execute();
                    url = getURI(url);
                    if (!StringUtils.isEmpty(url)) {
                        System.out.println("解析地址为:" + url);
                    }
                } catch (Exception e) {
                    System.out.println("解析失败，请更换地址重试,报错信息：" + e.getMessage());
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public static String decodeHttpUrl(String url) {
        // 检测是否有中文，如果没有中文就是直接地址
        boolean containChinese = isContainChinese(url);
        if (url.contains("iesdouyin")) return url;
        if (containChinese) {
            int start = url.indexOf("http");
            int end = url.lastIndexOf("/");
            String decodeurl = url.substring(start, end);
            return decodeurl;
        } else
            return url;
    }

    public static String inputText() {
        Scanner text = new Scanner(System.in);
        String inputurl = text.nextLine();
        if (StringUtils.isEmpty(inputurl)) {//这里只判断了输入为空，根据业务自己更改
            throw new RuntimeException("输入有误，请重新输入");
        } else {
            return inputurl;
        }
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String getDownloadUrl(String url) {
        String url2 = decodeHttpUrl(url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url2).cookie("cookie", "tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                    //模拟手机浏览器
                    .header("user-agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                    //.header("cookie","tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                    .timeout(12138).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析网页标签
        Elements elem = doc.getElementsByTag("script");
        String url1 = elem.toString();
        int startLen = url1.indexOf("itemId: \"");
        int endLen = url1.indexOf("\",\n" +
                "            test_group");
        String itemId = url1.substring(startLen, endLen).replaceAll("itemId: \"", "");
        /**
         * API里面有7个网站，可以自己选择，
         * 最后一个是出问题的
         * 最后一个是出问题的
         * 最后一个是出问题的
         */
        String result2 = HttpRequest.get(API[0] + itemId)
                //模拟手机浏览器
                .header(Header.USER_AGENT, "Aweme/79025 CFNetwork/978.0.7 Darwin/18.7.0")//头信息，多个头信息多次调用此方法即可
                .timeout(12138)//超时，毫秒
                .execute().body();
        try {
            //GOSN解析
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(result2.toString()).getAsJsonObject();
            url = jsonObject.get("aweme_detail").getAsJsonObject().get("long_video").getAsJsonArray().get(0).getAsJsonObject().get("video").getAsJsonObject().get("play_addr").getAsJsonObject().get("url_list").getAsJsonArray().get(0).toString().replaceAll("\"", "");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get().addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                    .build();
            Response response = client.newCall(request).execute();
            url = response.request().url().toString();
            int start = url.indexOf("http");
            int end = url.lastIndexOf("?");
            url = url.substring(start, end);
            return url;
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    /**
     * @time 2019年10月24日 20:12:12
     * @TODO 以前的方法不能使用了，只能用老方法了
     * 获取真实地址
     */
    public static String getURI(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpContext httpContext = new BasicHttpContext();
        HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
        HttpGet httpGet = new HttpGet(url);
        /**
         * 以下模拟手机请求，添加Header
         */
        httpGet.setHeader("accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        httpGet.setHeader("accept-encoding", "gzip, deflate, br");
        httpGet.setHeader("accept-language", "zh-CN,zh;q=0.9");
        httpGet.setHeader("upgrade-insecure-requests", "1");
        httpGet.setHeader("user-agent",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
        /**
         * 以上模拟手机请求，添加Header
         */
        try {
            HttpResponse execute = httpClient.execute(httpGet, httpContext);
            //获取真实地址
            return clientContext.getTargetHost() + ((HttpUriRequest) clientContext.getRequest()).getURI().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @NotNull
    public static String cuthttpschinese(String str) {
        int start = str.indexOf("http");
        return str.substring(start);
    }


    @NotNull
    public static String NewUrlDecode(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(10000).get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(doc.data());
        Elements elem = doc.getElementsByTag("script");
        String url1 = elem.toString();
        int start = url1.indexOf("playAddr");
        url1 = url1.substring(start);
        int end = url1.indexOf("\",");
        String replaceAll = url1.substring(11, end).replaceAll("playmw", "playwm");
        System.out.println(replaceAll);
        return url1.substring(11, end).replaceAll("playwm", "play");
    }

    public static String getDownloadUrl(String url, String cannull) {
        return getURI(NewUrlDecode(decodeHttpUrl(url)));
    }
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        while (true) {
//            try {
//                System.out.println("请输入您要解析的视频连接(可以直接复制到窗框):");
//                String inputText = inputText();
//                // 输入判断需要解析的抖音地址
//                String url2 = decodeHttpUrl(inputText);
//                String uri = getURI(NewUrlDecode(decodeHttpUrl(url2)));
//                System.out.println(url2);
//                if (StringUtils.isEmpty(uri)) {
//                    System.out.println("解析失败！！！联系qq755964539");
//                    throw new RuntimeException();
//                } else System.out.println("链接地址为：" + uri);
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("解析失败！！！联系qq755964539");
//            }
//
//        }
//    }

    public static String getDownloadUrl(String str, Integer test) {
        String url2 = decodeHttpUrl(str);
        Document doc = null;
        try {
            doc = Jsoup.connect(url2).cookie("cookie", "tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                    //模拟手机浏览器
                    .header("user-agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                    //.header("cookie","tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                    .timeout(12138).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析网页标签
        Elements elem = doc.getElementsByTag("script");
        String url1 = elem.toString();
        //正则
        String aweme_id = "itemId: \"([0-9]+)\"";
        String dytk = "dytk: \"(.*)\"";
        Pattern r = Pattern.compile(aweme_id);
        Matcher m = r.matcher(url1);
        while (m.find()) {
            aweme_id = m.group().replaceAll("itemId: ", "").replaceAll("\"", "");
        }
        System.out.println(aweme_id);
        Pattern r1 = Pattern.compile(dytk);
        Matcher m1 = r1.matcher(url1);
        while (m1.find()) {
            dytk = m1.group().replaceAll("dytk: ", "").replaceAll("\"", "");
        }
        System.out.println(dytk);
        /**
         * 一个api解析接口
         */
        String result2 = HttpRequest.get(API[2].replaceAll("ITEM_IDS", aweme_id).replaceAll("DYTK", dytk))
                //模拟手机浏览器
                .header(Header.USER_AGENT, "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")//头信息，多个头信息多次调用此方法即可
                .timeout(12138)//超时，毫秒
                .execute().body();
        System.out.println("我是result2：" + result2);
        try {
            //GOSN解析
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(result2.toString()).getAsJsonObject();
            //之前获取参数的方法
            //String url = jsonObject.get("aweme_detail").getAsJsonObject().get("long_video").getAsJsonArray().get(0).getAsJsonObject().get("video").getAsJsonObject().get("play_addr").getAsJsonObject().get("url_list").getAsJsonArray().get(0).toString().replaceAll("\"", "");
            String url = jsonObject.get("item_list").getAsJsonArray().get(0).getAsJsonObject().get("video").getAsJsonObject().get("play_addr").getAsJsonObject().get("url_list").getAsJsonArray().get(1).getAsString();
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .get().addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
//                            .build();
//                    Response response = client.newCall(request).execute();
            url = getURI(url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
