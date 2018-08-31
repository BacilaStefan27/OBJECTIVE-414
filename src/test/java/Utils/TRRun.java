package Utils;

public class TRRun {

	public String getRunID() {
		return this.RunID;
	}

	public void setRunID(String runID) {
		this.RunID = runID;
	}

	private String RunID;

	public TRRun(String runID) {
		setRunID(runID);
	}
}
