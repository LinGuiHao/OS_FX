package memory.hardware;

import java.util.LinkedList;

import myUtil.Number;

/**
 * 内存分配表
 * 
 * @author suisui
 *
 */
public class MemoryTable extends LinkedList<Zone>{

	public MemoryTable() {
		this.add(new Zone(0, Number.sizeOfUserArea,true));
	}
}
