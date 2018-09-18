package memory.hardware;

import java.util.LinkedList;

import myUtil.Number;

/**
 * 用户区，大小为512字节
 * @author suisui
 *
 */

/**
 * 补充： 用户区 的单位是字节 一共有512个字节 当文件打开或者运行的时候，调度分配算法 分配成功后文件的字节流存储到用户区 分配失败返回错误
 * 
 * @author Zhanbiao_Zhu
 *
 */

public class UserArea {
	private int maxSize;// 内存大小
	private byte[] memData;// 存储数据 

	public UserArea() {
		this.maxSize = Number.sizeOfUserArea;
		this.memData = new byte[maxSize];
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public byte[] getMemData() {
		return memData;
	}

	public void setMemData(byte[] memData) {
		this.memData = memData;
	}

}
