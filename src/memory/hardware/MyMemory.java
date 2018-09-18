package memory.hardware;
/**
 * 模拟内存
 * @author suisui
 *
 */
public class MyMemory {
	private SystemArea systemArea;//系统区
	private UserArea userArea;//用户区
	
	public MyMemory() {
		systemArea=new SystemArea();
		userArea=new UserArea();
	}
	
	public SystemArea getSystemArea() {
		return systemArea;
	}

	public void setSystemArea(SystemArea systemArea) {
		this.systemArea = systemArea;
	}

	public UserArea getUserArea() {
		return userArea;
	}

	public void setUserArea(UserArea userArea) {
		this.userArea = userArea;
	}
	
}
