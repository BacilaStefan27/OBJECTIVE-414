package Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.ListIterator;

@DefaultUrl("https://www.ixxus.com/")
public class NavBar extends PageObject {

	@FindBy(id = "menu-item-752")
	public WebElementFacade Home;

	@FindBy(id = "menu-item-40")
	public WebElementFacade Sectors;

	@FindBy(id = "menu-item-435")
	public WebElementFacade Solutions;

	@FindBy(id = "menu-item-39")
	public WebElementFacade Partners;

	@FindBy(id = "menu-item-575")
	public WebElementFacade Clients;

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
		ListIterator<WebElement> ListIt=Sect1.listIterator();
		while(ListIt.hasNext()) {
			WebElement we = ListIt.next();

			if (we.getText().contains("Not for Profit")) {
				we.click();
			}
		}


	}

}
