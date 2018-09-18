package disk.software;

import disk.hardware.FileStruct;

/**
 * 
 * @author Zhanbiao_Zhu
 *
 */
public interface DirOS {
	/**
	 * 新建目录
	 * @param
	 * @return
	 * @return 如果-1代表是错误，如果是其他代表是新目录得块
	 */
	public int md(int bnum,String name,int attribute);
	/**
	 * 显示目录下的文件和目录
	 * 想当于dir命令
	 * @return
	 */
	
	public FileStruct[] dir(int bnum);
	
	/**
	 * 删除一个空目录
	 * -->我们要实现一个删除非空目录的扩张功能
	 * @param path
	 * @return
	 */
	public boolean rd(int bnum,String path);
	/**
	 * 格式化磁盘
	 * @return
	 */
	public boolean format();

	/**
	 * 通过文件结构判断是不是目录
	 * @param fileStruct 文件的结构
	 * @return
	 */
	public boolean isDirectory(FileStruct fileStruct);

	void setDiskManager(DiskManager diskManager);
}
