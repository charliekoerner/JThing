package bt.siemens.jthing.bacnetadapter;

import java.net.URI;

import bt.siemens.jthing.types.Property;
import bt.siemens.jthing.types.Thing;

public class GMS_OM_BACNET_AI extends Thing{

	public GMS_OM_BACNET_AI(URI uri) {
		super(uri);
		present_value = new Property<Float>(uri, "present_value", "Present Value");
		addProperty(present_value);
	}
	Property<Float> present_value;
}
