package device1.software;

import memory.hardware.PCB;

public class DMT {
    /**
     * 设备占用表
     */

    //占用设备的进程
    private PCB useDeviceProcess;
    //进程需要使用的时间
    private int time;
    //进程使用时间
    private int useTime;

    public DMT(PCB useDeviceProcess,int useTime){
        this.useDeviceProcess = useDeviceProcess;
        this.useTime = useTime;
        this.time = useTime;
    }


    public PCB getUseDeviceProcess() {
        return useDeviceProcess;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public int getTime() {
        return time;
    }
}
