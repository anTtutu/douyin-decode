package com.example.demo;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.demo.DouYinDecodeMain.isContainChinese;

public class KuaiShouDecodeMain {


    static final String KSAPI = "http://api.ksapisrv.com/rest/n/photo/info2?kpf=IPHONE&net=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8_5&appver=6.6.2.1004&kpn=KUAISHOU&mod=iPhone8%2C1&c=a&ud=214451601&did_gt=1551354412209&ver=6.6&sys=ios12.3.1&did=39896A34-183F-44F6-A9D3-29553C501FE3&isp=CMCC";
    /**
     * 下面接口也可以使用
     * http://api.gifshow.com/rest/n/photo/info?
     */

    static final String KSAPI2 = "http://api.ksapisrv.com/rest/n/photo/info?";//

    static final String KSAPI2_NEXT = "client_key=56c3713c&photoIds=PHOTOIDS";

    public static void test(String[] args) {
        //http://m.gifshow.com/s/klbuaBKO
        //http://m.gifshow.com/s/pEixo6ss
        String url2 = "http://m.gifshow.com/s/pEixo6ss";
        Document doc = null;
        try {
            doc = Jsoup.connect(url2)
                    //模拟手机浏览器
                    .header("user-agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                    //.header("cookie","tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                    .timeout(12138).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析网页标签
        Elements elem = doc.getElementsByTag("div");
        String url1 = elem.toString();
        int sessionId = url1.indexOf("sessionId");
        int status = url1.lastIndexOf("status");
        //切分第一次
        String substring = url1.substring(sessionId, status).replaceAll("&quot;", "\"");
        //切分第二次
        int video = substring.indexOf("\"video\":");
        String substring1 = substring.substring(video);
        int i = substring1.indexOf("}");
        String substring2 = substring1.substring(0, i).replaceAll("\"video\":", "") + "}";
        //json解析
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(substring2).getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        //API请求地址
        //http://api.ksapisrv.com/rest/n/photo/info2?kpf=IPHONE&net=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8_5&appver=6.6.2.1004&kpn=KUAISHOU&mod=iPhone8%2C1&c=a&ud=214451601&did_gt=1551354412209&ver=6.6&sys=ios12.3.1&did=39896A34-183F-44F6-A9D3-29553C501FE3&isp=CMCC
        //链式构建请求
        Map<String, Object> param = new HashMap<>();
        //多余参数，可以不要
        //param.put("__NStokensig", "aee41ca192ea6b361454b9178a643dc4019ee6c3897def5b945bac09fc3fa4f10bf");
        param.put("client_key", "56c3713c");
        param.put("country_code", "cn");
        param.put("language", "zh-Hans-CN;q=1");
        param.put("photoInfos", "[{\"photoId\":\"" + id + "\"}]");
        //3c8c98ede159889af7936d1d8b62c52d
        //108503c18b12985f5bbd99581cc9fb5a
        param.put("sig", "108503c18b12985f5bbd99581cc9fb5a");
        param.put("token", "04b54afc4f9944a681622d9e125a74c8-214451601");
        String result2 = HttpRequest.post(KSAPI)
                .header("user-agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")//头信息，多个头信息多次调用此方法即可
                .form(param)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        //main_mv_urls
        //json解析
        JsonObject jsonObject2 = jsonParser.parse(result2).getAsJsonObject();
        JsonElement jsonElement1 = jsonObject2.get("photos").getAsJsonArray().get(0).getAsJsonObject().get("main_mv_urls").getAsJsonArray().get(0).getAsJsonObject().get("url");
        System.out.println(jsonElement1.toString().replaceAll("\"", ""));
    }

    //失败的签名算法
    public static List<String> sigAlgorithm(Map<String, String> map, String id) {

        Map<String, Object> map1 = new HashMap<>();
        //param.put("__NStokensig", "aee41ca192ea6b361b9178a643dc4019ee6c3897def5b945bac09fc3fa4f10bf");
        map1.put("client_key", "56c3713c");
        map1.put("country_code", "cn");
        map1.put("language", "zh-Hans-CN;q=1");
        map1.put("photoInfos", "[{\"photoId\":\"" + id + "\"}]");
        //3c8c98ede159889af7936d1d8b62c52d
        //9f0f4a54bf6898ddd7504d8051a326ba
        //b78898a88147e82a7fd229631774be9a
        //map1.put("sig", "b78898a88147e82a7fd229631774be9a");
        map1.put("token", "04b54afc4f9944a681622d9e125a74c8-214451601");
        List<String> list = new ArrayList<>();
        for (Map.Entry ent : map.entrySet()) {
            list.add(ent.getKey().toString() + "=" + ent.getValue().toString());
        }
        for (Map.Entry ent1 : map1.entrySet()) {
            list.add(ent1.getKey().toString() + "=" + ent1.getValue().toString());
        }
        Collections.sort(list);
        System.out.println(list);

        return list;
    }

    //地址链接解析
    public static Map<String, String> urlDecode(String str) {
        if (StringUtils.isEmpty(str)) throw new RuntimeException("str is null");
        Map<String, String> map = new HashMap<>();
        String[] split = str.split("\\?");
        String trim = split[1].trim();
        String[] split1 = trim.split("&");
        for (String param : split1) {
            String[] keyValue = param.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    public static void main(String[] args) {
        String s = "IG俱乐部小助理发了一个快手作品，一起来看！ http://kphshanghai.m.chenzhongtech.com/s/cUlxMnpr 复制此链接，打开【快手】直接观看！\n";
        String url2 = decodeKSHttpUrl(s);
        Document doc = null;
        try {
            doc = Jsoup.connect(url2)
                    //模拟手机浏览器
                    .header("user-agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                    //.header("cookie","tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                    .timeout(12138).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析网页标签
        String data_pagedata = doc.getElementsByTag("div").attr("data-pagedata");
        //正则匹配
        String ks = "\"photoId\":\"([a-z0-9]*)\"";
        Pattern r = Pattern.compile(ks);
        Matcher m = r.matcher(data_pagedata);
        String str = null;
        while (m.find()) {
            str = m.group().replaceAll("\"", "").replaceAll("photoId:", "");
        }
        //返回参数处理
        String result2 = HttpRequest.get(urlAppend(str))
                //模拟手机浏览器
                .header(Header.USER_AGENT, "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                //头信息，多个头信息多次调用此方法即可
                .timeout(12138)//超时，毫秒
                .execute().body();
        String data = "\"url\":\"(.*)\"";
        Pattern r1 = Pattern.compile(data);
        Matcher m1 = r1.matcher(result2);
        while (m1.find()) {
            str = m1.group(0).replaceAll("\"", "").replaceAll("url:", "");
        }
        System.out.println(str);
    }


    public static String decodeKSHttpUrl(String url) {
        // 检测是否有中文，如果没有中文就是直接地址
        boolean containChinese = isContainChinese(url);
        if (containChinese) {
            int start = url.indexOf("http");
            int end = url.lastIndexOf(" 复制此链接");
            String decodeurl = url.substring(start, end);
            return decodeurl;
        } else
            return url;
    }

    //url拼接
    public static String urlAppend(String photoId) {
        return KSAPI2 + KSAPI2_NEXT.replaceAll("PHOTOIDS", photoId) + "&sig=" + getSig(photoId);
    }

    //sig计算
    public static String getSig(String photoId) {
        String photoids = KSAPI2_NEXT.replaceAll("PHOTOIDS", photoId).replaceAll("&", "") + new String(new byte[]{50, 51, 99, 97, 97, 98, 48, 48, 51, 53, 54, 99});
        return SecureUtil.md5().digestHex(photoids);
    }
    //接口地址访问
    public static String ksdecode(String s) {
        String url2 = decodeKSHttpUrl(s);
        Document doc = null;
        try {
            doc = Jsoup.connect(url2)
                    //模拟手机浏览器
                    .header("user-agent", "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                    //.header("cookie","tt_webid=6711334817457341965; _ga=GA1.2.611157811.1562604418; _gid=GA1.2.1578330356.1562604418; _ba=BA0.2-20190709-51")
                    .timeout(12138).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析网页标签
        String data_pagedata = doc.getElementsByTag("div").attr("data-pagedata");
        //正则匹配
        String ks = "\"photoId\":\"([a-z0-9]*)\"";
        Pattern r = Pattern.compile(ks);
        Matcher m = r.matcher(data_pagedata);
        String str = null;
        while (m.find()) {
            str = m.group().replaceAll("\"", "").replaceAll("photoId:", "");
        }
        String returnData = HttpRequest.get(urlAppend(str))
                //模拟手机浏览器
                .header(Header.USER_AGENT, "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                //头信息，多个头信息多次调用此方法即可
                .timeout(12138)//超时，毫秒
                .execute().body();
        String data = "\"url\":\"(.*)\"";
        Pattern r1 = Pattern.compile(data);
        Matcher m1 = r1.matcher(returnData);
        while (m1.find()) {
            str = m1.group(0).replaceAll("\"", "").replaceAll("url:", "");
        }
        return str;
    }
}
