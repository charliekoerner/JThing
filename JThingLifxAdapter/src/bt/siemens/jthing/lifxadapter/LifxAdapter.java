package bt.siemens.jthing.lifxadapter;

import java.awt.Color;
import java.net.URI;
import java.util.Collection;

import org.ojothepojo.lifx.LifxBulb;
import org.ojothepojo.lifx.LifxWrapper;

import bt.siemens.jthing.types.Adapter;
import bt.siemens.jthing.types.AdapterClient;
import bt.siemens.jthing.types.Property;
import bt.siemens.jthing.types.Resource;
import bt.siemens.jthing.types.Thing;


public class LifxAdapter implements Adapter {
	
	AdapterClient mClient;
	
	@Override
	public  void initialize(URI baseUri){
		try{
			Collection<LifxBulb> bulbs = LifxWrapper.discoverBulbs(2000);		
			for(LifxBulb bulb : bulbs){
				String name = "lamp" + bulb.macAddress;
				LIFX_WIFI_LIGHT lifx = new LIFX_WIFI_LIGHT(URI.create("/" + name));
				lifx.id = bulb.macAddress;
				//lifx.om_name = "GMS_OM_LIFX";
				lifx.type = "ba:Lamp";
				lifx.setSubsystemContext(bulb);
				mClient.addThing(lifx);
			}
		}
		catch(Exception e){}
	}
	
	@Override
	public void addClient(AdapterClient client){
		mClient = client;
	}

	@Override
	public Object read(Resource resource) throws Exception {
		if(resource instanceof Thing)
			return resource;
		if(resource instanceof Property<?>){

			Property<?> p = (Property<?>)resource;
			String name = p.getName();
			Thing t = p.getParent();
			LifxBulb bulb = (LifxBulb)t.getSubsystemContext();
		
			if(name == "Brightness"){
				LifxWrapper.refreshBulbState(bulb);
				return bulb.brightness;
			}
			if(name == "Hue"){
				LifxWrapper.refreshBulbState(bulb);
				return bulb.hue;
			}
			if(name == "Saturation"){
				LifxWrapper.refreshBulbState(bulb);
				return bulb.saturation;
			}
			if(name == "Kelvin"){
				LifxWrapper.refreshBulbState(bulb);
				return bulb.kelvin;
			}
			if(name == "Color"){
				
				LifxWrapper.refreshBulbState(bulb);
				Color c = Color.getHSBColor(bulb.hue, bulb.saturation, bulb.brightness);
				String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
				return hex;
			}
			if(name == "Label"){
				return bulb.macAddress;
			}			
			if(name == "Power"){
				LifxWrapper.refreshBulbPower(bulb);
				return bulb.power;
			}
			
			return null;
				
		}
		throw new Exception("Unkown resource type");
	}

	@Override
	public Object write(Resource resource, Object value) throws Exception {
		if(resource instanceof Property<?>){

			Property<?> p = (Property<?>)resource;
			String name = p.getName();
			Thing t = p.getParent();
			LifxBulb bulb = (LifxBulb)t.getSubsystemContext();
			if(name == "Brightness"){
				Double d = (Double)value;
				Short f = d.shortValue();
				bulb.brightness = f;
				boolean res = LifxWrapper.updateBulbColor(bulb, 0);
				return res;
			}
			if(name == "Hue"){
				Double d = (Double)value;
				Short f = d.shortValue();
				bulb.hue = f;
				boolean res = LifxWrapper.updateBulbColor(bulb, 0);
				return res;
			}
			if(name == "Saturation"){
				Double d = (Double)value;
				Short f = d.shortValue();
				bulb.saturation = f;
				boolean res = LifxWrapper.updateBulbColor(bulb, 0);
				return res;
			}
			if(name == "Kelvin"){
				Double d = (Double)value;
				Short f = d.shortValue();
				bulb.kelvin = f;
				boolean res = LifxWrapper.updateBulbColor(bulb, 0);
				return res;
			}
			if(name == "Color"){
				String html = (String)value;
				html = html.replaceAll("#", "0x");
				Color c = Color.decode(html);
				float[] hsbvals = new float[3];
				Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbvals);
				bulb.hue = (short) (hsbvals[0] * 65535);
				bulb.saturation = (short) (hsbvals[1] * 65535);
				bulb.brightness = (short) (hsbvals[2] * 65535);
				boolean res = LifxWrapper.updateBulbColor(bulb, 0);
				return res;
			}			
			if(name == "Power"){
				Double d = (Double)value;
				Short f = d.shortValue();
				bulb.power = f;
				boolean res = LifxWrapper.updateBulbPower(bulb);
				return res;
			}

			return null;
				
		}
		throw new Exception("Unkown resource type");
	}

	@Override
	public boolean getStatus() {
		return true;
	}
}
