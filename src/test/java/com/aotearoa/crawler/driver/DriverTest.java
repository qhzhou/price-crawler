package com.aotearoa.crawler.driver;

import com.aotearoa.crawler.mapper.PriceRecordMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by qianhao.zhou on 19/12/2016.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class DriverTest {

    @Value("${chrome.driver.location}")
    private String chromeDriverLocation;

    @Autowired
    private PriceRecordMapper priceRecordMapper;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
    }

    @Test
    public void test() {
        System.out.println(System.getProperty("webdriver.chrome.driver"));
        assertNotNull(priceRecordMapper);
    }
}
