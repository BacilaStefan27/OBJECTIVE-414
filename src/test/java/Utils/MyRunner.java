package Utils;

import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public class MyRunner extends SerenityRunner {

	private static JUnitExecutionListener listener2 = new JUnitExecutionListener();
	private static Integer contains = 0;

	public MyRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override public void run(RunNotifier notifier){
		if(contains==0) {
			notifier.addListener(listener2);
			contains++;
		}
		notifier.fireTestRunStarted(getDescription());
		super.run(notifier);
	}
}