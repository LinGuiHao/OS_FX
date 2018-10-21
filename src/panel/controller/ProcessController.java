package panel.controller;

import device1.software.DMT;
import device1.software.EquipmentOSImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import memory.hardware.PCB;
import myUtil.Number;
import process.software.impl.ProcessOSImp;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProcessController {
    /**
     *
     */
    @FXML private Circle A1;
    @FXML private Circle A2;
    @FXML private Circle A3;
    @FXML private Circle B1;
    @FXML private Circle B2;
    @FXML private Circle C1;

    @FXML private ProgressBar eA1;
    @FXML private ProgressBar eA2;
    @FXML private ProgressBar eA3;
    @FXML private ProgressBar eB1;
    @FXML private ProgressBar eB2;
    @FXML private ProgressBar eC1;

    @FXML private  Label labelA1;
    @FXML private  Label labelA2;
    @FXML private  Label labelA3;
    @FXML private  Label labelB1;
    @FXML private  Label labelB2;
    @FXML private  Label labelC1;

    @FXML private VBox processBox;
    @FXML private Pane processPane;
    @FXML private Line processLine;

    private EquipmentOSImpl equipmentOS;
    private ArrayList<HBox> processHBoxList;
    private ArrayList<PCB> processList;
    private ProcessOSImp processOS;

    public void Init(ProcessOSImp processOS,EquipmentOSImpl equipmentOS){
        this.equipmentOS = equipmentOS;
        this.processOS = processOS;
        processList = new ArrayList<>();
        processHBoxList = new ArrayList<>();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new TimerTask() {
                    @Override
                    public void run() {
                        updataEquimentUI();
                        updataProcessUI();
                    }
                });
            }
        },0,1000);
    }

    public HBox createProcessTable(PCB pcb) {
        HBox hBox = new HBox();
        Pane namePane = new Pane();
        Pane statePane = new Pane();
        Label name = new Label();
        Label state = new Label();
        namePane.setPrefSize(350,30);
        statePane.setPrefSize(150,30);
        Integer pid = pcb.getPid();
        name.setText(pid.toString());
        namePane.getChildren().add(name);
        if(pcb.getState() == Number.PROCESS_READY){
            state.setText("就绪");
        }else if (pcb.getState() == Number.PROCESS_USE_DEVICE){
            state.setText("设备阻塞");
        }else if(pcb.getState() == Number.PROCESS_FINISH){
            state.setText("进程完成");
        }else if(pcb.getState() == Number.PROCESS_RUNNING){
            state.setText("运行");
        }
        statePane.getChildren().add(state);
        hBox.getChildren().add(namePane);
        hBox.getChildren().add(statePane);
        return hBox;
    }
    public ArrayList<HBox> createProcessTableUI(){
        processHBoxList.clear();
        if(processOS.getCpuOS().getRunningProcess() != null){
            processHBoxList.add(createProcessTable(processOS.getCpuOS().getRunningProcess()));
        }
        for(PCB pcb : processOS.getReadyProcess()){
            if(pcb != null){
                processHBoxList.add(createProcessTable(pcb));
            }
        }
        for(PCB pcb : processOS.getBlockProcess()){
            if(pcb != null){
                processHBoxList.add(createProcessTable(pcb));
            }
        }
        return processHBoxList;
    }

    public void updataProcessUI(){
        processBox.getChildren().clear();
        processBox.getChildren().addAll(createProcessTableUI());
    }

    public void updataEquimentUI(){
        isUseEquipment(equipmentOS.getEquipmentA().getDeviceUseTable()[0],A1,Color.BLUE);
        isUseEquipment(equipmentOS.getEquipmentA().getDeviceUseTable()[1],A2,Color.BLUE);
        isUseEquipment(equipmentOS.getEquipmentA().getDeviceUseTable()[2],A3,Color.BLUE);
        isUseEquipment(equipmentOS.getEquipmentB().getDeviceUseTable()[0],B1,Color.ORANGE);
        isUseEquipment(equipmentOS.getEquipmentB().getDeviceUseTable()[1],B2,Color.ORANGE);
        isUseEquipment(equipmentOS.getEquipmentC().getDeviceUseTable()[0],C1,Color.RED);

        progressOfUseEquipment(equipmentOS.getEquipmentA().getDeviceUseTable()[0],eA1);
        progressOfUseEquipment(equipmentOS.getEquipmentA().getDeviceUseTable()[1],eA2);
        progressOfUseEquipment(equipmentOS.getEquipmentA().getDeviceUseTable()[2],eA3);
        progressOfUseEquipment(equipmentOS.getEquipmentB().getDeviceUseTable()[0],eB1);
        progressOfUseEquipment(equipmentOS.getEquipmentB().getDeviceUseTable()[1],eB2);
        progressOfUseEquipment(equipmentOS.getEquipmentC().getDeviceUseTable()[0],eC1);
        if(equipmentOS.getEquipmentA().getDeviceUseTable()[0] != null){
            labelA1.setText(""+equipmentOS.getEquipmentA().getDeviceUseTable()[0].getUseDeviceProcess().getPid());
        }else{
            labelA1.setText("null");
        }
        if(equipmentOS.getEquipmentA().getDeviceUseTable()[1]!= null){
            labelA2.setText(""+equipmentOS.getEquipmentA().getDeviceUseTable()[1].getUseDeviceProcess().getPid());
        }else{
            labelA2.setText("null");
        }
        if(equipmentOS.getEquipmentA().getDeviceUseTable()[2] != null){
            labelA3.setText(""+equipmentOS.getEquipmentA().getDeviceUseTable()[2].getUseDeviceProcess().getPid());
        }else{
            labelA3.setText("null");
        }
        if(equipmentOS.getEquipmentB().getDeviceUseTable()[0] != null){
            labelB1.setText(""+equipmentOS.getEquipmentB().getDeviceUseTable()[0].getUseDeviceProcess().getPid());
        }else{
            labelB1.setText("null");
        }
        if(equipmentOS.getEquipmentA().getDeviceUseTable()[1] != null){
            labelB2.setText(""+equipmentOS.getEquipmentB().getDeviceUseTable()[1].getUseDeviceProcess().getPid());
        }else{
            labelB2.setText("null");
        }
        if(equipmentOS.getEquipmentC().getDeviceUseTable()[0] != null){
            labelC1.setText(""+equipmentOS.getEquipmentC().getDeviceUseTable()[0].getUseDeviceProcess().getPid());
        }else{
            labelC1.setText("null");
        }

    }

    public boolean isUseEquipment(DMT equimentNum,Circle equipmentUI, Paint color){
        if(equimentNum!=null){
            equipmentUI.setFill(color);
            return true;
        }else{
            equipmentUI.setFill(Color.WHITE);
            return false;
        }
    }

    public void progressOfUseEquipment(DMT equimentNum,ProgressBar progressBarOfEquipment){
        if(equimentNum!=null){
            progressBarOfEquipment.setProgress(1-(double)equimentNum.getUseTime()/equimentNum.getTime());
        }else{
            progressBarOfEquipment.setProgress(0);
        }
    }

}
