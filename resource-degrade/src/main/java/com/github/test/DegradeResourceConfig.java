package com.github.test;

import com.github.resource.DegradeResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tangsong
 * @date 2021/4/24 18:47
 */
@Configuration
public class DegradeResourceConfig {

    @Bean
    public DegradeResourceAspect degradeResourceAspect() {
        return new DegradeResourceAspect();
    }

}
