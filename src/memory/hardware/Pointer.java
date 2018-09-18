package memory.hardware;
/**
 * 读写指针
 * 是文件打开表的结构之一
 * @author Zhanbiao_Zhu
 *
 */
public class Pointer {
	private int dnum;//磁盘块的位置  取值范围0-255
	private int bnum;//磁盘块中的字节位置取值范围在0-63
	public int getDnum() {
		return dnum;
	}
	public void setDnum(int dnum) {
		this.dnum = dnum;
	}
	public int getBnum() {
		return bnum;
	}
	public void setBnum(int bnum) {
		this.bnum = bnum;
	}
}
