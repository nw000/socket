package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dxjf on 16/10/11.
 */
public class ThreadTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 300, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<Runnable>(5));
        for (int i = 0; i < 15; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println("核心线程" + threadPoolExecutor.getCorePoolSize() + "*******已经执行的任务数量" + threadPoolExecutor.getCompletedTaskCount() + "******队列中等待执行的任务数量" + threadPoolExecutor.getQueue().size());

        }

    }

    public static class TestRunnable implements Runnable {
        private int i;

        public TestRunnable(int i) {
            this.i = i;

        }

        @Override
        public void run() {
            System.out.println("正在执行第" + i + "个任务");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("第" + i + "个任务执行完毕");
        }
    }
}
