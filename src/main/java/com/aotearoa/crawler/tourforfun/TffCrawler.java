package com.aotearoa.crawler.tourforfun;

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
 * Created by qianhao.zhou on 10/6/16.
 */
@Component
public class TffCrawler implements PageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TffCrawler.class);

    @Autowired
    private Site site;

    @Value("${tff.url}")
    private String url;

    @Autowired
    private TffDownloader downloader;

    @Override
    public void process(Page page) {
        page.putField("company", "tour for fun");
        page.putField("price", page.getHtml().xpath("//*[@id=\"J_Price\"]/span/text()"));
        page.putField("date", new Date().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void execute() {
        logger.info("tff crawler started, url:" + url);
        logger.info("downloader:" + downloader);
        Spider.create(this).
                addUrl(url).
                addPipeline(new ConsolePipeline()).
                setDownloader(this.downloader).
                thread(1).
                run();
        logger.info("tff crawler finished");
    }
}
