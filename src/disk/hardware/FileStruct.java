package disk.hardware;

public class FileStruct {
	private String name; //限定3字节
	private String type; //限定一个字节 文本的拓展名是t 可运行文件的拓展名是e 目录没有拓展名
	private byte fileAttribute; //限定一个字节
	private byte startPos; //限定一个字节
	private short fileLength; //限定两个字节

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte getFileAttribute() {
		return fileAttribute;
	}
	public void setFileAttribute(byte fileAttribute) {
		this.fileAttribute = fileAttribute;
	}
	/**
	 * 开始的位置
	 * @return
	 */
	public byte getStartPos() {
		return startPos;
	}
	public void setStartPos(byte startPos) {
		this.startPos = startPos;
	}
	public short getFileLength() {
		return fileLength;
	}
	public void setFileLength(short fileLength) {
		this.fileLength = fileLength;
	}

	@Override
	public String toString(){
		return "["+name+"."+type+" pos:" +startPos+ "  length:"+fileLength+" type = file ] ";
	}
}
