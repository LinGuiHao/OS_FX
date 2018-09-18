package device;

import memory.hardware.PCB;

public class WaitProcess {
	private PCB pcb;
	private int time = 0;
	public PCB getPcb() {
		return pcb;
	}
	public void setPcb(PCB pcb) {
		this.pcb = pcb;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public WaitProcess(PCB pcb,int time){
		this.pcb = pcb;
		this.time = time;
	}
}
