package bt.siemens.jthing.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HypermediaLink {
	@SerializedName("href")
	@Expose
	private String href;
	
	@SerializedName("rel")
	@Expose
	private String rel;
	
	public String getHref(){return href;}
	public String getRel(){return rel;}
	
	public void setHref(String href){this.href = href;}
	
	public HypermediaLink(String href, String rel) {
		this.href = href;
		this.rel = rel;
		
	}
	
	public static String REL_TYPE_THING_DESCRIPTION = "td";  

}
