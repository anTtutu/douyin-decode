package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TestController {


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
}
