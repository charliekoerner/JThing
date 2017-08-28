package bt.siemens.jthing.lifxadapter;

import java.net.URI;

import bt.siemens.jthing.types.Property;
import bt.siemens.jthing.types.Thing;

public class GMS_OM_LIFX extends Thing {

	public GMS_OM_LIFX(URI uri) {
		super(uri);
		brightness = new Property<Short>(uri, "brightness", "Brightness") ;
		brightness.setWritable(true);
		brightness.setMin((short) 0);
		brightness.setMax((short) 65535);
		brightness.setUnits("%");
		addProperty(brightness);
		
		power = new Property<Short>(uri, "power", "Power");
		power.setWritable(true);
		power.setMin((short) 0);
		power.setMax((short) 100);
		power.setUnits("%");
		addProperty(power);
	}

	Property<Short> brightness;
	
	Property<Short> power;
}



