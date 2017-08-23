package bt.siemens.jthing.bacnetadapter;

import java.net.URI;

import bt.siemens.jthing.types.Adapter;
import bt.siemens.jthing.types.AdapterClient;
import bt.siemens.jthing.types.Resource;

public class BacnetAdapter implements Adapter {
	
	private AdapterClient mClient;
	@Override
	public  void initialize(URI baseUri){
		GMS_OM_BACNET_AI ai1 = new GMS_OM_BACNET_AI(URI.create("/ai1"));
		mClient.addThing(ai1);
	}
	
	@Override
	public void addClient(AdapterClient client){
		mClient = client;
	}
	@Override
	public Object read(Resource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object write(Resource resource, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getStatus() {
		return true;
	}

}
