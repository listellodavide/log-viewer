package com.logviewer.tests.web;

import com.google.common.collect.Iterables;
import com.logviewer.logLibs.logback.LogbackLogFormat;
import com.logviewer.mocks.TestFormatRecognizer;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EventDetailsTest extends AbstractWebTestCase {

    @Test
    public void testEventDetails() {
        ctx.getBean(TestFormatRecognizer.class).setFormat(new LogbackLogFormat("%d{yyyy-MM-dd_HH:mm:ss.SSS} [%t] %level %logger - %msg%n"));

        openLog("rendering/one-line-exception.log");

        List<WebElement> records = driver.findElementsByClassName("record");
        WebElement rec = Iterables.getOnlyElement(records);

        new Actions(driver).contextClick(rec).perform();

        driver.findElementByCssSelector(".dropdown-menu li:first-child").click();

        List<WebElement> fieldLabels = driver.findElementsByCssSelector("sl-event-details .field .field-label");

        assertEquals(Arrays.asList("date", "thread", "level", "logger", "msg"),
                fieldLabels.stream().map(WebElement::getText).collect(Collectors.toList()));
    }

}
