package disk.hardware;

import java.io.Serializable;

import myUtil.Number;
/**
 * 
 * @author Zhanbiao_Zhu
 */
public class DiskBlock  implements Serializable{
	/**
	 * 为了实现字节单位
	 * 我们使用了byte
	 * 为了实现流式存储我们使用了序列化（其实并不算流式存储）
	 * @author Zhanbiao_Zhu
	 */
	private byte[] diskblock = new byte[Number.lenOfDiskBlock];
	public DiskBlock() {
			for(int i =0;i<Number.lenOfDiskBlock;i++)diskblock[i]="$".getBytes()[0];
	}
	public byte[] getDiskblock() {
		return diskblock;
	}
	public void setDiskblock(byte[] diskblock) {
		this.diskblock = diskblock;
	}
}
