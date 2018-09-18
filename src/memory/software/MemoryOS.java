package memory.software;

import java.util.ArrayList;

import disk.hardware.FileStruct;
import memory.hardware.OpenFileItem;
import memory.hardware.PCB;
import process.software.impl.ProcessOS;

public interface MemoryOS {
	/**
	 * 打开文件申请分配内存
	 * 
	 * @param data存储在内存的数据
	 * @param openFileItem 打开文件目录项
	 * @return boolean
	 */
	public boolean allocationForFile(byte[] data, OpenFileItem openFileItem);

	/**
	 * 进程申请分配内存
	 * 
	 * @param data存储在内存的数据
	 * @return boolean
	 */
	public boolean allocationForProcess(byte[] data);

	/**
	 * 用于CPU调度，关闭进程后释放空间
	 * 
	 * @param pid 进程标志符
	 * @return boolean
	 */
	public boolean collection(PCB pcb);

	/**
	 * 通过打开文件目录项查找文件打开目录表然后回收内存
	 * @param openFileItem 打开文件目录项
	 * @return boolean
	 */
	public boolean collection(OpenFileItem openFileItem);

	/**
	 * 
	 * @return ArrayList<PCB>
	 */

	/**
	 * 返回用户区的数据
	 * @return byte[]
	 */
	public byte[] getUserArea();

	public void setProcessOS(ProcessOS processOS);
}
