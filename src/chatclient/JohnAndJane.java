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
class BankAccount {
    
    private int bankAccount = 100;
    
    public int getBalance(){
        return bankAccount;
    }
    
    public void withdraw(int amount){
        bankAccount = bankAccount - amount;
    }
}

public class JohnAndJane implements Runnable{
    private BankAccount account = new BankAccount();
    
    public static void main(String[] args){
        JohnAndJane theJob = new JohnAndJane();
        Thread one = new Thread(theJob);
        Thread two = new Thread(theJob);
        
        one.setName("John");
        two.setName("Jane");
        
        one.start();
        two.start();
    }
    public void run(){
        for(int i = 0; i<10; i++){
            makeWithdrawal(10);
            if(account.getBalance()<0){
                System.out.println("Overdrawn");
            }
        }
    }
    
    private synchronized void makeWithdrawal(int amount){
        if(account.getBalance()>= amount){
            System.out.println(Thread.currentThread().getName()+ " is about to withdraw.");
            try{
                System.out.println(Thread.currentThread().getName()+ " is going to sleep now.");
                Thread.sleep(500);
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is awake now.");
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName() + " completes the withdrawal.");
        }
        else {
            System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
        }
        
    }
}