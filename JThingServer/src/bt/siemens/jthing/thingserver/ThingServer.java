package bt.siemens.jthing.thingserver;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import bt.siemens.jthing.types.Adapter;
import bt.siemens.jthing.types.AdapterClient;
import bt.siemens.jthing.types.HypermediaLink;
import bt.siemens.jthing.types.HypermediaLinks;
import bt.siemens.jthing.types.InvalidResourceURLException;
import bt.siemens.jthing.types.ProtocolBinding;
import bt.siemens.jthing.types.ProtocolBindingClient;
import bt.siemens.jthing.types.Resource;
import bt.siemens.jthing.types.Thing;

public class ThingServer implements ProtocolBindingClient, AdapterClient {
	
	private List<Thing> mThings = new ArrayList<Thing>();
	private Adapter mAdapter;
	
	public ThingServer(ProtocolBinding binding, Adapter adapter){
		
		binding.addClient(this);
		adapter.addClient(this);
		mAdapter = adapter;
	}
	
	public void start(){}

	@Override
	public Object read(URI uri) throws Exception {
		if(uri.equals(URI.create("/"))){
			ArrayList<HypermediaLink> thingUris = new ArrayList<HypermediaLink>();
			for(Thing t: mThings)
				thingUris.add(new HypermediaLink(t.getUri().toString(), HypermediaLink.REL_TYPE_THING_DESCRIPTION));
			HypermediaLinks links = new HypermediaLinks();
			links.links = thingUris;
			return links;
		}else if(uri.equals(URI.create("/Status"))) {
			return mAdapter.getStatus();		}
			
		Resource r = resolveUri(uri);
		if(r == null) {
			throw new InvalidResourceURLException();
		}

		return mAdapter.read(r);
	}

	@Override
	public Object write(URI uri, Object value) throws Exception {
		Resource r = resolveUri(uri);
		
		if(r == null) {
			throw new InvalidResourceURLException();
		}
		
		if(!r.canWrite())
			throw new UnsupportedOperationException();
		
		return mAdapter.write(r, value);
	}

	@Override
	public void addThing(Thing thing) {
		mThings.add(thing);
	}
	
	private Resource resolveUri(URI uri){
		for(Thing t : mThings){
			if(t.getUri().equals(uri))
				return t;
			for(Resource p : t.getProperties()){
				if(p.getUri().equals(uri))
					return p;
			}
		}
		return null;
	}

}
