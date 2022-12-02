package com.example.tippetytap;

import android.util.Log;

import java.io.Serializable;
import java.util.Vector;

class Timing implements Serializable {
    String username = "";
    String password = "";
    Vector<Character> chars = new Vector<Character>();
    Vector<Vector<Long>> pressTimes = new Vector<Vector<Long>>();

    Timing(String name){
        username = name;
    }

    public double compare(Timing comparison){
        // Uses the Manhattan distance to compare two Timing objects.

        Vector<Long> compareTimes = comparison.calcTimes(0);
        double distanceSum = 0;


        for (int i = 0; i < this.pressTimes.size(); i++){
            Vector<Long> thisTimes = this.calcTimes(i);
            distanceSum += this.manhattanScaled(thisTimes, compareTimes);
        }

        return distanceSum / this.pressTimes.size();

    }

    public Vector<Long> calcTimes(int idx){
        // Calculates times between each key press and returns the vector listing these times.

        Vector<Long> pressTime = new Vector<Long>(pressTimes.get(idx));
        Vector<Long> times = new Vector<Long>();
        long oldTime = 0;
        for (int i = 0; i < pressTime.size(); i++){
            if (i != 0) {
                times.add(pressTime.get(i) - oldTime);
            }
            oldTime = pressTime.get(i);

        }

        return new Vector<Long>(times);
    }

    public double manhattanScaled(Vector<Long> x, Vector<Long> y){
        assert x.size() == y.size();
        double sum = 0;
        for (int i = 0; i < x.size(); i++){
            sum += Math.abs(x.get(i) - y.get(i));
        }

        double absoluteDeviation = (this.meanAbsDeviation(x) + this.meanAbsDeviation(y)) / 2;

        return sum / absoluteDeviation;

    }

    private double mean(Vector<Long> x){
        double sum = 0;
        for (int i = 0; i < x.size(); i++){
            sum += x.get(i);
        }

        return sum / x.size();
    }

    private double meanAbsDeviation(Vector<Long> x){
        double absSum = 0;

        for (int i = 0; i < x.size(); i++){
            absSum += Math.abs(x.get(i) - this.mean(x));
        }

        return absSum / x.size();
    }

    public void setChars(Vector<Character> chs){
        chars = new Vector<Character>(chs);
    }

    public Vector<Character> getChars(){
        return new Vector<Character>(chars);
    }

    public void pushTimeSequence(Vector<Long> time){
        pressTimes.add(new Vector<Long>(time));
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String name){
        username = name;
        return;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String pass){
        password = pass;
    }

    public String summary(){
        String summ = "Timing Object Summary\n";
        summ += "Username: " + username + "\n";
        summ += "Password: " + password + "\n";
        summ += "chars vector: " + chars.toString() + "\n";
        summ += "pressTimes vector: " + pressTimes.toString() + "\n";

        return summ;
    }
}




