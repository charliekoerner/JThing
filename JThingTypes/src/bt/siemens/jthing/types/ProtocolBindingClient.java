package bt.siemens.jthing.types;

import java.net.URI;

public interface ProtocolBindingClient {

	public Object read(URI uri) throws Exception;
	
	public Object write(URI uri, Object value) throws Exception;
}
