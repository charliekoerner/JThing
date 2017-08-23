package bt.siemens.jthing.lifxadapter;

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
				GMS_OM_LIFX lifx = new GMS_OM_LIFX(URI.create("/" + name));
				lifx.id = bulb.macAddress;
				lifx.om_name = "GMS_OM_LIFX";
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
			else if(name == "Power"){
				LifxWrapper.refreshBulbPower(bulb);
				return bulb.power;
			}else{
				return null;
			}				
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
