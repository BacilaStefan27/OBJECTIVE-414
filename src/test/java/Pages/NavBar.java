package Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@DefaultUrl("https://www.ixxus.com/")
public class NavBar extends PageObject {

	@FindBy(id = "menu-item-752")
	private WebElementFacade Home;

	@FindBy(id = "menu-item-40")
	private WebElementFacade Sectors;

	@FindBy(id = "menu-item-435")
	private WebElementFacade Solutions;

	@FindBy(id = "menu-item-39")
	private WebElementFacade Partners;

	@FindBy(id = "menu-item-575")
	private WebElementFacade Clients;

	public NavBar(WebDriver driver) {
		super(driver);
	}


	public void goHome(){
		Home.waitUntilClickable().click();
	}

	public void checkThatButtonIsVisible() {
		Home.shouldBeVisible();
		Sectors.shouldBeVisible();
		Solutions.shouldBeVisible();
		Partners.shouldBeVisible();
		Clients.shouldBeVisible();
	}

	public void checkDropdown1(){
		List<WebElement> Sect1 = Sectors.findElements(By.tagName("ul"));
		Sectors.click();
		for (WebElement we : Sect1) {
			if (we.getText().contains("Not for Profit")) {
				we.click();
			}
		}
	}
}
