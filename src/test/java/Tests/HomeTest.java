package Tests;

import Pages.NavBar;
import Steps.NavBarSteps;
import Utils.MyRunner;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import org.fluentlenium.core.annotation.Page;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.util.Map;

@RunWith(MyRunner.class)
public class HomeTest {

	@Managed()
	WebDriver driver;

	@Page
	NavBar NavPage;

	@Steps
	NavBarSteps NavStep;

	@Before
	public void setup(){
		NavPage.open();
		Map<String,String> Titles = Serenity.getCurrentSession().getMetaData();
	}

	@Title("C1")
	@Test
	public void Test1(){
		Assert.assertTrue("Incorrect page title!!!",driver.getTitle().contains("Work With Ixxus"));
	}

	@Title("C2")
	@WithTag("case:C2")
	@Test
	public void Test2(){
		NavStep.returnToMain();
	}

	@Test
	@Title("C3")
	@Ignore
	public void Test3(){
		NavStep.checkallbuttons();
	}

	@Title("C4")
	@Test
	public void Test4(){
		NavStep.click2ndSector();
		Assert.assertTrue(false);
	}
}
