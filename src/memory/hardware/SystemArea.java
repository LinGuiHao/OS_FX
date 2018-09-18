package memory.hardware;

import java.util.ArrayList;
import java.util.Random;

/**
 * 系统区
 * 
 * @author suisui
 *
 */
public class SystemArea {
	private ArrayList<PCB> pcb;// 最多10个PCB
	private OpenFileTable openFileTable;// 暂时没有限制个数
	private MemoryTable memoryTable;

	public SystemArea() {
		memoryTable = new MemoryTable();
		pcb = new ArrayList<>();
	}

	public ArrayList<PCB> getPcb() {
		return pcb;
	}

	public void setPcb(ArrayList<PCB> pcb) {
		this.pcb = pcb;
	}

	public MemoryTable getMemoryTable() {
		return memoryTable;
	}

	public void setMemoryTable(MemoryTable memoryTable) {
		this.memoryTable = memoryTable;
	}

	public OpenFileTable getOpenFileTable() {
		return openFileTable;
	}

	public void setOpenFileTable(OpenFileTable openFileTable) {
		this.openFileTable = openFileTable;
	}

}
