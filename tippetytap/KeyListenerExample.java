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
            System.out.println(time);
            pressed = false;
        }
        
    } 

    public void keyTyped(KeyEvent e){
        // I.setText("Key Typed");
    }

    public static void main(String[] args){
        new KeyListenerExample();
    }
}
