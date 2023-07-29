package test_java;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test_CK implements Runnable {
    private static int multi = 1; // Biến static được sử dụng bởi tất cả các thread
    private static final int NUM_THREADS = 5;
    private static final Object lock = new Object(); // Đối tượng lock để đồng bộ hóa

    public void run() {
        // Mỗi thread nhân 2 vào biến multi
        synchronized (lock) {
            multi *= 2;
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(new test_CK());
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Giá trị của biến multi sau khi tất cả các thread đã chạy: " + multi);
    }
}
