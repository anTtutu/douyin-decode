package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    /**
     * 测试
     * @return
     */
    @GetMapping(value = {"/test", "9527"})
    public String test() {
        return "OK";
    }

    /**
     * 抖音去水印主页
     * @return
     */
    @GetMapping("/douyin")
    public String index() {
        return "index";
    }

    /**
     * 抖音去水印接口
     * @param url
     * @param model
     * @return
     */
    @PostMapping(value = "/douyin/decode")
    public String getUrl(String url, Model model) {
        model.addAttribute("videoUrl", DouYinDecodeMain.getDownloadUrl(url,1));
        return "index";
    }


    /**
     * 快手主页
     * @return
     */
    @GetMapping("/kuaishou")
    public String indexKs() {
        return "indexks";
    }

    /**
     * 快手去水印接口
     * @return
     */
    @PostMapping(value = "/ks/decode")
    public String getKSUrl(String url, Model model) {
        model.addAttribute("ksvideoUrl", KuaiShouDecodeMain.ksdecode(url));
        return "indexks";
    }

}
