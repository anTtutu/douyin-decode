package com.example.demo;

import cn.hutool.core.codec.Base32;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

import java.security.Signature;
import java.util.*;

public class Test {
    static final String KSAPI = "http://api.ksapisrv.com/rest/n/photo/info2?kpf=IPHONE&net=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8_5&appver=6.6.2.1004&kpn=KUAISHOU&mod=iPhone8%2C1&c=a&ud=214451601&did_gt=1551354412209&ver=6.6&sys=ios12.3.1&did=39896A34-183F-44F6-A9D3-29553C501FE3&isp=CMCC";

    public static native int helloworld();

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        Map<String, String> stringStringMap = urlDecode(KSAPI);
        System.out.println(stringStringMap);
        List<String> strings = sigAlgorithm(stringStringMap, "5193776284807906285");
        StringBuffer sb = new StringBuffer();
        for (String s : strings) {
            sb.append(s);
        }
        String s = SecureUtil.md5(SecureUtil.sha256(sb.toString()));
        System.out.println(s);
    }

    //签名算法
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
        //map1.put("token", "04b54afc4f9944a681622d9e125a74c8-214451601");
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
        //if (StringUtils.isEmpty(str)) throw new RuntimeException("str is null");
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
}