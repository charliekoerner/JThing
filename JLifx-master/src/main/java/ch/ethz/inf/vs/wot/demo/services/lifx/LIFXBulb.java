package ch.ethz.inf.vs.wot.demo.services.lifx;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import jlifx.bulb.GatewayBulb;
import jlifx.packet.Packet;

public class LIFXBulb {

    private byte [] address;
    private InetAddress ipBroadcast;
    private String label;
    private int temperature = 3500;
    private boolean isGateway = false;

    public LIFXBulb(String macAddress, String network) {
        label = macAddress;
        try {
			ipBroadcast = InetAddress.getByName(network);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        // Convert to big-endian address
        String[] macAddressParts = macAddress.split(":");
        byte[] bigEndianAddress = new byte[8];
        Short byteComponent;
        for(int i=0; i<6; i++) {
            byteComponent = Short.parseShort(macAddressParts[i], 16);
            bigEndianAddress[i] = byteComponent.byteValue();
        }
        bigEndianAddress[6] = (byte) 0x00;
        bigEndianAddress[7] = (byte) 0x00;
        address = bigEndianAddress;

//        // Convert to little-endian address
//        ByteBuffer bigEndianAddressBuffer = ByteBuffer.wrap(bigEndianAddress);
//        ByteBuffer littleEndianAddressBuffer = ByteBuffer.allocate(bigEndianAddress.length);
//        littleEndianAddressBuffer.order( ByteOrder.LITTLE_ENDIAN);
//        int element;
//        while (bigEndianAddressBuffer.hasRemaining()) {
//            element = bigEndianAddressBuffer.getInt();
//            littleEndianAddressBuffer.putInt(element);
//        }
//        address = littleEndianAddressBuffer.array();
    }
    
    public LIFXBulb(String macAddress, String network, boolean isGateway) {
    	this(macAddress, network);
    	this.isGateway = isGateway;
    }

    public String toString () { return label; }

    public String getLabel () { return label; }
    
    public void setColor(Color color) {
    	float[] hsv = new float[3];
    	Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
    	setColor(hsv[0], hsv[1], hsv[2], temperature, 0);
    }

    public void setColor(float hue, float saturation, float brightness, int kelvin, int delay) {
        try {
            byte [] messageData = new LIFXSetColorRequest(address, delay, hue, saturation, brightness, kelvin).generatePacket();
            StringBuilder sb = new StringBuilder();
            for (byte byteValue : messageData) {
                sb.append(String.format("%02x ", byteValue));
            }
            String message = sb.toString();
            send(new Message(messageData, ipBroadcast, 56700), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPower(boolean power, int delay) {
        try {
            byte [] messageData = new LIFXSetPowerRequest(address, delay, power).generatePacket();
            StringBuilder sb = new StringBuilder();
            for (byte byteValue : messageData) {
                sb.append(String.format("%02x ", byteValue));
            }
            String message = sb.toString();
            send(new Message(messageData, ipBroadcast, 56700), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPower() {
        try {
            byte [] messageData = new LIFXGetPowerRequest(address, 0).generatePacket();
            StringBuilder sb = new StringBuilder();
            for (byte byteValue : messageData) {
                sb.append(String.format("%02x ", byteValue));
            }
            String message = sb.toString();
            send(new Message(messageData, ipBroadcast, 56700), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(Message message, boolean receive) {
    	
    	final Message toSend = message;
    	
    	new Thread(new Runnable() {
			public void run() {
				DatagramSocket dataGramSocket = null;	    	
    	        try {
    	            dataGramSocket = new DatagramSocket(58000);    	            
    	            dataGramSocket.setBroadcast(true);
    	            dataGramSocket.setReuseAddress(true);
    	            
    	            DatagramPacket udpPacket = new DatagramPacket(toSend.getMessageData(), toSend.getMessageData().length, toSend.getIpAddress(), toSend.getPort());
    	            if (!dataGramSocket.isClosed()) { 
    	            	dataGramSocket.send(udpPacket);
    	            	byte[] byteArray = new byte[38];
    	            	DatagramPacket answer = new DatagramPacket(byteArray, byteArray.length);
    	                try
    	                {
    	                	dataGramSocket.setSoTimeout(500);
    	                	dataGramSocket.receive(answer);
    	                    System.out.println("Got reply");
    	                } catch (SocketTimeoutException e) {
    	                } 	                	
    	            }
    	        }
    	        catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	        finally {
    	            dataGramSocket.close();
    	        }
			}
		}).start();
	}
    
    private static boolean waitForReply(DatagramSocket socket) throws IOException {
        socket.setSoTimeout(500);
        int retries = 3;
        byte[] byteArray = new byte[38];
        while (retries-- > 0) {
            DatagramPacket answer = new DatagramPacket(byteArray, byteArray.length);
            try
            {
                socket.receive(answer);
                System.out.println("Got reply");
                return true;
            } catch (SocketTimeoutException e) {
                retries--;
                continue;
            }
        }
        return false;
    }
}
