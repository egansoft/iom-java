import java.util.LinkedList;
import java.util.Queue;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.*;


public class Updater {
	private String name;
	private MouseMover mouseMover;
	private Queue<DataPoint> queue;
	private Thread t;
	
	public Updater(String name, MouseMover mouseMover) {
		this.name = name; // -Jq3CLpsjbANW3lzY7ui
		this.mouseMover = mouseMover; 
	}
	
	public void start() {
		Firebase ref = new Firebase("https://internet-of-mice.firebaseio.com/devices/" + name + "/accelData");
		queue = new LinkedList<DataPoint>();
		
		ref.addValueEventListener(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		        System.out.println(snapshot.getValue());
//		        String jsonData = (String) snapshot.getValue();
		        for(DataSnapshot dataSet : snapshot.getChildren()) {
		        	DataSnapshot accelData = dataSet.child("dataPoints");
		        	
		        	for(DataSnapshot pointSnapshot : accelData.getChildren()) {
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
		    }
		    
		    @Override
		    public void onCancelled(FirebaseError firebaseError) {
		        System.out.println("The read failed: " + firebaseError.getMessage());
		    }
		});
		
		t = new Thread(new DequeueThread());
		t.start();
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
					last = current.t;
					init = true;
				} else {
					System.out.println(current.t - last);
					sleep((current.t - last)/10);
				}
				
				mouseMover.moveMouse((int)(current.x * 100), (int)(current.y * 100));
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
