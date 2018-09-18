package disk.hardware;

import java.io.Serializable;

import myUtil.Number;
/**
 * 
 * @author Zhanbiao_Zhu
 *
 */
public class Disk  implements Serializable{
	/**
	 * 这里模拟了一个磁盘的存在
	 * 注意磁盘是物理结构
	 * 我们的管理系统是软件结构
	 * 需要软件和硬件分离
	 * @author Zhanbiao_Zhu
	 */
	private DiskBlock[] disk = new DiskBlock[Number.lenOfDisk];
	public Disk() {
		for(int i =0;i<Number.lenOfDisk;i++) {
			disk[i]= new DiskBlock();
		}
	}
	public DiskBlock[] getDisk() {
		return disk;
	}
	public void setDisk(DiskBlock[] disk) {
		this.disk = disk;
	}
}
