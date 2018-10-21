package test;

public class TestThread extends Thread{
    private  int i = 0;
    @Override
    public void run() {
        System.out.println(i++);
    }
    public static void main(String[] args){
        TestThread testThread = new TestThread();
        testThread.start();
        testThread.run();
    }
}
