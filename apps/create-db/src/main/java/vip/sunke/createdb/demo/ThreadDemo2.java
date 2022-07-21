package vip.sunke.createdb.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadDemo2 {
    public static void multiThead(int n, CountDownLatch countDownLatch) throws InterruptedException {

        String[] tasks = {"发短信完毕", "发微信完毕", "发QQ完毕"};
        int[] executeTimes = new int[]{2, 5, 1};
        ExecutorService executorService = Executors.newFixedThreadPool(n);

        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(executeTimes[finalI]);
                    System.out.println(tasks[finalI]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        int n = 3;
        CountDownLatch countDownLatch = new CountDownLatch(n);

        multiThead(n, countDownLatch);
        countDownLatch.await();
        System.out.println("所有消息都发送完毕了，继续执行主线程任务。\n耗时ms：" + (System.currentTimeMillis() - start));

        multiThead(n, countDownLatch);
    }
}
