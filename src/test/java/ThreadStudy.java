/**
 * Created by Smith on 2017/4/10.
 */
public class ThreadStudy {
    public static void main(String[] args) {
        Handle handle = new Handle();
        Thread t1 = new Thread(handle, "窗口1");
        Thread t2 = new Thread(handle, "窗口2");
        Thread t3 = new Thread(handle, "窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}

class Handle implements Runnable {
    private int ticket = 10;
    Object object = new Object();

    @Override
    public void run() {
        while (ticket > 0) {
            sold();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sold() {
        test();
    }

    private void test() {
        if (ticket > 0) {
            ticket--;
            System.out.println(Thread.currentThread().getName()+":"+ticket);
        }
    }
}
