package process.software.impl;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;
import memory.hardware.PCB;
import memory.software.MemoryOS;
import memory.software.impl.MemoryOSImpl;
import process.hardware.imp.CPUImp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessOSImp  implements ProcessOS {
	//PCB队列
	private Queue<PCB> readyProcess;
	private Queue<PCB> blockProcess;

	private CPUImp cpuOS;
	private byte[] data;
	private MemoryOSImpl memoryOS;


	public ProcessOSImp(MemoryOSImpl memoryOS) {
		readyProcess = new LinkedList<>();
		blockProcess = new LinkedList<>();
		cpuOS = new CPUImp();
		cpuOS.runningCPU(this,memoryOS);
	}


	@Override
	public boolean create(byte [] data,PCB newPCB) {
		  this.data = data;
		  readyProcess.add(newPCB);
		  return true;
	}

	//销毁进程
	@Override
	public void destory(PCB destoryPCB) {
		for(PCB pcb:readyProcess){
			if(pcb==destoryPCB){
				readyProcess.remove(pcb);
				break;
			}
		}
		for(PCB pcb:blockProcess){
			if(pcb==destoryPCB){
				blockProcess.remove(pcb);
				break;
			}
		}
	}

	//阻塞进程
	@Override
	public void block(PCB blockPCB) {
		blockProcess.add(blockPCB);
	}

	//唤醒进程
	@Override
	public void awake(PCB awakePCB) {
		if(blockProcess.contains(awakePCB)){
			blockProcess.remove(awakePCB);

		}
		readyProcess.add(awakePCB);
//		System.out.println("唤醒进程");
	}

	public Queue<PCB> getReadyProcess() {
		return readyProcess;
	}

	public Queue<PCB> getBlockProcess() {
		return blockProcess;
	}

	public byte[] getData() {
		return data;
	}

	public CPUImp getCpuOS() {
		return cpuOS;
	}
}
