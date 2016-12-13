package com.example.sm.problem3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<CustomerThread> list = new ArrayList<CustomerThread>();
        Manager manager = new Manager();

        for(int i = 0 ; i < 10 ; i++){
            Customer customer = new Customer("Customer" + i);
            CustomerThread ct = new CustomerThread(customer);
            list.add(ct);
            manager.add_customer(customer);
            ct.start();
        }


        for(CustomerThread ct : list){
            try {
                // need something here
                if(ct.isAlive()){
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) { }
        }

        manager.sort();

        MyBaseAdapter adapter = new MyBaseAdapter(this, manager.list);
        ListView listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);


    }
}

class CustomerThread extends Thread{

    Customer customer;

    CustomerThread(Customer customer){
        this.customer = customer;
    }
    // need something here


    @Override
    public void run() {
        super.run();
        for(int i=0; i<10; i++){
            this.customer.work();
        }
    }
}

abstract class Person{

    static int money = 100000;
    int spent_money = 0;
    abstract void work();

}


class Customer extends Person{

    String name;
    Customer(String name){
        this.name = name;
    }

    @Override
    void work() {
        double spendDouble = Math.random()*1000;
        int spend;
        if(spendDouble%1 >= 0.5){
            spendDouble+=1;
        }
        spend = (int)spendDouble;
        this.spent_money+=spend;
        money-=spend;
    }

    // need something here
}


class Manager extends Person{
    ArrayList <Customer> list = new ArrayList<Customer>();

    void add_customer(Customer customer) {
        list.add(customer);
    }

    void sort(){ // 직접 소팅 알고리즘을 이용하여 코딩해야함. 자바 기본 정렬 메소드 이용시 감점

        // need something here

        for(int i=0; i<list.size(); i++){
            for(int j=i+1; j<list.size(); j++){
                Customer tmp1 =list.get(i);
                Customer tmp2 =list.get(j);
                if(tmp1.spent_money<tmp2.spent_money){
                    Customer tmp3 = tmp1;
                    list.set(i, tmp2);
                    list.set(j, tmp3);
                }
            }
        }
    }

    @Override
    void work() {
        sort();
    }
}

// need something here

