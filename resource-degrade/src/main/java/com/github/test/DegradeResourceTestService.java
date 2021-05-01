package com.github.test;

import com.github.resource.DegradeResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author tangsong
 * @date 2021/4/17 23:54
 */
@Service
public class DegradeResourceTestService {


    @DegradeResource(fallback = "findByIdFromCacheFallback1")
    public String findById(String id) {
        int i = Integer.parseInt(id);
        System.out.println("id=" + id);
        return "ok = " + id;
    }

    @DegradeResource(fallback = "findByIdFromCacheFallback2", exceptionHandle = {NumberFormatException.class})
    public String findByIdWithException(String id) {
        int i = Integer.parseInt(id);
        System.out.println("id=" + id);
        return "ok = " + id;
    }

    /**
     * 支持指定fallback method的class，将降级方法统一放置指定的class中
     *
     */
    @DegradeResource(fallback = "findByIdFromCacheFallback3", exceptionHandle = {NumberFormatException.class},
            fallbackClass = {FallbackClassService.class})
    public String findByIdWithFallbackClass(String id) {
        int i = Integer.parseInt(id);
        System.out.println("id=" + id);
        return "ok = " + id;
    }


    /**
     * fallback method可以只接受原始函数的参数
     */
    public String findByIdFromCacheFallback1(String id) {
        return "fallback1 = " + id;
    }

    /**
     * fallback method 不仅可以接收原始方法的参数，还可以接收具体的Exception
     *
     */
    public String findByIdFromCacheFallback2(String id, Throwable e) {
        System.out.println("fallback method exception:" + e);
        return "fallback2 = " + id;
    }

}
