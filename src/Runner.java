import java.awt.AWTException;

import javax.swing.JOptionPane;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class Runner {

	public static void main(String[] args) throws Exception {
		String name = JOptionPane.showInputDialog("Enter device name");

		MouseMover mm = new MouseMover();
		Updater updater = new Updater(name, mm);
		ControlWindow cw = new ControlWindow(updater);
		
		
		
//		Demo:
//		MouseMover mm = new MouseMover();
//		for(int i=0;i<500;i++)
//			mm.moveMouse(1, 1);
	}

}
