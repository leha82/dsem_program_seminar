package maze;

public class LogRank {
	String mouse;
	String timestamp;
	int count;

	public LogRank(String mouse, String timestamp, int count) {
		super();
		this.mouse = mouse;
		this.timestamp = timestamp;
		this.count = count;
	}

	public String getMouse() {
		return mouse;
	}

	public void setMouse(String mouse) {
		this.mouse = mouse;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
