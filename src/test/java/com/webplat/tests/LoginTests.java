package com.webplat.tests;

import com.webplat.base.BaseTest;
import com.webplat.pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.time.Duration;

public class LoginTests extends BaseTest {

    LoginPage loginPage;
    WebDriverWait wait;

    @Test(priority = 1)
    public void verifyLoginPageLoadPerformance() {
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        long start = System.currentTimeMillis();

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.usernameField));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.passwordField));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.loginButton));

        long end = System.currentTimeMillis();
        double duration = (end - start) / 1000.0;
        System.out.println("Login elements loaded in: " + duration + " seconds");

        Assert.assertTrue(duration <= 30, "Page load exceeded 30 seconds");
    }

    @Test(priority = 2)
    public void verifyInvalidLogin() {
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        driver.findElement(loginPage.usernameField).sendKeys("9999999999");
        driver.findElement(loginPage.passwordField).sendKeys("Amitabh");
        driver.findElement(loginPage.loginButton).click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.errorToast)).isDisplayed(),
                "Invalid login toast not displayed");
    }

    @Test(priority = 3)
    public void verifyForgotPasswordFlow() {
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        if (driver.findElements(loginPage.forgotPasswordLink).size() > 0) {
            driver.findElement(loginPage.forgotPasswordLink).click();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.usernameField)).sendKeys("9999999999");
            driver.findElement(loginPage.sendButton).click();
            Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.forgotMessage)).isDisplayed(),
                    "Forgot password error not displayed");
        } else {
            System.out.println("Forgot password option is not present at login page");
        }
    }

    @Test(priority = 4, dependsOnMethods = {"verifyForgotPasswordFlow"})
    public void verifyInstagramRedirect() {
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        if (driver.findElements(loginPage.forgotPasswordLink).size() == 0) {
            String originalWindow = driver.getWindowHandle();
            driver.findElement(loginPage.instagramIcon).click();

            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Assert.assertTrue(driver.getCurrentUrl().contains("instagram.com"), "Instagram redirect failed");
        }
    }
}
