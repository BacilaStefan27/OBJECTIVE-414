package Tests;

import Pages.NavBar;
import Steps.NavBarSteps;
import Utils.MyRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.fluentlenium.core.annotation.Page;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

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
	}

	@After
	public  void cleanup(){
		driver.quit();
	}

	@Title("C163233")
	@Test
	public void Test1(){
		Assert.assertTrue("Incorrect page title!!!",driver.getTitle().contains("Work With Ixxus"));
	}

	@Title("C163234")
	@Test
	public void Test2(){
		NavStep.returnToMain();
	}

	@Test
	@Title("C163235")
//	@Ignore
	public void Test3(){
		NavStep.checkallbuttons();
	}

	@Title("C163236")
	@Test
	public void Test4(){
		NavStep.click2ndSector();
//		Assert.assertTrue(false);
	}
}
