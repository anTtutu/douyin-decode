package com.example.demo;

public class Test {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
 
        Ticket ticket=new Ticket();
        // 　实例化几个抢票用户
        Thread mary = new Thread(ticket, "玛丽");
 
        Thread jack = new Thread(ticket, "杰克");
 
        mary.start();
 
        jack.start();
 
    }
}