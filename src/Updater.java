import java.util.LinkedList;
import java.util.Queue;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class Updater {
	private String name;
	private MouseMover mouseMover;
	private Queue<DataPoint> queue;
	private Thread t;
	private Firebase ref;
	private ValueEventListener listen;
	
	public Updater(String name, MouseMover mouseMover) {
		this.name = name; // -Jq4ScE9IWmYJSeGkU56
		this.mouseMover = mouseMover; 
	}
	
	public void start() {
		ref = new Firebase("https://internet-of-mice.firebaseio.com/devices/" + name + "/accelData");
		queue = new LinkedList<DataPoint>();
		
		// Clear out preexisting data
		ref.removeValue();
		
		listen = new ValueEventListener() {
			
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		        System.out.println(snapshot.getValue());
		        
		        // Queue data
		        for(DataSnapshot dataSet : snapshot.getChildren()) {
//		        	DataSnapshot accelData = dataSet.child("dataPoints");
		        	
		        	for(DataSnapshot pointSnapshot : dataSet.getChildren()) {
			        	System.out.println("well you got this far");
		        		double x = (Double) pointSnapshot.child("x").getValue();
		        		double y = (Double) pointSnapshot.child("y").getValue();
		        		double z = (Double) pointSnapshot.child("z").getValue();
		        		int t = (int) (((Long) pointSnapshot.child("t").getValue())).longValue();
		        		
		        		DataPoint dataPoint = new DataPoint(x,y,z,t);
		        		queue.add(dataPoint);
		        		System.out.println(t);
		        	}
		        }
		        
		        // Remove loaded data from firebase
		        ref.removeValue();
		    }
		    
		    @Override
		    public void onCancelled(FirebaseError firebaseError) {
		        System.out.println("The read failed: " + firebaseError.getMessage());
		    }
		};
		
		ref.addValueEventListener(listen);
		
		t = new Thread(new DequeueThread());
		t.start();
	}
	
	public void stop() {
		queue = null;
		t.interrupt();
		ref.removeEventListener(listen);
		queue = null;
	}
	
	private class DequeueThread implements Runnable {
		public void run() {
			boolean init = false;
			int last = 0;
			
			while(true) {
				if(queue.isEmpty()){
					sleep(100);
					continue;
				}
				
				DataPoint current = queue.remove();
				
				if(!init) {
					init = true;
				} else {
					System.out.println(current.t - last);
//					sleep((current.t - last)/10);
				}

				last = current.t;
				mouseMover.smoothMove(-((int)(current.x*10)), (int)(current.y*10));
			}
		}
		
		private void sleep(long t){
			try {
				Thread.sleep(t);
			} catch (InterruptedException e) {}
		}
	}
	
	private class DataPoint {
		public double x;
		public double y;
		public double z;
		public int t;
		
		public DataPoint(double x, double y, double z, int t) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.t = t;
		}
	}
}
