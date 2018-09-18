package disk.software;

import disk.hardware.FileStruct;
import disk.software.impl.DiskOS;

/**
 * 磁盘空间管理
 * 连接文件和磁盘
 * 将文件存储到磁盘
 * 会调用磁盘的存取
 * 将文件byte分别存放到block中
 *
 * @author Zhanbiao_Zhu
 */
public interface DiskManager {

    //磁盘空间管理

    /**
     * 获取空闲FAT块的位置
     *
     * @return 位置
     */
    int getFreeBlockPos();

    /**
     * 获取空闲磁盘块数量
     *
     * @return 位置
     */
    int getFreeBlockNum();

    /**
     * 给定数据判断能否存下
     *
     * @param file 要输入的文件流
     * @return
     */
    boolean isEnoughBlock(byte[] file);

    /**
     * @param fileStruct 文件目录项
     * @param file 文件输入流
     */


    void storeFile(FileStruct fileStruct, byte[] file);

    /**
     * 获取文件的字节数据
     *
     * @param fileStruct 文件目录项
     * @return 文件流
     */
    public byte[] getFile(FileStruct fileStruct);

    /**
     * 通过文件目录项删除一个文件
     * 如果文件目录项不存在说明文件不存在
     * 在一定程度上避免了空指针问题
     *
     * @param fileStruct 文件目录项
     *
     */
    void removeFile(FileStruct fileStruct);



    /**
     * @param bnum 磁盘块位置
     * @param name 目录的名字
     * @return
     */
    int getStructPos(int bnum, String name,String type);

    /**
     * @param bnum 磁盘块位置
     * @return 返回空闲文件目录项位置 如果为-1表示不存在
     */
    int getFreeStructPos(int bnum);

    /**
     * 通过磁盘块位置和文件结构添加到磁盘
     * @param bnum 磁盘块文职
     * @param fileStruct 文件目录项
     */

    void addStruct(int bnum, FileStruct fileStruct);

    /**
     * 通过磁盘块位置和文件结构删除文件结构
     * @param bnum 磁盘块位置
     * @param fileStruct 文件目录项
     */
    void delStruct(int bnum, FileStruct fileStruct);

    /**
     * @param bnum 磁盘块位置
     * @param pnum 磁盘块内便宜位置
     * @return 文件目录项
     */
    FileStruct getFileStruct(int bnum,int pnum);

    /**
     * @param bnum 磁盘块位置
     * @param name 文件名字
     * @param type 文件类型
     * @return
     */
    FileStruct getFileStructByName(int bnum, String name, String type);

    /**
     * 修改文件结构块内容
     *
     * @param bnum
     * @param fileStruct
     * @return
     */
    boolean updateFileStruct(int bnum, FileStruct fileStruct);

    public boolean isFileStructExist(FileStruct fileStruct);

}
