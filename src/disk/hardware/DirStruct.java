package disk.hardware;

public class DirStruct extends FileStruct {
	public DirStruct() {
		setType(" ");
		setFileLength((short)0);
	}
	@Override
	public String toString(){
		return "["+getName()+"."+getType()+" pos:" +getStartPos()+ "  length:"+getFileLength()+" type = dir ] ";
	}
}
