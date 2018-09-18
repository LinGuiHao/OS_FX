package OS;

import disk.software.DirOS;
import disk.software.DiskManager;
import disk.software.FileOS;
import disk.software.impl.DirOSImpl;
import disk.software.impl.DiskManagerImpl;
import disk.software.impl.FileOSImpl;
import memory.software.impl.MemoryOSImpl;
import org.junit.Test;
import process.software.impl.ProcessOS;
import process.software.impl.ProcessOSImp;
import compiler.Compiler;
public class OSX {
    private FileOS fileOS;
    private DirOS dirOS;
    private MemoryOSImpl memoryOS;
    private ProcessOS processOS;
    private DiskManager diskManager;
    private Compiler compiler;

    public OSX(){
        diskManager = new DiskManagerImpl();
        fileOS = new FileOSImpl();
        fileOS.setDiskManager(diskManager);
        dirOS = new DirOSImpl();
        dirOS.setDiskManager(diskManager);
        memoryOS = new MemoryOSImpl();
        fileOS.setMemoryOS(memoryOS);
        processOS = new ProcessOSImp(memoryOS);
        memoryOS.setProcessOS(processOS);
        compiler = new Compiler();
    }

    public FileOS getFileOS() {
        return fileOS;
    }

    public void setFileOS(FileOS fileOS) {
        this.fileOS = fileOS;
    }

    public DirOS getDirOS() {
        return dirOS;
    }

    public void setDirOS(DirOS dirOS) {
        this.dirOS = dirOS;
    }

    public MemoryOSImpl getMemoryOS() {
        return memoryOS;
    }

    public void setMemoryOS(MemoryOSImpl memoryOS) {
        this.memoryOS = memoryOS;
    }

    public ProcessOS getProcessOS() {
        return processOS;
    }

    public void setProcessOS(ProcessOS processOS) {
        this.processOS = processOS;
    }

    public DiskManager getDiskManager() {
        return diskManager;
    }

    public void setDiskManager(DiskManager diskManager) {
        this.diskManager = diskManager;
    }

    public Compiler getCompiler() {
        return compiler;
    }

    public void setCompiler(Compiler compiler) {
        this.compiler = compiler;
    }


    public static void  main(String []args){
        OSX osx = new OSX();
        String s = "i=1 i=2 i=3 i-- y=3 end";
        osx.fileOS.create_file(4,"abc","e",0);
        byte[]data = osx.compiler.fileTurnToBits(s);
        osx.fileOS.write_file(4,"abc","e",data);
        osx.fileOS.execute(4,"abc","e");
    }
}
