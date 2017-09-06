package bt.siemens.jthing.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resource {
	
	protected  List<Resource> mChildren = new ArrayList<Resource>();
	

	private URI mUri;
	
	@SerializedName("name")
	@Expose(serialize = true, deserialize=true)
	public String name;
	
	
	private  Object mSubsystemContext;
	
	public Resource(URI uri)
    {
        this.mUri = uri;
    }

    public Object read()
    {
        return null;
    }

    public boolean canWrite() {
    	return true;
    }
    
    public void write(Object val)
    {    	
    }

    public URI getUri() { return mUri; }

    public Resource ResolveUrl(URI url)
    {
        if (url == mUri)
            return this;
        else
            for(Resource r : mChildren){
            	if(r.getUri() == url)
            		return r;
            }
        return null;
    }

    public Object getSubsystemContext() { return mSubsystemContext; }
    
    public void setSubsystemContext(Object context){ mSubsystemContext = context;}

    
    public String getValueType() { return ""; }

    public String getName() { return name;}
    
    public void setName(String str) { name = str;}

}
