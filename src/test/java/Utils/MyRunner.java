package Utils;

import TestRailAPI.TRClient;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.model.TestOutcome;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import java.util.List;

public class MyRunner extends SerenityRunner {

	private static TRExecutionListener listener2 = new TRExecutionListener();
	private static Integer contains = 0;
	private static TRReportService reportService;

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

	@Override protected void generateReports(){
		generateReportsFor(getTestOutcomes());
	}

	private TRReportService getReportService() {
		if (reportService == null) {
			reportService = new TRReportService(getOutputDirectory(), getDefaultReporters());
		}
		return reportService;
	}

	private void generateReportsFor(final List<TestOutcome> testOutcomeResults) {
		getReportService().generateReportsFor(testOutcomeResults);
		getReportService().generateConfigurationsReport();
		TRClient.ParseSerenityResults(testOutcomeResults);
	}

}
