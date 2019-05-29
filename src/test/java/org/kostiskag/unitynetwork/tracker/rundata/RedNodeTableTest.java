package org.kostiskag.unitynetwork.tracker.rundata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kostiskag.unitynetwork.tracker.App;
import org.kostiskag.unitynetwork.tracker.AppLogger;
import org.kostiskag.unitynetwork.tracker.functions.CryptoMethods;

public class RedNodeTableTest {

    @BeforeClass
    public static void init() {
        AppLogger.newInstance(null,null);
    }

	@Test
	public void initTest() {
		BlueNodeEntry bn = null;
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis2", "192.168.1.2");
			rns.lease("pakis3", "192.168.1.3");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
			return;
		}
		assertTrue(rns.getSize() == 3);
	}
	
	@Test
	public void uniqueHosnameTest() {
		PublicKey pub = CryptoMethods.generateRSAkeyPair().getPublic();
		BlueNodeEntry bn = new BlueNodeEntry("pakis", pub, "192.168.1.1", 33);
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis", "192.168.1.2");
		} catch (Exception e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}
	
	@Test
	public void uniqueAddressTest() {
		PublicKey pub = CryptoMethods.generateRSAkeyPair().getPublic();
		BlueNodeEntry bn = new BlueNodeEntry("pakis", pub, "192.168.1.1", 33);
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("makis", "192.168.1.1");
		} catch (Exception e) {
			assertEquals(rns.getSize(), 1);
			return;
		}
		assertTrue(false);
	}
	
	@Test
	public void getByHostnameTest() {
		BlueNodeEntry bn = null;
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis2", "192.168.1.2");
			rns.lease("pakis3", "192.168.1.3");
			rns.lease("pakis4", "192.168.1.4");
			rns.lease("pakis5", "192.168.1.5");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(rns.getRedNodeEntryByHn("pakis3").getHostname().equals("pakis3"));
		assertTrue(rns.getRedNodeEntryByHn("pakis3").getVaddress().equals("192.168.1.3"));
		assertEquals(rns.getRedNodeEntryByHn("pakis15"), null);
	}
	
	@Test
	public void getByVAddressTest() {
		BlueNodeEntry bn = null;
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis2", "192.168.1.2");
			rns.lease("pakis3", "192.168.1.3");
			rns.lease("pakis4", "192.168.1.4");
			rns.lease("pakis5", "192.168.1.5");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(rns.getRedNodeEntryByVAddr("192.168.1.2").getHostname().equals("pakis2"));
		assertTrue(rns.getRedNodeEntryByVAddr("192.168.1.2").getVaddress().equals("192.168.1.2"));
	}
	
	@Test
	public void getStringListTest() {
		BlueNodeEntry bn = null;
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis2", "192.168.1.2");
			rns.lease("pakis3", "192.168.1.3");
			rns.lease("pakis4", "192.168.1.4");
			rns.lease("pakis5", "192.168.1.5");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		List<String> list = rns.getLeasedRedNodeHostnameList();
		assertTrue(list.size() == 5);
		assertTrue(list.get(0).equals("pakis"));
		assertTrue(list.get(1).equals("pakis2"));
		assertTrue(list.get(2).equals("pakis3"));
		assertTrue(list.get(3).equals("pakis4"));
		assertTrue(list.get(4).equals("pakis5"));
	}
	
	@Test
	public void checkOnlineByHnTest() {
		BlueNodeEntry bn = null;
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis2", "192.168.1.2");
			rns.lease("pakis3", "192.168.1.3");
			rns.lease("pakis4", "192.168.1.4");
			rns.lease("pakis5", "192.168.1.5");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(rns.checkOnlineByHn("pakis"));
		assertTrue(rns.checkOnlineByHn("pakis3"));
		assertTrue(rns.checkOnlineByHn("pakis4"));
		assertTrue(rns.checkOnlineByHn("pakis2"));
		assertTrue(rns.checkOnlineByHn("pakis5"));
		assertTrue(!rns.checkOnlineByHn("bob"));
	}
	
	@Test
	public void checkOnlineByVaddressTest() {
		BlueNodeEntry bn = null;
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis2", "192.168.1.2");
			rns.lease("pakis3", "192.168.1.3");
			rns.lease("pakis4", "192.168.1.4");
			rns.lease("pakis5", "192.168.1.5");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(rns.checkOnlineByVaddress("192.168.1.3"));
		assertTrue(rns.checkOnlineByVaddress("192.168.1.1"));
		assertTrue(rns.checkOnlineByVaddress("192.168.1.2"));
		assertTrue(rns.checkOnlineByVaddress("192.168.1.5"));
		assertTrue(rns.checkOnlineByVaddress("192.168.1.4"));
		assertTrue(!rns.checkOnlineByVaddress("192.168.1.20"));
	}
	
	@Test
	public void releaseByHostnameTest() {
		BlueNodeEntry bn = null;
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis2", "192.168.1.2");
			rns.lease("pakis3", "192.168.1.3");
			rns.lease("pakis4", "192.168.1.4");
			rns.lease("pakis5", "192.168.1.5");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		rns.release("pakis");
		rns.release("pakis3");
		rns.release("pakis5");
		
		assertTrue(!rns.checkOnlineByVaddress("192.168.1.1"));
		assertTrue(!rns.checkOnlineByVaddress("192.168.1.3"));
		assertTrue(!rns.checkOnlineByVaddress("192.168.1.5"));
		assertTrue(rns.checkOnlineByVaddress("192.168.1.2"));
		assertTrue(rns.checkOnlineByVaddress("192.168.1.4"));
		assertEquals(rns.getSize(), 2);
	}
	
	@Test
	public void releaseByVaddressTest() {
		BlueNodeEntry bn = null;
		RedNodeTable rns = new RedNodeTable(bn);
		try {
			rns.lease("pakis", "192.168.1.1");
			rns.lease("pakis2", "192.168.1.2");
			rns.lease("pakis3", "192.168.1.3");
			rns.lease("pakis4", "192.168.1.4");
			rns.lease("pakis5", "192.168.1.5");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		rns.releaseByVAddress("192.168.1.2");
		rns.releaseByVAddress("192.168.1.4");
		
		assertTrue(!rns.checkOnlineByHn("pakis2"));
		assertTrue(!rns.checkOnlineByHn("pakis4"));
		assertTrue(rns.checkOnlineByHn("pakis3"));
		assertTrue(rns.checkOnlineByHn("pakis"));
		assertTrue(rns.checkOnlineByHn("pakis5"));
		assertEquals(rns.getSize(), 3);
	}

}
