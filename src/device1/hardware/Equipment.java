package device1.hardware;

import device1.software.DMT;
import process.software.impl.ProcessOSImp;

import java.util.*;

public class Equipment {
    //设备的种类
    private char equipmentNum;
    //该种设备的数量
    private int numberOfEquipemnt;
    //一种设备一个总的计时器
    private Timer timerOfDevice;
    //设备占用表
    private DMT[] DeviceUseTable;
    //设备是否使用
    private boolean[] isUsingDevice;
    //设备等待队列
    private Queue<DMT> waitingProcess;
    //使用状态
    private final int FINSIH_USE_DEVICE = 0;
    //进程管理
    private ProcessOSImp processOS;


    public Equipment(char equipmentNum,int numberOfEquipemnt,ProcessOSImp processOS){
        this.equipmentNum = equipmentNum;
        this.numberOfEquipemnt = numberOfEquipemnt;
        isUsingDevice = new boolean[numberOfEquipemnt];
        timerOfDevice = new Timer();
        waitingProcess = new LinkedList<>();
        DeviceUseTable = new DMT[numberOfEquipemnt];
        this.processOS = processOS;
        timerOfDevice.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i = 0 ;i < DeviceUseTable.length ;i++){
                    if(DeviceUseTable[i] != null){
                        System.out.println(equipmentNum);
                        System.out.println(DeviceUseTable[i].getUseDeviceProcess());
                        DeviceUseTable[i].setUseTime(DeviceUseTable[i].getUseTime()-1);
                        if(DeviceUseTable[i].getUseTime() == FINSIH_USE_DEVICE){
                            processOS.awake(DeviceUseTable[i].getUseDeviceProcess());
                            DeviceUseTable[i] = null;
                            isUsingDevice[i] = false;
                        }
                    }
                    if(DeviceUseTable[i] == null){
                        if(!waitingProcess.isEmpty()){
                            DeviceUseTable[i] = waitingProcess.poll();
                            isUsingDevice[i] = true;
                        }
                    }
                }
            }
        },0,1000);
    }

    public DMT[] getDeviceUseTable() {
        return DeviceUseTable;
    }

    public Queue<DMT> getWaitingProcess() {
        return waitingProcess;
    }

    public boolean[] getIsUsingDevice() {
        return isUsingDevice;
    }

    public void setWaitingProcess(Queue<DMT> waitingProcess) {
        this.waitingProcess = waitingProcess;
    }
}
