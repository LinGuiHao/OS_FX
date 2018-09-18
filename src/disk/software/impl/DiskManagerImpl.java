package disk.software.impl;

import org.junit.Test;

import disk.hardware.Disk;
import disk.hardware.DiskBlock;
import disk.hardware.FAT;
import disk.hardware.FileStruct;
import disk.software.DiskManager;
import myUtil.Number;

public class DiskManagerImpl implements DiskManager{

	private  FAT fat = new FAT();
	private  Disk disk= new Disk();


	@Override
	public int getFreeBlockPos() {
		// TODO Auto-generated method stub
		byte [] fatItem=fat.getFatItem();
		for(int i =4;i<fatItem.length;i++) {
			if(Number.byteToInt(fatItem[i])==0) {
					return i;
			}
		}
		return -1;
	}

	@Override
	public int getFreeBlockNum() {
		// TODO Auto-generated method stub
		byte [] fatItem=fat.getFatItem();
		int ans=0;
		for(int i =0;i<fatItem.length;i++) {
			if(Number.byteToInt(fatItem[i])==0) {
					ans++;
			}
		}
		return ans;
	}
	@Override
	public boolean isEnoughBlock(byte[] file) {
		int len=file.length/64+1;
		if(len<=getFreeBlockNum()) {
			return true;
		}else
		return false;
	}
	@Override
	public void storeFile(FileStruct fileStruct,byte[] file) {
		// TODO Auto-generated method stub
		/**
		 * 存取数据的时候去fat表查找适合的空间将数据放进磁盘
		 * 无论如何一定会占用一个块磁盘
		 * 
		 */
		int start = fileStruct.getStartPos();
		byte [] fatItem=fat.getFatItem();
		if(file == null) {
			fatItem[start]=-1;
			return;
		}
		int last;
		int pos;
		int wp=0; //写指针
		int remain = file.length;
		int len = file.length;
		DiskBlock diskBlock[] =disk.getDisk();
		//无论如何都需要占用一块内存然后初分配
		//初分配如果不够需要更多的块构成链表
		last = pos = start;
		fatItem[pos]=-1; //假设只有一块
		int times=remain;
		for(int i=0;i<Math.min(times,64);i++) {
			diskBlock[pos].getDiskblock()[i] = file[wp];
			wp++;
			remain--;
		}
		//再分配
		for(int i=0;i<len/64;i++) {
			fatItem[last]=-1;
			pos = getFreeBlockPos();
			fatItem[last] =Number.intToByte(pos);
			last = pos;
			times=remain;
			for(int j =0;j<Math.min(times,64);j++) {
				diskBlock[pos].getDiskblock()[j] = file[wp];
				wp++;
				remain--;
			}
		}
		fatItem[pos]=-1;
	}

	/**
	 * 给定FAT的起点和文件的长度
	 * 将文件读取出来成流式文件
	 */
	@Override
	public byte[] getFile(FileStruct fileStruct) {
		// TODO Auto-generated method stub
		
		int pos=fileStruct.getStartPos();
		int remain = fileStruct.getFileLength();
		int len = remain;
		int rp=0;//读取指针用于定位
		byte []ans = new byte[len];
		byte []fatItem = fat.getFatItem();
		DiskBlock[] diskBlock = disk.getDisk();
		while(remain!=0) {
			int time=remain;
			for(int j=0;j<Math.min(64,time);j++) {
				ans[rp] =diskBlock[pos].getDiskblock()[j]; 
				rp++;
				remain--;
			}
			pos = Number.byteToInt(fatItem[pos]);
		}
		return ans;
		
	}

