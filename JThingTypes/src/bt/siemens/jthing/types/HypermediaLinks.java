package bt.siemens.jthing.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HypermediaLinks
{
	@SerializedName("links")
	@Expose(serialize = true, deserialize=true)
    public Object links;
}
