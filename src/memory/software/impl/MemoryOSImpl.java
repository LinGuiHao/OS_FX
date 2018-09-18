package memory.software.impl;

import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.print.attribute.standard.RequestingUserName;

import org.junit.Test;

import memory.hardware.MemoryTable;
import memory.hardware.MyMemory;
import memory.hardware.OpenFileItem;
import memory.hardware.PCB;
import memory.hardware.Zone;
import memory.software.MemoryOS;
import myUtil.Number;
import process.software.impl.ProcessOS;

/**
 * 软件实现内存管理类
 * 
 * @author suisui
 *
 */
public class MemoryOSImpl implements MemoryOS {

	private MyMemory memory;
	private MemoryTable memoryTable;// 内存分配表
	private LinkedList<Zone> freeZones;// 空闲区链表
	private  ProcessOS processOS;
	@Override
	public void setProcessOS(ProcessOS processOS) {
		this.processOS = processOS;
	}

	public MemoryOSImpl() {
		// 初始化
		this.memory = new MyMemory();
		this.memoryTable = memory.getSystemArea().getMemoryTable();
		this.freeZones = new LinkedList<>();

		freeZones.add(new Zone(0, Number.sizeOfUserArea, true));
	}

	@Override
	public boolean allocationForFile(byte[] data, OpenFileItem openFileItem) {
		int size = data.length;// 数据长度
		int head = firstFit(size);
		if (head != -1) {// 默认首次适配
			saveData(head, data);// 保存数据
			memory.getSystemArea().getOpenFileTable().getOpenFileTable().add(openFileItem);// 添加打开文件目录项
			return true;
		}
		return false;
	}

	@Override
	public boolean allocationForProcess(byte[] data) {
		int size = data.length;// 数据长度
		int head = firstFit(size);
		if (head != -1) {// 默认首次适配
			saveData(head, data);// 保存数据
			PCB pcb = new PCB();
			pcb.setPc(head);
			processOS.create(getUserArea(),pcb);
			return true;
		}
		return false;
	}

	/**
	 * 存储数据
	 * @param head 存储数据的起址
	 * @param data 存储的数据
	 */
	private void saveData(int head, byte[] data) {
//		System.out.println("head:" + head);
		for (int i = 0; i < data.length; i++) {
			memory.getUserArea().getMemData()[head + i] = data[i];
		}
	}

