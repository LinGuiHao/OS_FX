package memory.hardware;

import java.util.ArrayList;

public class OpenFileTable {
	private ArrayList<OpenFileItem> openFileTable=new ArrayList<>();
	private int length;
	public ArrayList<OpenFileItem> getOpenFileTable() {
		return openFileTable;
	}
	public void setOpenFileTable(ArrayList<OpenFileItem> openFileTable) {
		this.openFileTable = openFileTable;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
}
