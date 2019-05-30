package com.google.firebase.udacity.friendlychat;

public class Device {
    private String name;
    private int index;
    private int GPIOPin;
    private boolean status ;

    public Device(String name, int index, int GPIOPin ,boolean status){

        this.name= name;
        this.GPIOPin= GPIOPin;
        this.index= index;
        this.status = status;

    }
    public Device(){}


    public void setGPIOPin(int GPIOPin) {
        this.GPIOPin = GPIOPin;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGPIOPin() {
        return GPIOPin;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
    public boolean getStatus(){
        return status;
    }
    public void setStatus(boolean status){
        this.status = status;
    }
}
