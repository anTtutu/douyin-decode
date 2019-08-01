package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    static final String KSAPI = "http://api.ksapisrv.com/rest/n/photo/info2?kpf=IPHONE&net=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8_5&appver=6.6.2.1004&kpn=KUAISHOU&mod=iPhone8%2C1&c=a&ud=214451601&did_gt=1551354412209&ver=6.6&sys=ios12.3.1&did=39896A34-183F-44F6-A9D3-29553C501FE3&isp=CMCC";


    @GetMapping(value = {"/test", "9527"})
    public String test() {
        return "OK";
    }

    @PostMapping(value = "/douyin/decode")
    public String getUrl(String url, Model model) {
        model.addAttribute("videoUrl", DouYinDecodeMain.getDownloadUrl(url));
        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/kstest")
    @ResponseBody
    public String ksSig() {
        Map<String, String> stringStringMap = KuaiShouDecodeMain.urlDecode(KSAPI);
        System.out.println(stringStringMap);
        List<String> strings = KuaiShouDecodeMain.sigAlgorithm(stringStringMap, "5193776284807906285");
        StringBuffer sb = new StringBuffer();
        for (String s : strings) {
            sb.append(s);
        }
        String clock = CPU.INSTANCE.getClock(null,sb.toString().getBytes(), 21);
        System.out.println(clock);
        return clock;
    }
}
