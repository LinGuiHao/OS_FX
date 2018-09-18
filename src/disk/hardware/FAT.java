package disk.hardware;

import myUtil.Number;
/**
 * 
 * @author Zhanbiao_Zhu
 *
 */
public class FAT {
	private byte[] fatItem=new byte[Number.lenOfDisk];

	public FAT() {
		fatItem[4]=-1;
	}
	public byte[] getFatItem() {
		return fatItem;
	}

	public void setFatItem(byte[] fatItem) {
		this.fatItem = fatItem;
	}
	
}
