package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exception.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	private WebDriver driver;
    private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	private void nullCheck(String value) {
		if (value == null) {
			throw new ElementException("Value is null" + value);
		}
	}
	
	private void highlightElement(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}

	@Step("finding element using locator : {0}")
	public WebElement getElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			highlightElement(element);
			return element;
		} catch (NoSuchElementException e) {
			System.out.println("Element is no present on this page " + locator);
			e.printStackTrace();
			return null;
		}

	}

	public void doSendKeys(By locator, String Value) {
		nullCheck(Value);
		getElement(locator).clear();
		getElement(locator).sendKeys(Value);

	}

	public void doSendKeys(By locator, String Value, int timeOut) {
		nullCheck(Value);
		waitForElementVisibility(locator, timeOut).clear();
		waitForElementPresence(locator, timeOut).sendKeys(Value);

	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(By locator, int timeOut) {
		waitForElementPresence(locator, timeOut).click();
	}

	public boolean doIsDisplayed(By locator) {
		try {
			boolean flag = getElement(locator).isDisplayed();
			return flag;
		} catch (NoSuchElementException e) {
			System.out.println("Element with this locator " + locator + "is not present");
			return false;
		}

	}

	public boolean isElemenentDisplayed(By locator) {
		int eleCount = getElements(locator).size();
		if (eleCount == 1) {
			System.out.println("Single element is presnt " + locator);
			return true;
		} else {
			System.out.println("Multiple or zero elements are  present " + locator);
			return false;
		}
	}

	public boolean isElemenentDisplayed(By locator, int expectElementCount) {
		int eleCount = getElements(locator).size();
		if (eleCount == expectElementCount) {
			System.out.println("Single element is presnt " + locator + " With the occurrence of element count "
					+ expectElementCount);
			return true;
		} else {
			System.out.println("Multiple or zero elements are  present " + locator
					+ " With the occurrence of element count " + expectElementCount);
			return false;
		}
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String AttrNmae) {
		return getElement(locator).getDomProperty(AttrNmae);
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<String> getElemenTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String Text = e.getText();
			if (Text.length() != 0) {
				eleTextList.add(Text);
			}
		}
		return eleTextList;
	}

	public List<String> getAttributeList(By locator, String atttrName) {
		List<WebElement> ListImages = getElements(locator);
		List<String> AttrList = new ArrayList<String>();
		for (WebElement e : ListImages) {
			String attrVal = e.getDomAttribute(atttrName);
			if (attrVal != null) {
				AttrList.add(attrVal);
			}
		}
		return AttrList;
	}
	public boolean isElementSelected(By locator) {
	    return getElement(locator).isSelected();
	}
	// *************** Select DropDown Utilities ******************//

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectByText(By locator, String VisibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(VisibleText);
	}

	public List<String> getDropdownOptionsListText(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optiontextList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String Text = e.getText();
			optiontextList.add(Text);
		}
		return optiontextList;
	}

	public int getDropDownOptionCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	public void SelectDropdownWithoutSelectMethods(By locator, String Value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optiontextList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String Text = e.getText();
			if (Text.equals(Value.trim())) {
				e.click();
				break;
			}
		}
	}

	public void SelectDropdownWithoutSelectClass(By locator, String Value) {

		List<WebElement> optionsList = getElements(locator);

		for (WebElement e : optionsList) {
			String Text = e.getText();
			if (Text.equals(Value.trim())) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchField, String searchKey, By suggestion, String Value) throws InterruptedException {
		doSendKeys(searchField, searchKey);
		Thread.sleep(3000);
		List<WebElement> sugList = getElements(suggestion);
		System.out.println(sugList.size());
		for (WebElement e : sugList) {
			String Text = e.getText();
			if (Text.equals(Value.trim())) {
				e.click();
				break;
			}
		}
	}

//************************** Actions Utils *********************************//

	public void handleParentSubmenu(By ParentLocator, By ChildLocator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(ParentLocator)).perform();
		doClick(ChildLocator);
	}

	public void doDragAndDrop(By source, By target) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(source), getElement(target)).perform();
	}

	public void doActionSendkeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionCick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator));
	}

	public void doActionSendkeysWithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}

	public void handle4LevelSubMenuUsingClick(By level1, By level2, By level3, By level4) throws InterruptedException {
		doClick(level1);
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);
		doClick(level4);

	}

	public void handle4LevelSubMenuUsingHover(By level1, By level2, By level3, By level4) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);
		doClick(level4);

	}

	// ******************************** Wait Utils
	// **********************************************************//

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element= wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public WebElement waitForElementVisibility(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking that the title contains a case-sensitive
	 * substring
	 * 
	 * @param titleFraction
	 * @param timeOut
	 * @return
	 */

	public String waitForTitleContain(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("Title not found");
		}
		return driver.getTitle();

	}

	/**
	 * An expectation for checking the title of a page.
	 * 
	 * @param title
	 * @param timeOut
	 * @return
	 */
	public String waitForTitleToBe(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("Title not found");
		}
		return driver.getTitle();
	}

	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * 
	 * @param UrlFraction
	 * @param timeOut
	 * @return
	 */

	public String waitForUrlContain(String UrlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(UrlFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not found");
		}
		return driver.getCurrentUrl();
	}

	/**
	 * An expectation for the URL of the current page to be a specific url.
	 * 
	 * @param Url
	 * @param timeOut
	 * @return
	 */

	public String waitForUrlToBe(String Url, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlToBe(Url))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not found");
		}
		return driver.getCurrentUrl();
	}

	public Alert waitJsAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		Alert alert = waitJsAlert(timeOut);
		String text = alert.getText();
		alert.accept();
		return text;

	}

	public void acceptAlert(int timeOut) {
		Alert alert = waitJsAlert(timeOut);
		alert.accept();
	}

	public void dismissAlert(int timeOut) {
		Alert alert = waitJsAlert(timeOut);
		alert.dismiss();
	}

	public void alertSendkeys(int timeOut, String value) {
		Alert alert = waitJsAlert(timeOut);
		alert.sendKeys(value);
		alert.accept();

	}

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frame is available it switches the given driver to the specified
	 * frame.
	 * 
	 * @param frameLocator
	 * @param timeOut
	 */
	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to.
	 * 
	 * If the frame is available it switches the given driver to the specified
	 * frameIndex.
	 * 
	 * @param frameIndex
	 * @param timeOut
	 */

	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));

	}

	/**
	 * Wait will ignore instances of NotFoundException that are encountered (thrown)
	 * by default in the'until' condition, and immediately propagate all others. You
	 * can add more to the ignore list bycalling ignoring(exceptions to add).
	 * 
	 * @param frameIDOrName
	 * @param timeOut
	 */
	public void waitForFrameById(String frameIDOrName, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));

	}

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frame is available it switches the given driver to the specified
	 * web element.
	 * 
	 * @param frameElement
	 * @param timeOut
	 */
	public void waitForFrameByWebElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}

	public boolean waitForWindowsToBe(int totalWindows, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
	}

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int intervalTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("===element is not found===");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int intervalTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchElementException.class)
				.withMessage("===element is not found===");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForPresenceOfElementsLocator(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locatorare visible. Visibility means that the elements are not only
	 * displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForVisibilityOfElementsLocator(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}catch(Exception e) {
			return List.of();
		}
		
	}
}