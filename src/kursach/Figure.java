package kursach;

import java.awt.*;
import java.util.*;

class Figure extends Observable implements Runnable{
    private Thread thread;
    private boolean xplus;
    private boolean yplus;
    int x, dx;
    int y, dy;
    Color colorFigure;
    private String textFigure;
    private String type;
    private int speed;
    private int number;
    
    Figure (Color color, String text, String type, int speed, int number) {
        xplus = true;
        yplus = true;
        x = 0;
        y = 20;
        Random r = new Random();
        dx = (int) Math.round(r.nextDouble()*20.0);
        dx = dx == 0 ? 1 : dx;
        dy = (int) Math.round(r.nextDouble()*20.0);
        dy = dy == 0 ? 1 : dy;
        colorFigure = color;
        textFigure = text;
        this.type = type;
        this.speed = speed;
        this.number = number;
        thread = new Thread (this);
        thread.start();
    }
    
    int getX () {return x;}
    int getY() {return y;}
    
    Color getColorFigure() {return colorFigure;}
    void setColorFigure (Color cc) {this.colorFigure = cc;}
    String getTextFigure () {return textFigure;}
    void setTextFigure (String textFigure) {this.textFigure = textFigure;}
    String getTypeFigure () {return type;}
    void setSpeed (int speed) {this.speed = speed;}
    
    public void run () {
        Dimension d;
        while (true) {
            d = Kursach.figures.getSizeShowFrame();
            if (x>=(d.width - 20)) xplus = false;
            if (x<= -1) xplus = true;
            if (y >= (d.height - 25)) yplus = false;
            if (y<=19) yplus = true;
            if (xplus) x+=dx; else x-=dx;
            if (yplus) y+=dy; else y-=dy;
            setChanged ();
            notifyObservers(this);
            try {
                Thread.sleep (300 / speed);
            }
            catch (InterruptedException e) {}
        }
    }
}
