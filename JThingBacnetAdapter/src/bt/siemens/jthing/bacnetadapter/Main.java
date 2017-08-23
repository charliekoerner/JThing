package bt.siemens.jthing.bacnetadapter;

import java.io.IOException;
import java.net.URI;

import bt.siemens.jthing.httpbinding.HTTPServer;
import bt.siemens.jthing.thingserver.ThingServer;
import bt.siemens.jthing.types.Adapter;
import bt.siemens.jthing.types.ProtocolBinding;

public class Main {

	public static void main(String[] args) {

		URI baseUri = URI.create("http://192.168.0.114:8080/");
		
		ProtocolBinding binding = new HTTPServer("192.168.0.114", 8080);
		
		Adapter adapter = new BacnetAdapter();
		
		ThingServer server = new ThingServer(binding, adapter);
		
		adapter.initialize(baseUri);
		
		server.start();
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
