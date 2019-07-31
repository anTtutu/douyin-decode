package com.example.demo;

import cn.hutool.crypto.SecureUtil;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;

public class Test {
    static final String KSAPI = "http://api.ksapisrv.com/rest/n/photo/info2?kpf=IPHONE&net=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8_5&appver=6.6.2.1004&kpn=KUAISHOU&mod=iPhone8%2C1&c=a&ud=214451601&did_gt=1551354412209&ver=6.6&sys=ios12.3.1&did=39896A34-183F-44F6-A9D3-29553C501FE3&isp=CMCC";

    /**
     * @param args
     */
    public static void main(String[] args) {

        Map<String, String> stringStringMap = KuaiShouDecodeMain.urlDecode(KSAPI);
        System.out.println(stringStringMap);
        List<String> strings = KuaiShouDecodeMain.sigAlgorithm(stringStringMap, "5193776284807906285");
        StringBuffer sb = new StringBuffer();
        for (String s : strings) {
            sb.append(s);
        }
        String clock = CPU.INSTANTCE.getClock(null, sb.toString().getBytes(), 32);
        System.out.println(clock);
    }
}