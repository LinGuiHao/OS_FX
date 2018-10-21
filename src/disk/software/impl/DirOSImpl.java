package disk.software.impl;

import java.util.ArrayList;


import disk.hardware.FileStruct;
import disk.software.DirOS;
import disk.software.DiskManager;
import myUtil.Number;

public class DirOSImpl implements DirOS {
    private DiskManager diskManagerImpl;

    @Override
    public int md(int bnum, String name, int attribute) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        if (diskManagerImpl.getFreeStructPos(bnum) == -1) return -1;
        if (diskManagerImpl.getFreeBlockNum() == 0) return -1;
        //满足磁盘块和目录项都足够的条件

        int pos = diskManagerImpl.getFreeBlockPos();

        FileStruct fileStruct = new FileStruct();
        fileStruct.setName(name);
        fileStruct.setType(" ");
        fileStruct.setFileAttribute(Number.intToByte(attribute));
        fileStruct.setStartPos(Number.intToByte(pos));
        fileStruct.setFileLength((short) 0);


        diskManagerImpl.addStruct(bnum, fileStruct);
        diskManagerImpl.storeFile(fileStruct, null);
        return pos;
    }

    @Override
    public FileStruct[] dir(int bnum) {
        // TODO Auto-generated method stub
        ArrayList<FileStruct> list = new ArrayList<>();
        for (int i = 0; i < 64; i += 8) {
            FileStruct temp = diskManagerImpl.getFileStruct(bnum,i);
            if (diskManagerImpl.isFileStructExist(temp) == false) continue;
            list.add(temp);
        }
        return list.toArray(new FileStruct[list.size()]);
    }

//	/**
//	 * 目的：测试写入文件（文件长度不超过255）
//	 *  结果： 测试成功
//	 */
//	//@Test
//	public void test() {
//			DiskOS diskOS = new DiskOS();
//			diskManagerImpl = new DiskManagerImpl();
//			FileOSImpl fileOS = new FileOSImpl();
//			fileOS.setDiskManagerImpl(diskManagerImpl);
//
//			fileOS.create_file(4, "abc", "e", 0);
//			FileStruct file =diskManagerImpl.getFileStructByName(4, "abc");
//			byte []data={
//
//			};
//			fileOS.write_file(4, "abc", data);
//
//			file =diskManagerImpl.getFileStructByName(4, "abc");
//
//			byte []data2=fileOS.read_file(4,"abc",data.length);
//
//			for(int i=0;i<data2.length;i++) {
//				System.out.println("data["+i+"]"+data2[i]);
//			}
//
//	}
//
//	/**
//	 * 目的 测试长文件大于255保存到磁盘
//	 * 结果 ok
//	 */
//	//@Test
//	public void test2() {
//			DiskOS diskOS = new DiskOS();
//			diskManagerImpl = new DiskManagerImpl();
//			diskManagerImpl.setDiskOS(diskOS);
//			FileOSImpl fileOS = new FileOSImpl();
//			fileOS.setDiskManagerImpl(diskManagerImpl);
//
//			byte[] data = new byte[300];
//			for(int i =0;i<data.length;i++)data[i]=(byte)i;
//			fileOS.create_file(4, "abc", "e", 0);
//			FileStruct file =diskManagerImpl.getFileStructByName(4, "abc");
//
//			fileOS.write_file(4, "abc", data);
//
//			file =diskManagerImpl.getFileStructByName(4, "abc");
//
//			byte []data2=fileOS.read_file(4,"abc",data.length);
//
//			for(int i=0;i<data2.length;i++) {
//				System.out.println("data"+data2[i]);
//			}
//
//	}


    public void setDiskManager(DiskManager diskManagerImpl) {
        this.diskManagerImpl = diskManagerImpl;
    }

    //
//	//@Test
//	public void testRM(){
//		DiskOS diskOS = new DiskOS();
//			diskManagerImpl = new DiskManagerImpl();
//			diskManagerImpl.setDiskOS(diskOS);
//			FileOSImpl fileOS = new FileOSImpl();
//			fileOS.setDiskManagerImpl(diskManagerImpl);
//
//			byte[] data = new byte[300];
//			for(int i =0;i<data.length;i++)data[i]=(byte)i;
//			fileOS.create_file(4, "abc", "e", 0);
//			FileStruct file =diskManagerImpl.getFileStructByName(4, "abc");
//			DirOSImpl dirOS = new DirOSImpl();
//			dirOS.setDiskManagerImpl(diskManagerImpl);
//			fileOS.write_file(4,"abc",data);
//			dirOS.list(4);
//			fileOS.delete_file(4,"abc");
//			dirOS.list(4);
//
//	}
    public void list(int bnum) {
        FileStruct[] fileStruct = dir(bnum);
        for (int i = 0; i < fileStruct.length; i++) {
            if (isDirectory(fileStruct[i]))
                System.out.println("dir:" + fileStruct[i].getName());
            else
                System.out.println("file:" + fileStruct[i].getName() + "." + fileStruct[i].getType());
        }
    }

    @Override
    public boolean rd(int bnum, String path) {
        // TODO Auto-generated method stub
        int pnum = diskManagerImpl.getStructPos(bnum, path," ");
        if (pnum == -1) return false;
        FileStruct fileStruct = diskManagerImpl.getFileStructByName(bnum, path, " ");
        diskManagerImpl.removeFile(fileStruct);
        diskManagerImpl.delStruct(bnum, fileStruct);
        return true;
    }

    @Override
    public boolean format() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDirectory(FileStruct fileStruct) {
        if (fileStruct == null) return false;
        if (fileStruct.getType().equals(" ")) {
            return true;
        }
        return false;
    }
}
