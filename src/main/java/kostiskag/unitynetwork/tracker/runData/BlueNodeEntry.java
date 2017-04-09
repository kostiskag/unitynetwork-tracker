package kostiskag.unitynetwork.tracker.runData;

import java.sql.Time;

/**
 *
 * @author kostis
 */
public class BlueNodeEntry {
    
    private final String name;
    private final String Phaddress;
    private final int port;
    private Time regTimestamp;
    public RedNodeTable rednodes;
    
    private Object timeLock = new Object();

    public BlueNodeEntry(String name, String phAddress, int port, Time regTimestamp) {
        this.name = name;
        this.Phaddress = phAddress;
        this.port = port;
        this.regTimestamp = regTimestamp;
        this.rednodes = new RedNodeTable(this);
    }
    
    //auto timestamp
    public BlueNodeEntry(String name, String phAddress, int port) {
        this.name = name;
        this.Phaddress = phAddress;
        this.port = port;
        this.regTimestamp = new Time(System.currentTimeMillis());
        this.rednodes = new RedNodeTable(this);
    }

    public String getName() {
        return name;
    }

    public String getPhaddress() {
        return Phaddress;
    }

    public int getPort() {
        return port;
    }
    
    public int getLoad() {
    	return rednodes.getSize();
    }
    
    public RedNodeTable getRedNodes() {
    	return rednodes;
    }

    public Time getTimestamp() {
    	synchronized (timeLock) {
    		return regTimestamp;
    	}
    }
    
    public void updateTimestamp(){
    	synchronized (timeLock) {
    		this.regTimestamp = new Time(System.currentTimeMillis());
    	}
    }    
}
