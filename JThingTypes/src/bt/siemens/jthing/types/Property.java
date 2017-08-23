package bt.siemens.jthing.types;

import java.net.URI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Property<T> extends Resource {

	private T mValue;	
	private Thing mParent;
	
	@SerializedName("units")
	@Expose(serialize = true, deserialize=true)
	private String mUnits;
	
	@SerializedName("min")
	@Expose(serialize = true, deserialize=true)
	private T mMin;
	
	@SerializedName("max")
	@Expose(serialize = true, deserialize=true)
	private T mMax;
	
	@SerializedName("writable")
	@Expose(serialize = true, deserialize=true)
	private boolean mWritable;
	
	@SerializedName("href")
	@Expose(serialize = true, deserialize=true)
	public URI uri;
	
	public String f1;
	
	public Property(URI baseUri, String relativePath, String name) {
		super(URI.create(baseUri.getPath() + "/" + relativePath));
		mName = name;
		this.uri = super.getUri();
	}

	public T getValue(){return mValue;}
	
	public void setValue(T val){mValue = val;}
	
	public void setParent(Thing thing){mParent = thing;}
	
	public Thing getParent(){return mParent;}
	
	@Override
    public boolean canWrite() {
    	return getWritable();
    }

	public String getUnits() {
		return mUnits;
	}

	public void setUnits(String mUnits) {
		this.mUnits = mUnits;
	}

	public T getMin() {
		return mMin;
	}

	public void setMin(T mMin) {
		this.mMin = mMin;
	}

	public T getMax() {
		return mMax;
	}

	public void setMax(T mMax) {
		this.mMax = mMax;
	}

	public boolean getWritable() {
		return mWritable;
	}

	public void setWritable(boolean mWritable) {
		this.mWritable = mWritable;
	}
	
}
