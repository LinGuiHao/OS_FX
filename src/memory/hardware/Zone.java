package memory.hardware;

/**
 * 内存可用空间块节点类
 * 
 * @author suisui
 */
public class Zone {
	private int head;// 内存块起址
	private int zoneSize;// 内存块大小
	private boolean isFree;// 内存块是否空闲

	public Zone(int head, int zoneSize,boolean isFree) {
		this.head = head;
		this.zoneSize = zoneSize;
		this.isFree = isFree;
	}

	public int getZoneSize() {
		return zoneSize;
	}

	public void setZoneSize(int zoneSize) {
		this.zoneSize = zoneSize;
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	@Override
	public String toString() {
		return "Zone [head=" + head + ", zoneSize=" + zoneSize + ", isFree=" + isFree + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + head;
		result = prime * result + (isFree ? 1231 : 1237);
		result = prime * result + zoneSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zone other = (Zone) obj;
		if (head != other.head)
			return false;
		if (isFree != other.isFree)
			return false;
		if (zoneSize != other.zoneSize)
			return false;
		return true;
	}

}
