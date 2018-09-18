package disk.software;

import disk.software.impl.DiskManagerImpl;
import memory.software.MemoryOS;

/**
 * 利用接口避免耦合
 * 
 * @author Zhanbiao_Zhu
 *
 */
public interface FileOS {
	/**
	 * 创建文件的路径+名字
	 * 还有文件的属性
	 * @param bnum 所在磁盘块
	 * @param filename 文件的名字
	 * @param type 文件的后缀名
	 * @param attribute 文件的属性
	 * @return
	 */
	public boolean create_file(int bnum,String filename,String type,int attribute);
	
	/**
	 * 打开文件的名字和打开的方式（读或者写）
	 * @param bnum 文件磁盘块位置
	 * @param name  文件的名字
	 * @param kind 是什么类型打开
	 * @return 操作结果
	 */
	public boolean open_file(int bnum,String name,String type,int kind);
	/**
	 * 读取length个字节
	 * （其实不知道有什么作用）
	 * @param  bnum 磁盘块位置
	 * @param name 文件名字
	 * @param type 文件类型
	 * @return 文件流
	 */
	public byte[] read_file(int bnum,String name,String type);
	/**
	 *
	 *
	 * @param name 文件名字
	 * @param type 文件类型
	 * @param bnum 代表磁盘块位置
	 * @param data 要写入的文件流
	 *
	 * @return
	 */
	public boolean write_file(int bnum,String name,String type,byte [] data);
	public boolean close_file(int bnum,String name,String type);
	public boolean delete_file(int bnum,String name,String type);
	/**
	 * 显示文件的内容
	 * @param name 文件名字
	 * @param type 文件类型
	 * @param bnum 文件所在磁盘块
	 * @return
	 */
	public boolean type_file(int bnum,String name,String type);

	/**
	 * 改变文件的属性
	 * @param name 文件名字
	 * @param type 文件类型
	 * @param bnum 文件所在磁盘块
	 * @return
	 */
	public boolean change(int bnum,String name,String type);
	/**
	 * 运行可执行文件
	 * @param name 文件名字
	 * @param type 文件类型
	 * @param bnum 文件所在磁盘块
	 * @return
	 */
	public boolean execute(int bnum,String name,String type);


	void setDiskManager(DiskManager diskManager);

	void setMemoryOS(MemoryOS memoryOS);

}
