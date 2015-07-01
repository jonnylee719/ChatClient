/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

/**
 *
 * @author Jonathan
 */
public class ThreadTesting {
    public static void main(String[] args){
        ThreadJob job = new ThreadJob();
        Thread thread = new Thread(job);
        thread.setName("thread1");
        
        
        System.out.println("In main");
        thread.start();
        
        try{
            Thread.sleep(10);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
        
        System.out.println("back to main");
    }
}

class ThreadJob implements Runnable{
    public void run(){
        System.out.println("in thread");
        go();
    }
    private void go(){
        try{
            Thread.sleep(10);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("Thread job completed");
    }
}
