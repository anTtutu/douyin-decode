package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {


    @GetMapping(value = {"/test", "9527"})
    public String test() {
        return "OK";
    }

    @PostMapping(value = "/douyin/decode")
    public String getUrl(String url, Model model) {
        model.addAttribute("videoUrl", DouYinDecodeMain.getDownloadUrl(url,1));
        return "index";
    }

    @GetMapping("/douyin")
    public String index() {
        return "index";
    }


//    @RequestMapping("/0327")
//    @ResponseBody
    public String ks_test() {
        try {
            CPU instance = CPU.ar;
            String clock = instance.getClock(new Object(), "12345601212".getBytes(), 17);
            System.out.println(clock);
            return clock;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }


    @GetMapping("/kuaishou")
    public String indexKs() {
        return "indexks";
    }


    @PostMapping(value = "/ks/decode")
    public String getKSUrl(String url, Model model) {
        model.addAttribute("ksvideoUrl", KuaiShouDecodeMain.ksdecode(url));
        return "indexks";
    }

}
