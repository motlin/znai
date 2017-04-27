package com.twosigma.testing.webui.page.path;

import com.twosigma.testing.reporter.TokenizedMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author mykola
 */
public interface ElementsSelector {
    List<WebElement> select(WebDriver driver);
    String description();
    TokenizedMessage tokenizedDescription();
}
