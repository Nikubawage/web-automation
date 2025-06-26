package com.webplat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public By usernameField = By.id("fullName");
    public By passwordField = By.id("Password");
    public By loginButton = By.xpath("//button[contains(text(), ' Sign In')]");
    public By errorToast = By.xpath("//*[contains(text(),'Invalid username or password')]");
    public By forgotPasswordLink = By.xpath("//a[contains(@class, 'forgot')]");
    public By sendButton = By.xpath("//button[contains(text(), 'Send')]");
    public By forgotMessage = By.xpath("//p-toastitem//div[contains(text(), 'Username')]");
    public By instagramIcon = By.xpath("//a[contains(@href, 'instagram')]");

    public void openLoginPage() {
        driver.get("https://merchant1.uatdev.in/auth/login");
    }
}
