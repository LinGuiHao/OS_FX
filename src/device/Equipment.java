package device;

import java.util.LinkedList;
import java.util.Queue;

import org.omg.CORBA.PUBLIC_MEMBER;

import memory.hardware.PCB;

/**
 *
 * @author Xin
 *
 */
public class Equipment {
	//每个设备有一个设备分配表和一个等待序列
	private int numOfDevice;
	public DMT[] dmts;
	public Queue<WaitProcess> waitList = new LinkedList<>();

	/*
	 * A设备2个，B设备3个，C设备3个
	 */
	public static Equipment A =new Equipment(2);
	public static Equipment B =new Equipment(3);
	public static Equipment C =new Equipment(3);


	public DMT[] getDmts() {
		return dmts;
	}

	public int getNumOfDevice() {
		return numOfDevice;
	}

	public void setNumOfDevice(int numOfDevice) {
		this.numOfDevice = numOfDevice;
	}

	public Equipment(int num){
		DMT[] value = new DMT[num];
		this.dmts = value;
		this.numOfDevice=num;
	}

}
