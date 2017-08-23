package bt.siemens.jthing.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thing extends Resource {

	@SerializedName("properties")
	@Expose
	public List<Resource> mProperties = new ArrayList<Resource>();
	
	@SerializedName("id")
	@Expose	
	public String id;
	
	@SerializedName("type")
	@Expose
	public String type;
	
	@SerializedName("om_name")
	@Expose
	public String om_name;
	
	@SerializedName("uri")
	@Expose(serialize = true, deserialize=true)
	public URI uri;
	
	public Thing(URI uri) {
		super(uri);
		this.uri = uri;
	}

	public List<Resource> getProperties() {
		return mProperties;
	}

	protected void setProperties(List<Resource> mProperties) {
		this.mProperties = mProperties;
	}	
	
	protected void addProperty(Property p){
		p.setParent(this);
		mProperties.add(p);
	}
	
}
