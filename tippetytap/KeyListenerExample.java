package tippetytap;

import tippetytap.Timing;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class KeyListenerExample extends Frame implements KeyListener{
    Label I;
    TextArea area;

    Timing tim = new Timing();

    boolean pressed = false;

    KeyListenerExample(){
        I = new Label();
        I.setBounds(20, 50, 100, 20);
        area = new TextArea();
        area.setBounds(20, 80, 300, 300);
        area.addKeyListener(this);
        add(I);
        add(area);
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }
    
    public void keyPressed(KeyEvent e){

        if (!pressed){
            pressed = true;
            Date date = new Date();
            long time = date.getTime();
            I.setText("Key Pressed");
            System.out.println(time);
        }
        
    }

    public void keyReleased(KeyEvent e){
        if (pressed){
            Date date = new Date();
            long time = date.getTime();
            I.setText("Key Released");
            System.out.println(time);
            pressed = false;
        }
        
    } 

    public void keyTyped(KeyEvent e){
        //char input;
        //input = text inputted somehow
        //I.setText("Key Typed");
        //System.out.println(input);
        //String password += password or something like that to add char to password
    }
    
    public boolean passwordValid(){
        //boolean flag = true;
        //compile the times down and compare with the stored times of the 
        //double sum;
        //start loop
          //double difference = math.abs(stored value - measured value)
          //sum += sum;
        //end loop
        //double scaled = sum/deviation
        //if(password characters are correct)
            //if(mathetical range is not met)
                //flag = false;
            //else
                //flag = true;
        //return flag;
    }

    public static void main(String[] args){
        new KeyListenerExample();
    }
}
