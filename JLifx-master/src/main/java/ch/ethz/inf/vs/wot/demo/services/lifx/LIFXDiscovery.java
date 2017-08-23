package ch.ethz.inf.vs.wot.demo.services.lifx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jlifx.bulb.DiscoveryService;
import jlifx.bulb.GatewayBulb;
import jlifx.bulb.IBulb;

public class LIFXDiscovery {

	public static List<LIFXBulb> findBulbs(String network){
		List<LIFXBulb> bulbs = new ArrayList<LIFXBulb>();
        try {
			GatewayBulb gatewayBulb = DiscoveryService.discoverGatewayBulb();
			if (gatewayBulb == null) {

			} else {
			    Collection<IBulb> allBulbs = DiscoveryService.discoverAllBulbs(gatewayBulb);
			    for (IBulb bulb : allBulbs) {
					LIFXBulb lb = new LIFXBulb(bulb.getMacAddressAsString(), network);
					bulbs.add(lb);
					//lb.setPower(false, 5);
					//lb.setColor(Color.red);
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bulbs;
	}
}
