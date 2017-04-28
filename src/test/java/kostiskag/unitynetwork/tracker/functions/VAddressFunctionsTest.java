package kostiskag.unitynetwork.tracker.functions;

import static org.junit.Assert.*;

import org.junit.Test;

public class VAddressFunctionsTest {

	@Test
	public void test() {
		assertEquals(VAddressFunctions._10ipAddrToNumber("10.0.0.3"), 2);
		assertEquals(VAddressFunctions.numberTo10ipAddr(2), "10.0.0.3");
	}
}
