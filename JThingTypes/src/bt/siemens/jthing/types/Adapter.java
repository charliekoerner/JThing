package bt.siemens.jthing.types;

import java.net.URI;

public interface Adapter {

	public default void initialize(URI baseUri){
		
	}
	
	public default void addClient(AdapterClient client){
		
	}
	
	public Object read(Resource resource) throws Exception;
	
	public Object write(Resource resource, Object value) throws Exception;
	
	public boolean getStatus();
}
