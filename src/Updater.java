import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import org.json.*;


public class Updater {
	private String name;
	private MouseMover mouseMover;
	
	public Updater(String name, MouseMover mouseMover) {
		this.name = name; // -Jq3CLpsjbANW3lzY7ui
		this.mouseMover = mouseMover; 
	}
	
	public void start() {
		Firebase ref = new Firebase("https://internet-of-mice.firebaseio.com/devices/" + name + "/accelData");
		
		ref.addValueEventListener(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		        System.out.println(snapshot.getValue());
		    }
		    
		    @Override
		    public void onCancelled(FirebaseError firebaseError) {
		        System.out.println("The read failed: " + firebaseError.getMessage());
		    }
		});
	}
}
