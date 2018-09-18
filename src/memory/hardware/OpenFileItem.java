package memory.hardware;

import disk.hardware.FileStruct;

/**
 * 
 * @author Zhanbiao_Zhu
 *
 */
public class OpenFileItem {

	private FileStruct fileStruct;
	private Pointer read;
	private Pointer write;

	// 打开的文件在内存的位置
	private int startPos; // start代表起点
	private int length; // length代表这个文件的大小

	public FileStruct getFileStruct() {
		return fileStruct;
	}

	public void setFileStruct(FileStruct fileStruct) {
		this.fileStruct = fileStruct;
	}

	public Pointer getRead() {
		return read;
	}

	public void setRead(Pointer read) {
		this.read = read;
	}

	public Pointer getWrite() {
		return write;
	}

	public void setWrite(Pointer write) {
		this.write = write;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileStruct == null) ? 0 : fileStruct.hashCode());
		result = prime * result + ((read == null) ? 0 : read.hashCode());
		result = prime * result + ((write == null) ? 0 : write.hashCode());
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
		OpenFileItem other = (OpenFileItem) obj;
		if (fileStruct == null) {
			if (other.fileStruct != null)
				return false;
		} else if (!fileStruct.equals(other.fileStruct))
			return false;
		if (read == null) {
			if (other.read != null)
				return false;
		} else if (!read.equals(other.read))
			return false;
		if (write == null) {
			if (other.write != null)
				return false;
		} else if (!write.equals(other.write))
			return false;
		return true;
	}

}
