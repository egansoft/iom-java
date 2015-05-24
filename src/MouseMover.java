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
	
	public void smoothMove(int dx, int dy) {
//		Complex algorithms that I figured out asleep that don't work
//		if(dx > dy) {
//			int mod, max;
//			if(dy * 2 <= dx) {
//				mod = (int) Math.ceil((double) dx / (double) dy);
//				max = mod - dx + dy;
//			} else {
//				mod = dx/(dx-dy);
//				max = 1;
//			}
//			
//			for(int x=0;x<dx;x++) {
//				int tdy = (x % mod < max) ? 1 : 0;
//				moveMouse(1, tdy);
//			}
//		} else {
//			int mod, max;
//			if(dx * 2 <= dy) {
//				mod = (int) Math.ceil((double) dy / (double) dx);
//				max = mod - dy + dx;
//			} else {
//				mod = dy/(dy-dx);
//				max = 1;
//			}
//			
//			for(int y=0;y<dy;y++) {
//				int tdx = (y % mod < max) ? 1 : 0;
//				moveMouse(tdx, 1);
//			}
//		}
//		System.out.println("dx: " + dx + ", dy: " + dy);
		
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
}
