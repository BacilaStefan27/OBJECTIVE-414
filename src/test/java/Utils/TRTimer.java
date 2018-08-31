package Utils;

public class TRTimer {
	public long getTimer() {
		return (System.currentTimeMillis()-this.timer);
	}

	public void setTimer(Integer timer) {
		this.timer = System.currentTimeMillis();
	}

	private long timer;

	public TRTimer() {
		this.timer = 0;
	}
}