	/**
	 * 删除FAT中的分配信息
	 */
	@Override
	public void removeFile(FileStruct fileStruct) {
		// TODO Auto-generated method stub
		int pos=fileStruct.getStartPos();
		byte []fatItem = fat.getFatItem();
		int last = pos;
		while(pos!=255) {
			pos = Number.byteToInt(fatItem[pos]);
			fatItem[last]=0;
			last = pos;
		}

	}
	/**
	 * 获取b磁盘下的空余目录项下标
	 */
	@Override
	public int getFreeStructPos(int bnum) {
		// TODO Auto-generated method stub
		byte []diskItem = disk.getDisk()[bnum].getDiskblock();
		for(int i=0;i<64;i+=8) {
			if(diskItem[i]=="$".getBytes()[0]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 通过名字查询文件目录项位置
	 */
 	@Override
	public int getStructPos(int bnum, String name,String  type) {
		// TODO Auto-generated method stub
		for(int i=0;i<64;i+=8) {
			FileStruct fileStruct=getFileStruct(bnum, i);
			if(fileStruct.getName().equals(name)&&fileStruct.getType().equals(type)) {
				return i;
			}
		}
		return -1;
	}
 	/**
 	 * 通过名字查询得到文件目录项
 	 */
	@Override
	public FileStruct getFileStructByName(int bnum, String name,String type) {
		// TODO Auto-generated method stub
		for(int i=0;i<64;i+=8) {
			FileStruct fileStruct=getFileStruct(bnum, i);
			if(fileStruct.getName().equals(name) && fileStruct.getType().equals(type)) {
				return fileStruct;
			}
		}
		return null;
	}

	@Override
	public void addStruct(int bnum,FileStruct fileStruct) {
		// TODO Auto-generated method stub
		//文件结构是8个字节的
		
		/**
		 * 格式如下:
		 * 名字[0]
		 * 名字[1]
		 * 名字[2]
		 * 类型[0]
		 * 属性[0]
		 * 起点[0]
		 * 长度[0] 低位
		 * 长度[1] 高位
		 */
		if(fileStruct.getName().length()==1) {
			fileStruct.setName("  "+fileStruct.getName());
		}
		if(fileStruct.getName().length()==2) {
			fileStruct.setName(" "+fileStruct.getName());
		}
		byte[] fileByte=new byte[]{
			fileStruct.getName().getBytes()[0],
			fileStruct.getName().getBytes()[1],
			fileStruct.getName().getBytes()[2],
			fileStruct.getType().getBytes()[0],
			fileStruct.getFileAttribute(),
			fileStruct.getStartPos(),
			(byte)(fileStruct.getFileLength()),
			(byte)(fileStruct.getFileLength()>>8)
		};
		int pnum = getFreeStructPos(bnum);

		for(int i =0;i<8;i++) {
			disk.getDisk()[bnum].getDiskblock()[pnum+i]=fileByte[i];
		}
	}
	/**
	 * 删除文件目录项
	 */
	@Override
	public void delStruct(int bnum, FileStruct fileStruct) {
		// TODO Auto-generated method stub
		int pnum = getStructPos(bnum,fileStruct.getName(),fileStruct.getType());
		for(int i =0;i<8;i++) {
			disk.getDisk()[bnum].getDiskblock()[pnum+i]="$".getBytes()[0];
		}
	}
	/**
	 * 通过位置得到文件目录项
	 */

	public FileStruct getFileStruct(int bnum, int pnum) {
		// TODO Auto-generated method stub
		FileStruct fileStruct  = new FileStruct();
		byte []diskItem = disk.getDisk()[bnum].getDiskblock();
		fileStruct.setName(new String(new byte[] {
				diskItem[pnum],diskItem[pnum+1],diskItem[pnum+2]
		}));
		fileStruct.setType(new String(new byte[] {
				diskItem[pnum+3]
		}));
		fileStruct.setFileAttribute(diskItem[pnum+4]);
		fileStruct.setStartPos(diskItem[pnum+5]);
		short x =(short)(diskItem[pnum+7]*256+diskItem[pnum+6]);
		fileStruct.setFileLength(x);
		fileStruct.setName(fileStruct.getName().trim());
		return fileStruct;
	}

	@Override
	public boolean isFileStructExist(FileStruct fileStruct) {
		if(fileStruct == null) {
			return false;
		}else {
			if(fileStruct.getName().startsWith("$")) {
				return false;
			}else {
				return true;
			}
		}
	}
	/**
	 * 更新文件目录项内容
	 */
	@Override
	public boolean updateFileStruct(int bnum,FileStruct fileStruct) {
		// TODO Auto-generated method stub
		int pnum=getStructPos(bnum, fileStruct.getName(),fileStruct.getType());
		if(pnum==-1)return false;
		byte[] fileByte=new byte[]{
				fileStruct.getName().getBytes()[0],
				fileStruct.getName().getBytes()[1],
				fileStruct.getName().getBytes()[2],
				fileStruct.getType().getBytes()[0],
				fileStruct.getFileAttribute(),
				fileStruct.getStartPos(),
				(byte)(fileStruct.getFileLength()),
				(byte)(fileStruct.getFileLength()>>8)
		};
		for(int i =0;i<8;i++) {
				disk.getDisk()[bnum].getDiskblock()[pnum+i]=fileByte[i];
		}
		return true;
	}

}
