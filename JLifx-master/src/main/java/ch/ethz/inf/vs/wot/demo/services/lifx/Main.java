package ch.ethz.inf.vs.wot.demo.services.lifx;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;

import jlifx.bulb.DiscoveryService;
import jlifx.bulb.GatewayBulb;
import jlifx.bulb.IBulb;

public class Main {
	
	public static final String LIFX_BULB_MAC = "d0:73:d5:00:ef:88";
	public static final String WIFI_BROADCAST_ADDRESS = "192.168.0.255";

	public static void main(String[] args) {
        try {
			GatewayBulb gatewayBulb = DiscoveryService.discoverGatewayBulb();
			if (gatewayBulb == null) {

			} else {
			    Collection<IBulb> allBulbs = DiscoveryService.discoverAllBulbs(gatewayBulb);
			    for (IBulb bulb : allBulbs) {
					LIFXBulb lb = new LIFXBulb(bulb.getMacAddressAsString(), WIFI_BROADCAST_ADDRESS);
					lb.setPower(true, 5);
					//lb.setColor(Color.red);
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
