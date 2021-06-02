package com.example.shoesfinder;

public class comment {
    String comm;
    int number;

    public comment(String comm, int number){
        this.comm = comm;
        this.number = number;
    }

    public void setComm(String s){
        this.comm = s;
    }

    public String getComm(){
        return comm;
    }

    public void setNumber(int n){
        this.number = n;
    }

    public int getNumber(){
        return number;
    }
}
