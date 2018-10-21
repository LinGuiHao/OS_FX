package panel.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import memory.hardware.PCB;
import myUtil.Number;
import process.software.impl.ProcessOSImp;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ExeProcessResultController {
    @FXML
    private VBox resultBox;

    private PCB pcb;
    private boolean isStop = false;
    private int time = 0;

    class processRunningTask extends Thread{
        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Label label = new Label();
                    label.setTextFill(Color.WHITE);
                    if(pcb != null){
                        if(pcb.getState() == Number.PROCESS_RUNNING){
                            label.setText("CPU运行处理进程...\n" +
                                    "结果:");
                            resultBox.getChildren().add(label);
                            for(Map.Entry<Short,Short> entry : pcb.getIntermediaResult().entrySet()){
                                label =new Label();
                                label.setTextFill(Color.WHITE);
                                label.setText((char) (entry.getKey()+'a')+"  :  "+entry.getValue());
                                resultBox.getChildren().add(label);
                            }
                        }else if(pcb.getState() == Number.PROCESS_USE_DEVICE){
                            label.setText("进程正在使用设备...");
                            resultBox.getChildren().add(label);
                        }else if(pcb.getState() == Number.PROCESS_FINISH){
                            label.setText("进程完成.\n结果:");
                            resultBox.getChildren().add(label);
                            for(Map.Entry<Short,Short> entry : pcb.getIntermediaResult().entrySet()){
                                label =new Label();
                                label.setTextFill(Color.WHITE);
                                label.setText((char) (entry.getKey()+'a')+"  :  "+entry.getValue());
                                resultBox.getChildren().add(label);
                            }
                            isStop = true;
                        }
                    }
                }
            });
        }
    }

    public void init(ProcessOSImp processOS){
        Timer timer = new Timer();
        processRunningTask processRunningTask = new processRunningTask();
        resultBox.setPrefWidth(499);
        resultBox.setPrefHeight(585);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(processOS.getCpuOS().getRunningProcess()!=null&&pcb==null){
                    pcb = processOS.getCpuOS().getRunningProcess();
                }
                if(pcb!=null && !isStop){
                    processRunningTask.run();
                    time++;
                    if(time >= 15){
                        resultBox.setPrefHeight(resultBox.getHeight()+20);
                    }
                }

            }
        },0,1000);
    }


}
