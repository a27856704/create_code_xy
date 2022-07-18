package vip.sunke.createdb.demo;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private static CountDownLatch countDownLatsh = new CountDownLatch(5);

    private static class Player implements Runnable{
        private Integer index;
        public Player(Integer index){
            this.index = index;
        }

        @Override
        public void run() {
            System.out.println("玩家"+index+"准备完成");
            countDownLatsh.countDown();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        for(int i = 0; i < 5; i++){
            Player player = new Player(i);
            Thread thread = new Thread(player);
            thread.start();
        }
        countDownLatsh.await();

        System.out.println("玩家准备完毕，开始游戏");
    }
}
