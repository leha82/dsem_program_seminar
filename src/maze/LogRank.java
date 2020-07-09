package maze;

public class LogRank {
	int id;
	String mouse;
	String timestamp;
	String mapname;
	int count;

	public LogRank(int id,String mouse, String mapname, String timestamp, int count) {
		super();
		this.id = id;
		this.mouse = mouse;
		this.mapname = mapname;
		this.timestamp = timestamp;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String getMapname() {
		return mapname;
	}

	public void setMapname(String mapname) {
		this.mapname = mapname;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
