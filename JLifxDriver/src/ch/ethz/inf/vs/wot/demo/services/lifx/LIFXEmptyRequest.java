package ch.ethz.inf.vs.wot.demo.services.lifx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by wilhelmk on 01/10/15.
 */
class LIFXEmptyRequest extends LIFXRequest {

	public LIFXEmptyRequest(byte[] address, int delay) {
		super(address, delay);
		// TODO Auto-generated constructor stub
	}

	@Override
	int getRequestType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	byte[] generatePayload() {
		// TODO Auto-generated method stub
		return new byte[] {};
	}


}
