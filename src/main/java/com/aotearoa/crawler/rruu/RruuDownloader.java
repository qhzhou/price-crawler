package com.aotearoa.crawler.rruu;

import com.aotearoa.crawler.driver.CustomizedChromeDriver;
import com.aotearoa.crawler.resource.SimplePool;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;

/**
 * Created by qianhao.zhou on 10/3/16.
 */
public class RruuDownloader extends AbstractDownloader {

    private static final Logger logger = LoggerFactory.getLogger(RruuDownloader.class);

    private final SimplePool<CustomizedChromeDriver> pool;

    public RruuDownloader(SimplePool<CustomizedChromeDriver> chromeDriverPool) {
        this.pool = chromeDriverPool;
    }

    public Page download(Request request, Task task) {
        CustomizedChromeDriver chromeDriver = pool.acquireResource();
        if (chromeDriver == null) {
            logger.warn("cannot get chrome driver, maybe the pool has been shutdown");
            return null;
        }
        try {
            chromeDriver.get(request.getUrl());
            chromeDriver.findElement(By.xpath("//*[@id=\"fb_pshow\"]/div[1]/div/div[1]/div/a[3]")).click();
            Page page = new Page();
            page.setRawText(chromeDriver.getPageSource());
            page.setUrl(new PlainText(request.getUrl()));
            page.setRequest(request);
            return page;
        } finally {
            pool.returnResource(chromeDriver);
        }
    }

    public void setThread(int threadNum) {

    }
}
