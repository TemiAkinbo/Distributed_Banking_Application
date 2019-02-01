package server;

import java.io.Serializable;
import java.util.Timer;

public class Session implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int activeTime;
	private Timer timer;
	private boolean active;
	private long sessionID;
	
	
	
	

}
