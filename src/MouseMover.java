import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;


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
	
	public void smoothMove(int dx, int dy) {
		
		if(Math.abs(dx) > Math.abs(dy)) {
			for(int x=0;x<Math.abs(dx);x++) {
				int tdy = (x < Math.abs(dy)) ? sign(dy) : 0;
				moveMouse(sign(dx), tdy);
			}
		} else {
			for(int y=0;y<Math.abs(dy);y++) {
				int tdx = (y < Math.abs(dx)) ? sign(dx) : 0;
				moveMouse(tdx, sign(dy));
			}
		}
		
	}
	
	private int sign(int x) {
		return (int) Math.signum((double) x);
	}
	
	public void leftPress() {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void rightPress() {
		robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
	}
	
	public void leftUnpress() {
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void rightUnpress() {
		robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
	}
}
