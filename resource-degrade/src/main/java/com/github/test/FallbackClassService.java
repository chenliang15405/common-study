package com.github.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tangsong
 * @date 2021/4/24 17:45
 */
@Slf4j
public class FallbackClassService {

//    public static String findByIdFromCacheFallback(String id) {
//        return "fallback class service = " + id;
//    }

    public String findByIdFromCacheFallback(String id, Throwable e) {
        log.warn("execute fallback method, e", e);
        return "fallback class service = " + id;
    }
}
