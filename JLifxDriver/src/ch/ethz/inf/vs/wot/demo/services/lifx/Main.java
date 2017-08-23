package ch.ethz.inf.vs.wot.demo.services.lifx;

import java.io.IOException;

public class Main {
	
	public static final String LIFX_BULB_MAC = "d0:73:d5:00:ef:88";
	public static final String WIFI_BROADCAST_ADDRESS = "192.168.0.255";

	public static void main(String[] args) {
		LIFXBulb bulb = new LIFXBulb(LIFX_BULB_MAC, WIFI_BROADCAST_ADDRESS);
		bulb.setPower(true, 5);

	}

}
