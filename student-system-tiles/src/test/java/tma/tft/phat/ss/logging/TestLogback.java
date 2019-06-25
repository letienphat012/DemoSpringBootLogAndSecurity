package tma.tft.phat.ss.logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import tma.tft.phat.ss.TestLogLevelApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,classes = TestLogLevelApplication.class)
@ActiveProfiles("logback-test")
public class TestLogback {

    private static final Logger logger = LoggerFactory.getLogger(TestLogback.class);

    @Test
    public void testLogbackConf() {
        logger.info("5 Level of Logback");
        logger.trace("TRACE message");
        logger.debug("DEBUG message");
        logger.info("INFO message");
        logger.warn("WARN message");
        logger.error("ERROR message");
    }
}
