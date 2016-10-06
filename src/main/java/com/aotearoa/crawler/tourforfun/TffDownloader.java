package com.aotearoa.crawler.tourforfun;

import com.aotearoa.crawler.driver.CustomizedChromeDriver;
import com.aotearoa.crawler.resource.SimplePool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.PlainText;

/**
 * Created by qianhao.zhou on 10/6/16.
 */
@Component
public class TffDownloader implements Downloader {

    private static final Logger logger = LoggerFactory.getLogger(TffDownloader.class);

    @Autowired
    private SimplePool<CustomizedChromeDriver> pool;

    @Override
    public Page download(Request request, Task task) {
        CustomizedChromeDriver chromeDriver = pool.acquireResource();
        if (chromeDriver == null) {
            logger.warn("cannot get chrome driver, maybe the pool has been shutdown");
            return null;
        }
        try {
            chromeDriver.get(request.getUrl());
//            chromeDriver.findElementByXPath("//*[@id=\"J_SkuWrap\"]/dd[1]/ul/li[3]/a/span").click();
            Page page = new Page();
            page.setRawText(chromeDriver.getPageSource());
            page.setUrl(new PlainText(request.getUrl()));
            page.setRequest(request);
            return page;
        } finally {
            pool.returnResource(chromeDriver);
        }
    }

    @Override
    public void setThread(int threadNum) {

    }
}
