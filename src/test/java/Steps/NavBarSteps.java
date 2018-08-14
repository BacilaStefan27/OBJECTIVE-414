package Steps;

import Pages.NavBar;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;

@SuppressWarnings("ALL")
public class NavBarSteps  {

	NavBar NavPage;

	@StepGroup
	public void checkallbuttons(){
		checkButton();
		returnToMain();
	}

	@Step
	public void checkButton(){
		NavPage.checkThatButtonIsVisible();
	}

	@Step
	public void returnToMain(){
		NavPage.goHome();
	}

	@Step
	public void openDropdopwn1(){
		NavPage.checkDropdown1();
	}

	@Step
	public void click2ndSector() {
		openDropdopwn1();
	}
}
