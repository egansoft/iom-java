import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ControlWindow implements ActionListener {
	protected JButton startButton;
	
	public ControlWindow() {
		JFrame frame = new JFrame("Internet of Mice");
		InnerPanel panel = new InnerPanel(this);
		panel.setOpaque(true);
		frame.setContentPane(panel);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("start")) {
			startButton.setActionCommand("stop");
			startButton.setText("Stop");
			System.out.println("started");
		} else if(e.getActionCommand().equals("stop")) {
			startButton.setActionCommand("start");
			startButton.setText("Start");
			System.out.println("stopped");
		}
	}

	private class InnerPanel extends JPanel {
	
		private static final long serialVersionUID = 1L;

		public InnerPanel(ControlWindow parent) {
			startButton = new JButton("Start");
			startButton.addActionListener(parent);
			startButton.setActionCommand("start");
			add(startButton);
		}
		
	}
}
