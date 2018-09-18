package compiler;


import java.util.ArrayList;
import java.util.List;

public class Compiler {
	/**
	 * 编译器
	 */
	public Compiler() {
		// TODO Auto-generated constructor stub
	}
	//指令译码
	public byte[] turuToBit(String primitive) {
		short ir = 0;
		if(primitive.matches("[a-z]=[0-9]")) {
			char c = (char) (primitive.charAt(0)-'a');
			char number = primitive.charAt(2);
			ir = (short) (c*Math.pow(2, 8) + (number-48));
		}else if(primitive.matches("[a-z][+][+]")) {
			ir = 0b0010000000000000;
			char c = (char) (primitive.charAt(0)-'a');
			ir=(short) (ir + c*Math.pow(2, 8));
		}else if(primitive.matches("[a-z][-][-]")) {
			ir = 0b0100000000000000;
			char c = (char) (primitive.charAt(0)-'a');
			ir=(short) (ir + c*Math.pow(2, 8));
		}else if(primitive.matches("[!][A-Z][0-9]")) {
			ir = 0b0110000000000000;
			char c = (char) (primitive.charAt(1)-'A');
			char number = primitive.charAt(2);
			ir=(short) (ir + c*Math.pow(2, 8)+number-48);
		}else if(primitive.matches("end")) {
			ir = (short)((byte) 0b10000000*Math.pow(2,8)+0);
		}
		byte[] bytes = new byte[2];
		bytes[0] = (byte)(ir/Math.pow(2,8));
		bytes[1] = (byte)(ir%Math.pow(2,8));
		return bytes;
	}
	public byte[] fileTurnToBits(String fileString){
		fileString = fileString.replaceAll("\n","");
		fileString = fileString.replaceAll(" ","");
		List<Byte> list = new ArrayList<>();
		String threeChar;

		int nowPos=0;
		for(int i = 0;i<fileString.length();i+=3){
			threeChar=fileString.substring(i,i+3);

			byte first = turuToBit(threeChar)[0];
			byte second = turuToBit(threeChar)[1];
			list.add(second);
			list.add(first);
			nowPos+=2;
		}
		byte data[]  = new byte[list.size()];
		for(int i =0;i<list.size();i++){
			data[i]=list.get(i);
		}
		return  data;
	}
}
