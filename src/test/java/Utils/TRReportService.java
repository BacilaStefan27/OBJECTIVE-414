package Utils;

import net.thucydides.core.reports.AcceptanceTestFullReporter;
import net.thucydides.core.reports.AcceptanceTestReporter;
import net.thucydides.core.reports.ReportService;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.Configuration;

import java.io.File;
import java.util.Collection;

public class TRReportService extends ReportService {
	public TRReportService(Configuration configuration) {
		super(configuration);
	}

	public TRReportService(File outputDirectory, Collection<AcceptanceTestReporter> subscribedReporters) {
		super(outputDirectory, subscribedReporters);
	}

	public TRReportService(File outputDirectory, Collection<AcceptanceTestReporter> subscribedReporters, EnvironmentVariables environmentVariables) {
		super(outputDirectory, subscribedReporters, environmentVariables);
	}

	public TRReportService(File outputDirectory, Collection<AcceptanceTestReporter> subscribedReporters, Collection<AcceptanceTestFullReporter> subscribedFullReporters, EnvironmentVariables environmentVariables) {
		super(outputDirectory, subscribedReporters, subscribedFullReporters, environmentVariables);
	}
}
