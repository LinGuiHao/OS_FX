package OS;

import disk.software.DirOS;
import disk.software.DiskManager;
import disk.software.FileOS;
import disk.software.impl.DirOSImpl;
import disk.software.impl.DiskManagerImpl;
import disk.software.impl.FileOSImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import memory.hardware.PCB;
import memory.software.impl.MemoryOSImpl;
import panel.controller.ExeProcessResultController;
import panel.controller.ProcessController;
import process.software.impl.ProcessOS;
import process.software.impl.ProcessOSImp;
import compiler.Compiler;

import java.io.IOException;
import java.net.URL;

public class OSX extends Application {
    private FileOS fileOS;
    private DirOS dirOS;
    private MemoryOSImpl memoryOS;
    public  ProcessOSImp processOS;
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

    @Override
    public void start(Stage primaryStage) throws IOException {
        ProcessOS processOS = this.processOS;
        String s = "i=1 i=2 i=3 i-- y=3 !A3 !B3 end";
        this.fileOS.create_file(4,"abc","e",0);
        byte[]data = this.compiler.fileTurnToBits(s);
        this.fileOS.write_file(4,"abc","e",data);
        this.fileOS.execute(4,"abc","e");
//        URL location = getClass().getResource("..//panel//fxml//taskmanagerpane.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(location);
//        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
//        Parent root = null;
//        root = fxmlLoader.load();
//        primaryStage.setTitle("任务管理器" );
//        primaryStage.setScene(new Scene(root, 490, 550));
//        primaryStage.setResizable(false);
//        ProcessController controller = fxmlLoader.getController();
//        controller.Init(this.processOS,this.processOS.getCpuOS().getEquipmentOS());
//        primaryStage.setOnCloseRequest(
//                event -> {
//                    try {
//                        System.exit(0);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//        );
//        primaryStage.show();


        URL location = getClass().getResource("..//panel//fxml//exepane.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = null;
        root = fxmlLoader.load();
        primaryStage.setTitle("任务管理器" );
        primaryStage.setScene(new Scene(root, 480, 540));
        primaryStage.setResizable(false);
        ExeProcessResultController controller = fxmlLoader.getController();
        controller.init(this.processOS);
//        controller.setEquipmentOS();
        primaryStage.setOnCloseRequest(
                event -> {
                    try {
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        primaryStage.show();
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

    public ProcessOSImp getProcessOS() {
        return processOS;
    }

    public void setProcessOS(ProcessOSImp processOS) {
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


    public static void  main(String []args) throws Exception {

        launch(args);
    }
}
