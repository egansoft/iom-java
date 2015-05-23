import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;


public class MouseMover {
	private Robot robot;
	
	public MouseMover() throws AWTException {
		robot = new Robot();
		
	}
	
	public void moveMouse(int dx, int dy) {
		int x = MouseInfo.getPointerInfo().getLocation().x;
		int y = MouseInfo.getPointerInfo().getLocation().y;
		
		robot.mouseMove(x+dx, y+dy);
	}
}
