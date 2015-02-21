package com.example.nfdproducerconsumer;

import java.io.IOException;

import net.named_data.jndn.Data;
import net.named_data.jndn.Face;
import net.named_data.jndn.Interest;
import net.named_data.jndn.Name;
import net.named_data.jndn.OnData;
import net.named_data.jndn.OnTimeout;
import net.named_data.jndn.encoding.EncodingException;

public class Consumer implements OnData, OnTimeout {
	private static final String CONSUMER_TAG = "[Consumer]";
	
	private Face mFace;
	private Name mName;
	
	private ConsumerThread mConsumerThread;
	
	public Consumer () {
		mFace = new Face("localhost");
	
		String producerName = "/producer/" + Math.floor(Math.random() * 100000);
		mName = new Name(producerName);
		
		mConsumerThread = new ConsumerThread();
		mConsumerThread.start();
		
		G.Log(CONSUMER_TAG, "Consumer() Completed.");
	}

	@Override
	public void onTimeout(Interest interest) {
		G.Log(CONSUMER_TAG, "Consumer::onTimeout(): " + interest.toString());
	}

	@Override
	public void onData(Interest interest, Data data) {
		G.Log(CONSUMER_TAG, "Consumer::onData(): " + interest.toString());
	}
	
	private class ConsumerThread extends Thread {
		public void run() {
			G.Log(CONSUMER_TAG, "ConsumerThread started.");
			
			try {
				// Consumer to express Interest in the same thread a processEvents()
				mFace.expressInterest(mName, Consumer.this, Consumer.this);
				
				while (true) {
					mFace.processEvents();
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (EncodingException e) {
				e.printStackTrace();
			} 
			
			G.Log(CONSUMER_TAG, "ConsumerThread::run() completed!");
		}
	}
}
