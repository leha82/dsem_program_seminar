package maze.challengemode;

public class TimeThread extends Thread{
	private long limitTime;
	private boolean playing;
	private long elapsedTime;
	
	public TimeThread() {
		this(0);
	}
	
	public TimeThread(long limitTime) {
		this.limitTime=limitTime;
		this.playing = true;
	}

	@Override
	public void run() {
		try {
			long startTime = System.currentTimeMillis();
//			System.out.println("starttime : " + startTime);
			while (playing) {
				this.elapsedTime = System.currentTimeMillis() - startTime;
				if (this.elapsedTime >= this.limitTime) {
					playing=false;
				}
//				System.out.println("elapsedtime : " + elapsedTime);
			}	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void finish() {
		this.playing = false;
	}
	
	public long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(long limitTime) {
		this.limitTime = limitTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

}
