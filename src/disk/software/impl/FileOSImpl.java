package disk.software.impl;

import disk.hardware.FileStruct;
import disk.software.DiskManager;
import disk.software.FileOS;
import memory.hardware.OpenFileItem;
import memory.software.MemoryOS;
import myUtil.Number;

public class FileOSImpl implements FileOS{

	/**
	 * 创建一个新的文件
	 * @param diskManagerImpl 管理者 用于查询磁盘空间是不是足够 以及 目录项是不是还有空间
	 * @param 其他是文件属性
	 * 如果存在目录项并且存在空的磁盘块那么可以创建新的文件
	 * 
	 */
	private DiskManager diskManagerImpl;
	private MemoryOS memoryOS;
	@Override
	public boolean create_file(int bnum,String filename,String type,int attribute) {
		// TODO Auto-generated method stub
		if(diskManagerImpl.getFreeStructPos(bnum)==-1)return false;
		if(diskManagerImpl.getFreeBlockNum()==0)return false;
		//满足磁盘块和目录项都足够的条件
		
		int pos=diskManagerImpl.getFreeBlockPos();
		
		FileStruct fileStruct = new FileStruct();
		fileStruct.setName(filename);
		fileStruct.setType(type);
		fileStruct.setFileAttribute(Number.intToByte(attribute));
		fileStruct.setStartPos(Number.intToByte(pos));
		fileStruct.setFileLength((short)0);
		
		int pnum = diskManagerImpl.getFreeStructPos(bnum);
		
		diskManagerImpl.addStruct(bnum,fileStruct);
		diskManagerImpl.storeFile(fileStruct,null);
		return true;
	}
	@Override
	public boolean open_file(int bnum, String name,String  type, int kind) {


		// TODO Auto-generated method stub
		FileStruct fileStruct = diskManagerImpl.getFileStructByName(bnum,name,type);
		if(fileStruct == null){
			return  false;
		}
		byte [] data = read_file(fileStruct);
		OpenFileItem openFileItem = new OpenFileItem();
		memoryOS.allocationForFile(data,openFileItem);


		return true;
	}
	/**
	 * @param bnum 磁盘块位置
	 * @param name 文件的名字
	 * @param type 文件类型
	 * 功能：读取所在磁盘块的下name文件length长度的数据
	 */
	@Override
	public byte[] read_file(int bnum, String name, String type) {
		// TODO Auto-generated method stub
		FileStruct fileStruct = diskManagerImpl.getFileStructByName(bnum,name,type);
		if(fileStruct !=null) {
			return diskManagerImpl.getFile(fileStruct);
		}
		return null;
	}

	public byte[] read_file(FileStruct fileStruct) {

			return diskManagerImpl.getFile(fileStruct);
	}
	/**
	 * 将数据写进磁盘内
	 * 同时更新文件目录项
	 */
	@Override
	public boolean write_file(int bnum, String name,String type,byte[]data) {
		// TODO Auto-generated method stub
		FileStruct fileStruct=diskManagerImpl.getFileStructByName(bnum, name,type);
		if(fileStruct==null)return false;
		int pos=fileStruct.getStartPos();
		if(diskManagerImpl.isEnoughBlock(data)) {
			diskManagerImpl.storeFile(fileStruct,data);
			fileStruct.setFileLength((short)data.length);
			diskManagerImpl.updateFileStruct(bnum, fileStruct);
			return true;
		}else {
			return false;
		}	
	}
	public void setDiskManager(DiskManager diskManagerImpl) {
		this.diskManagerImpl = diskManagerImpl;
	}
	@Override
	public boolean close_file(int bnum, String name,String type) {
		// TODO Auto-generated method stub
		
		return false;
	}
	@Override
	public boolean delete_file(int bnum, String name,String type){
		// TODO Auto-generated method stub
		int pnum = diskManagerImpl.getStructPos(bnum, name,type);
		if(pnum ==-1)return false;
		FileStruct fileStruct = diskManagerImpl.getFileStructByName(bnum, name,type);
		diskManagerImpl.removeFile(fileStruct);
		diskManagerImpl.delStruct(bnum, fileStruct);
		return true;
	}
	@Override
	public boolean type_file(int bnum, String name,String  type) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean change(int bnum, String name,String  type) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 调用内存的分配算法，将代码装入内存准备运行
	 * @param bnum 文件所在磁盘块
	 * @param name 文件名字
	 * @param type 文件类型
	 * @return
	 */
	@Override
	public boolean execute(int bnum, String name,String  type) {
		FileStruct fileStruct = diskManagerImpl.getFileStructByName(bnum,name,type);
		if(fileStruct == null){
			return  false;
		}
		byte [] data = read_file(fileStruct);

		memoryOS.allocationForProcess(data);
		return false;
	}

	@Override
	public void setMemoryOS(MemoryOS memoryOS) {
		this.memoryOS=memoryOS;
	}
}
