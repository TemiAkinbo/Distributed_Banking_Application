package server;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Session extends TimerTask implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int timeAlive;
	private Timer timer;
	private boolean active;
	private long sessionID;
	private static final int MAX_SESSION_LENGTH = 5*60;
	private static final long DELAY = 1000;
	
	public Session() {
		this.sessionID = (long) (Math.random() * 9000000 + 1) ;
		this.timer = new Timer();
		this.active = true;
		
		this.timer.scheduleAtFixedRate(this, 
				new Date(System.currentTimeMillis()), DELAY);
	}

	@Override
	public void run() {		
		this.timeAlive++;
		
		if(timeAlive == MAX_SESSION_LENGTH) {
			this.active = false;
			this.timeAlive = 0;
		}
				
	}
	
	
	public long getSessionID() {
		return this.sessionID;
	}
	
	public boolean getSessionActive() {
		return this.active;
	}
	
	
	
	

}
