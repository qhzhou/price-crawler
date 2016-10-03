package com.aotearoa.crawler.rruu;

import com.aotearoa.crawler.driver.CustomizedChromeDriver;
import com.aotearoa.crawler.resource.SimplePool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import us.codecraft.webmagic.Site;

/**
 * Created by qianhao.zhou on 10/4/16.
 */
@Configuration
public class RruuConfig {

    @Autowired
    private SimplePool<CustomizedChromeDriver> pool;

    @Bean
    public RruuDownloader rruuDownloader() {
        return new RruuDownloader(pool);
    }

    @Bean
    public Site site() {
        return Site.me().setRetryTimes(3).setSleepTime(1000);
    }
}
