package com.example.demo;

public class triple<X, Y, Z> {
    public X destination; // next node
    public Y y; // gain
    public Z gain; // float number of gain

    public triple<X, Y, Z> clone(){
        triple<X, Y, Z> newTriple = new triple<>();
        newTriple.destination = this.destination;
        newTriple.y = this.y;
        newTriple.gain = this.gain;
        return newTriple;
    }
}