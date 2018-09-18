package device;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import memory.hardware.PCB;

public class DMT {
	//设备占用信息
	//占用设备的进程id
	private PCB proceeID ;
	//设备占用时间
	private int useTime = 0;
	//设备占用状态 等待0，运行1
	private int status = 0;

	public void useDevice(PCB pcb,int time){
		this.proceeID = pcb;
		this.useTime = time;
	}
	public int getUseTime() {
		return useTime;
	}
	public PCB getProceeID() {
		return proceeID;
	}
	public void setProceeID(PCB proceeID) {
		this.proceeID = proceeID;
	}
	public void setUseTime(int useTime) throws InterruptedException{
		this.useTime = useTime;
		CountDown countDown = new CountDown(useTime);
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
