package bt.siemens.jthing.httpbinding;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import bt.siemens.jthing.types.InvalidResourceURLException;
import bt.siemens.jthing.types.ProtocolBinding;
import bt.siemens.jthing.types.ProtocolBindingClient;
import bt.siemens.jthing.types.Thing;
import fi.iki.elonen.NanoHTTPD;

public class HTTPServer extends NanoHTTPD implements ProtocolBinding{

	private ProtocolBindingClient mClient;
	private Gson gson; 
	
	 public HTTPServer(String hostname, int port) {
         super(hostname, port);
         try {
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         System.out.println("\nRunning! Point your browsers to " + hostname + ":"+  port);
         gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
     }
 

	 private class ValueObject{
		 ValueObject(Object v){
			 value = v;
		 }
		 @Expose
		 public Object value;
	 }
	 
	 private static String readStream(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
	 }
	 
     @Override
     public Response serve(IHTTPSession session) {    	 
    	 if(session.getMethod() == Method.GET){
    		 Object response;
			try {
				response = mClient.read(URI.create(session.getUri()));
			} catch (Exception e) {
				response = e;
			}
    		 
    		 if(response instanceof Number || response instanceof Boolean || response instanceof String){
    			 ValueObject valObj = new ValueObject(response);
        		 String json = gson.toJson(valObj);    		 
        		 Response res = new Response(json);
        		 
        		 res.addHeader( "Content-Type", "application/json");
        		 return res;
    		 }
    		 else if(response instanceof InvalidResourceURLException){
    			 String json = gson.toJson(response);
    			 Response errorResponse = new Response(json);
    			 errorResponse.setStatus(Response.Status.NOT_FOUND);
    			 return errorResponse;
    		 }    		 
    		 else if(response instanceof Exception){
    			 String json = gson.toJson(response);
    			 Response errorResponse = new Response(json);
    			 errorResponse.addHeader("Content-Type", "application/json");
    			 errorResponse.setStatus(Response.Status.INTERNAL_ERROR);
    			 return errorResponse;
    		 }
    		 else{
        		 String json = gson.toJson(response);        		 
        		 Response res = new Response(json);
        		 res.addHeader("Content-Type", "application/json");
        		 return res;
    		 }		 
    	 }
    	 else if(session.getMethod() == Method.PUT){
			try {
		        Integer contentLength = Integer.parseInt(session.getHeaders().get("content-length"));
		        byte[] buffer = new byte[contentLength];
		        session.getInputStream().read(buffer, 0, contentLength);
		        String json = new String(buffer);
	    		ValueObject valObj = (ValueObject)gson.fromJson(json,ValueObject.class);
	    		Object response = mClient.write(URI.create(session.getUri()), valObj.value);
	    		return new Response(gson.toJson(response));
			} catch (UnsupportedOperationException e) {
				String json = gson.toJson(e);
   			 	Response errorResponse = new Response(json);
   			 	errorResponse.setStatus(Response.Status.METHOD_NOT_ALLOWED);
   				return errorResponse;
			}catch (InvalidResourceURLException e) {
				String json = gson.toJson(e);
   			 	Response errorResponse = new Response(json);
   			 	errorResponse.setStatus(Response.Status.NOT_FOUND);
   				return errorResponse;
			}catch (Exception e) {
				String json = gson.toJson(e);
   			 	Response errorResponse = new Response(json);
   			 	errorResponse.setStatus(Response.Status.INTERNAL_ERROR);
   				return errorResponse;
			}
    	 }
	 	Response errorResponse = new Response("Unsupported method. Can only read or update resources!");
	 	errorResponse.setStatus(Response.Status.BAD_REQUEST);
		return errorResponse;
     }


	@Override
	public void addClient(ProtocolBindingClient client) {
		mClient = client;		
	}
}