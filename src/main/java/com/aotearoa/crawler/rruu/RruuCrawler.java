package com.aotearoa.crawler.rruu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by qianhao.zhou on 10/3/16.
 */
@Component
public class RruuCrawler implements PageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RruuCrawler.class);

    @Autowired
    private Site site;

    @Autowired
    private RruuDownloader downloader;

    @Value("${rruu.url}")
    private String url;

    public void process(Page page) {
        page.putField("price", page.getHtml().xpath("//*[@id=\"totoaldiv\"]/em/text()"));
        page.putField("time", new Date().toString());
    }

    public Site getSite() {
        return site;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void execute() {
        logger.info("rruu crawler started, url:" + url);
        logger.info("downloader:" + downloader);
        Spider.create(this).
                addUrl(url).
                addPipeline(new ConsolePipeline()).
                setDownloader(this.downloader).
                thread(1).
                run();
        logger.info("rruu crawler finished");
    }
}
