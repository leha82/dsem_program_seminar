package maze.challengemode;

public class LogRank {
	int id;
	String mouse;
	String mapname;
	String timestamp;
	int search_count;
	int search_time;
	int search_moves;
	int record_time;
	int moves;

	public LogRank(int id,String mouse, String mapname, String timestamp, int search_count, int search_time, int search_moves, int record_time, int moves) {
		super();
		this.id = id;
		this.mouse = mouse;
		this.mapname = mapname;
		this.timestamp = timestamp;
		this.search_count = search_count;
		this.search_time = search_time;
		this.search_moves = search_moves;
		this.record_time = record_time;
		this.moves = moves;
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

	public String getMapname() {
		return mapname;
	}

	public void setMapname(String mapname) {
		this.mapname = mapname;
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
	
	public int getSearch_count() {
		return search_count;
	}

	public void setSearch_count(int search_count) {
		this.search_count = search_count;
	}

	public int getSearch_time() {
		return search_time;
	}

	public void setSearch_time(int search_time) {
		this.search_time = search_time;
	}

	public int getSearch_moves() {
		return search_moves;
	}

	public void setSearch_moves(int search_moves) {
		this.search_moves = search_moves;
	}

	public int getRecord_time() {
		return record_time;
	}

	public void setRecord_time(int record_time) {
		this.record_time = record_time;
	}
	
	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

}
