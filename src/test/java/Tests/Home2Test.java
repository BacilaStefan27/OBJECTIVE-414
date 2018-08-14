package Tests;

import Pages.NavBar;
import Steps.NavBarSteps;
import Utils.MyRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.fluentlenium.core.annotation.Page;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(MyRunner.class)
public class Home2Test {

	@Managed()
	WebDriver driver;

	@Page
	NavBar NavPage;

	@Steps
	NavBarSteps NavStep;

	@Before
	public void setup(){
		NavPage.open();
	}

	@Title("C54")
	@Test
	public void Test1(){
		Assert.assertTrue("Incorrect page title!!!",driver.getTitle().contains("Work With Ixxus"));
	}

	@Title("C55")
	@Test
	public void Test2(){
		NavStep.returnToMain();
	}

	@Test
	@Title("C56")
	@Ignore
	public void Test3(){
		NavStep.checkallbuttons();
	}

	@Title("C57")
	@Test
	public void Test4(){
		NavStep.click2ndSector();
		Assert.fail();
	}
}