	@Override
	public boolean collection(PCB tempPcb) {

		if (tempPcb != null) {
			for (int i = 0; i < memoryTable.size(); i++) {
				if (memoryTable.get(i).getHead() == tempPcb.getStartPos()) {
					memory.getSystemArea().getPcb().remove(tempPcb);
					// 更新内存分配表和空闲区链表
					update(i);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean collection(OpenFileItem openFileItem) {
		OpenFileItem tempFileItem = null;
		for (OpenFileItem openFileItem2 : memory.getSystemArea().getOpenFileTable().getOpenFileTable()) {
			if (openFileItem2.equals(openFileItem)) {
				tempFileItem = openFileItem2;
				break;
			}
		}
		if (tempFileItem != null) {
			for (int i = 0; i < memoryTable.size(); i++) {
				if (memoryTable.get(i).getHead() == openFileItem.getStartPos()) {
					memory.getSystemArea().getOpenFileTable().getOpenFileTable().remove(openFileItem);// 删除打开目录项
					// 更新内存分配表和空闲区链表
					update(i);
					return true;
				}
			}
		}
		return false;
	}



	@Override
	public byte[] getUserArea() {
		return memory.getUserArea().getMemData();
	}
//
//	/**
//	 * 测试用
//	 */
//	private void show() {
//		System.out.println("showPcb:");
//		for (int j = 0; j < pcbs.size(); j++) {
//			System.out.print(pcbs.get(j).getPid() + " ");
//		}
//		System.out.println();
//
//		System.out.println("showFreeZone:");
//		for (int j = 0; j < freeZones.size(); j++) {
//			System.out.println("j=" + j + " " + freeZones.get(j));
//		}
//		System.out.println();
//		System.out.println("showMemoryTable:");
//		for (int j = 0; j < memoryTable.size(); j++) {
//			System.out.println("j=" + j + " " + memoryTable.get(j));
//		}
//		System.out.println();
//
//	}

	/**
	 * 更新内存分配表和空闲区链表
	 * 
	 * @param position 回收内存块的下标
	 */
	private void update(int position) {
		int i = position;
		int lastSize, nextSize;
		if (i == 0) {
			memoryTable.get(i).setFree(true);
			freeZones.add(i, memoryTable.get(i));
		} else {// i!=0
			if (!memoryTable.get(i - 1).isFree() && memoryTable.get(i + 1).isFree()) {// 前空闲区满，后内存块(i+1)空闲
				// 更新内存分配表
				lastSize = memoryTable.get(i).getZoneSize();// 合并的前内存块
				nextSize = memoryTable.get(i + 1).getZoneSize();// 合并的后内存块
				memoryTable.get(i).setZoneSize(lastSize + nextSize);
				memoryTable.get(i).setFree(true);

				// 更新空闲区链表
				int j = freeZones.indexOf(memoryTable.get(i + 1));
				if (j != -1) {
					freeZones.get(j).setZoneSize(lastSize + nextSize);
				}

				memoryTable.remove(i + 1);
			} else if (memoryTable.get(i - 1).isFree() && !memoryTable.get(i + 1).isFree()) {// 前空闲区(i-1)空，后内存块满
				// 更新内存分配表
				lastSize = memoryTable.get(i - 1).getZoneSize();// 合并的前内存块
				nextSize = memoryTable.get(i).getZoneSize();// 合并的后内存块
				memoryTable.get(i - 1).setZoneSize(lastSize + nextSize);

				// 更新空闲区链表
				int j = freeZones.indexOf(memoryTable.get(i - 1));
				if (j != -1) {
					freeZones.get(j).setZoneSize(lastSize + nextSize);
				}

				memoryTable.remove(i);
			} else if (memoryTable.get(i - 1).isFree() && memoryTable.get(i + 1).isFree()) {// 前空闲区空，后内存块空
				// 更新内存分配表
				lastSize = memoryTable.get(i - 1).getZoneSize() + memoryTable.get(i).getZoneSize();// 合并的前内存块
				nextSize = memoryTable.get(i + 1).getZoneSize();// 合并的后内存块
				memoryTable.get(i - 1).setZoneSize(lastSize + nextSize);

				// 更新空闲区链表
				freeZones.remove(memoryTable.get(i + 1));
				int j = freeZones.indexOf(memoryTable.get(i - 1));
				if (j != -1) {
					freeZones.get(j).setZoneSize(lastSize + nextSize);
				}

				memoryTable.remove(i);
				memoryTable.remove(i);// 下标已往前移，此次删除不需要改下标
			} else {// 前空闲区满，后内存块满
				memoryTable.get(i).setFree(true);
				for (int j = 0; j < freeZones.size(); j++) {
					if (memoryTable.get(i).getHead() < freeZones.get(j).getHead()) {
						freeZones.add(j, memoryTable.get(i));
						break;
					}
				}
			}
		}
	}

	/**
	 * 首次适配
	 * 
	 * @param size 申请内存大小
	 * @return 分配成功返回分配的内存块起址，失败返回-1
	 */
	private int firstFit(int size) {
		int i = 0;
			for (Zone zone : freeZones) {
				if (size <= zone.getZoneSize()) {
					int remainSize = zone.getZoneSize() - size;// 剩余空闲区大小
					int newHead = zone.getHead() + size;// 空闲区的新起址
					Zone busyZone = new Zone(zone.getHead(), size, false);// 分配忙内存空间

					if (remainSize != 0) {
						Zone freeZone = new Zone(newHead, remainSize, true);// 新空闲块
						// 更新空闲区链表
						freeZones.remove(zone);// 删除旧空闲区
						freeZones.add(i, freeZone);// 增加新空闲区

						// 更新内存分配表
						memoryTable.remove(zone);// 删除旧内存块
						memoryTable.add(busyZone);// 增加忙内存块
						memoryTable.add(freeZone);// 增加空闲内存块
					} else {// remainSize == 0
							// 更新空闲区链表
						freeZones.remove(zone);// 删除旧空闲区
						memoryTable.remove(zone);// 删除旧内存块
						memoryTable.add(busyZone);// 增加忙内存块
					}
					return zone.getHead();
				}
				i++;
			}
			System.out.println("无足够大的空闲块！");
			return -1;
	}

	/**
	 * 下次适配
	 * 
	 * @param size
	 */
	private void nextFit(int size) {
		
	}

	/**
	 * 最佳适配
	 * 
	 * @param size
	 */
	private void bestFit(int size) {

	}

	/**
	 * 最差适配
	 * 
	 * @param size
	 */
	private void worstFit(int size) {

	}
}
