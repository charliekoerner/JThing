package bt.siemens.jthing.lifxadapter;

import java.net.URI;

import bt.siemens.jthing.types.Property;
import bt.siemens.jthing.types.Thing;

class LIFX_WIFI_LIGHT extends Thing
{
	public LIFX_WIFI_LIGHT(URI uri)
	{
		super(uri);

		color = new Property<String>(uri, "color", "Color");
		color.setWritable(true);
		addProperty(color);

		saturation = new Property<Long>(uri, "saturation", "Saturation");
		saturation.setWritable(true);
		saturation.setMin((long) 0);
		saturation.setMax((long) 100);
		saturation.setUnits("%");
		addProperty(saturation);

		brightness = new Property<Long>(uri, "brightness", "Brightness");
		brightness.setWritable(true);
		brightness.setMin((long) 0);
		brightness.setMax((long) 100);
		brightness.setUnits("%");
		addProperty(brightness);

		kelvin = new Property<Long>(uri, "kelvin", "Kelvin");
		kelvin.setWritable(true);
		kelvin.setMin((long) 2500);
		kelvin.setMax((long) 9000);
		kelvin.setUnits("°");
		addProperty(kelvin);

		power = new Property<Boolean>(uri, "power", "Power");
		power.setWritable(true);
		addProperty(power);

		label = new Property<String>(uri, "label", "Label");
		label.setWritable(false);
		addProperty(label);

		infrared = new Property<Long>(uri, "infrared", "Infrared");
		infrared.setWritable(true);
		infrared.setMin((long) 0);
		infrared.setMax((long) 100);
		infrared.setUnits("%");
		addProperty(infrared);

	}

	Property<String> color;

	Property<Long> saturation;

	Property<Long> brightness;

	Property<Long> kelvin;

	Property<Boolean> power;

	Property<String> label;

	Property<Long> infrared;

}
