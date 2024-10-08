package com.yang.multi.thread.demo.github.sync005;
/**
 * synchronized 锁的重入
 * synchronized是不可以继承的
 * @author huanchu
 *
 */
public class SyncDubbo2 {
	static class Main{
		public int i = 10;
		public synchronized void operationSup(){
			try {
				i--;
				System.out.println("Main print i = "+i);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class Sub extends Main{
		public synchronized void operationSub(){
			try {
				while(i > 0){
					i--;
					System.out.println("Sub print i = " + i);
					Thread.sleep(100);
					this.operationSup();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Sub sub = new Sub();
				sub.operationSub();
			}
		}).start();
	}
}
