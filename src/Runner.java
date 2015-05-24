import javax.swing.JOptionPane;


public class Runner {

	public static void main(String[] args) throws Exception {
		String name = JOptionPane.showInputDialog("Enter device name");

		MouseMover mm = new MouseMover();
		Updater updater = new Updater(name, mm);
		ControlWindow cw = new ControlWindow(updater);
		
		
		
//		Demo:
//		for(int i=0;i<200;i++)
//			mm.moveMouse(1, 1);
	}

}
