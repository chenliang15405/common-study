package com.github.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tangsong
 * @date 2021/4/17 23:53
 */
@Controller
public class DegradeResourceTestController {

    @Autowired
    private DegradeResourceTestService degradeResourceTestService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam("id") String id) {
        return degradeResourceTestService.findById(id);
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String test2(@RequestParam("id") String id) {
        return degradeResourceTestService.findByIdWithException(id);
    }

    @RequestMapping("/test3")
    @ResponseBody
    public String test3(@RequestParam("id") String id) {
        return degradeResourceTestService.findByIdWithFallbackClass(id);
    }
}
