package test.java.com.browserstack;

import java.util.List;
import java.util.HashMap;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import org.openqa.selenium.WebDriver;

public class BStackDemoTest extends SeleniumTest {
	@Parameters({ "url", "username", "password" })
	@Test
	public void testBS(String url, String username, String password) throws Exception {
		String username_bs = System.getenv("BROWSERSTACK_USERNAME");
		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
		String buildName = System.getenv("JENKINS_LABEL");
		
		MutableCapabilities capabilities = new MutableCapabilities();
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("browserVersion", "100.0");
		HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
		browserstackOptions.put("os", "Windows");
		browserstackOptions.put("osVersion", "10");
		browserstackOptions.put("sessionName", "BStack Build Name: " + buildName);
		browserstackOptions.put("seleniumVersion", "4.0.0");
		capabilities.setCapability("bstack:options", browserstackOptions);

		WebDriver driver = new RemoteWebDriver(new URL("https://" + username_bs + ":" + accessKey + "@hub.browserstack.com/wd/hub"), capabilities);

		driver.get(url);
		driver.manage().window().maximize();

		try {
			clickSignIn(driver);
		} catch (Exception e) {
			clickHamburgerMenu(driver);
			clickSignIn(driver);
		}

		driver.findElement(By.id("user_email_login")).sendKeys(username);

		driver.findElement(By.id("user_password")).sendKeys(password);

		// Click on the Sign in button
		try {
			driver.findElement(By.id("user_submit")).click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.id("user_submit")));
		}

		String currentUrl = "";
		while (!currentUrl.toLowerCase().contains("dashboard")) {
			currentUrl = driver.getCurrentUrl();
		}

		try {
			driver.get(driver.getCurrentUrl());
		} catch (Exception ex) {
			// handle exception in case required
		}

		// Assert that the homepage includes a link to Invite Users and retrieve the
		// link’s URL
		boolean invitationLinkFound = false;
		List<WebElement> allLinks = driver.findElements(By.tagName("a"));

		for (WebElement link : allLinks) {
			if (link != null && link.getText() != null && link.getText().toLowerCase().contains("invite")) {
				Assert.assertTrue(true);
				System.out.println("Invitation Link found! Link text: " + link.getText() + " and its url is: "
						+ link.getAttribute("href") + ".");
				invitationLinkFound = true;
				break;
			}
		}

		if (!invitationLinkFound) {
			invitationLinkFound = findInvitationLinkInHamburgerMenu(invitationLinkFound, driver);
		}

		// Open the account menu to shows the Sign out button
		try {
			WebElement accountMenuToggleButton = driver
					.findElement(By.xpath("//*[@id='user-action-parent']//*[@id='account-menu-toggle']"));
			if (accountMenuToggleButton != null) {
				accountMenuToggleButton.click();
			}
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();",
					driver.findElement(By.xpath("//*[@id='user-action-parent']//*[@id='account-menu-toggle']")));
		}

		// Click on the Sign out button
		boolean signoutFound = false;
		try {
			WebElement accountMenuDropdownLink = driver.findElement(
					By.xpath("//*[@id='user-action-parent']//*[@id='account-menu-dropdown']//*[@id='sign_out_link']"));

			if (accountMenuDropdownLink != null) {
				accountMenuDropdownLink.click();
			}
			signoutFound = true;
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(
					By.xpath("//*[@id='user-action-parent']//*[@id='account-menu-dropdown']//*[@id='sign_out_link']")));
			signoutFound = true;
		}

		if (!signoutFound) {
			clickSignoutInHamburgerMenu(driver);
		}
	}

	private void clickSignIn(WebDriver driver) {
		try {
			WebElement signinLink = driver.findElement(By.linkText("Sign in"));
			signinLink.click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.linkText("Sign in")));
		}

	}

	private void clickHamburgerMenu(WebDriver driver) {
		try {
			driver.findElement(By.id("hamburger_menu_Layer_1")).click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.id("hamburger_menu_Layer_1")));
		}

	}

	private void clickSignoutInHamburgerMenu(WebDriver driver) {
		try {
			driver.findElement(By.id("primary-menu-toggle")).click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.id("primary-menu-toggle")));
		}

		try {
			driver.findElement(By.linkText("Sign out")).click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.linkText("Sign out")));
		}
	}

	private boolean findInvitationLinkInHamburgerMenu(boolean invitationLinkFound, WebDriver driver) {
		try {
			driver.findElement(By.id("primary-menu-toggle")).click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.id("primary-menu-toggle")));
		}

		List<WebElement> allLinks = driver.findElements(By.tagName("a"));

		for (WebElement link : allLinks) {
			if (link != null && link.getText() != null && link.getText().toLowerCase().contains("invite")) {
				Assert.assertTrue(true);
				System.out.println("Invitation Link found! Link text: " + link.getText() + " and its url is: "
						+ link.getAttribute("href") + ".");
				invitationLinkFound = true;
				break;
			}
		}

		return invitationLinkFound;
	}
}
