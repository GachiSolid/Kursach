package kursach;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class Figures extends Frame implements Observer, ActionListener{
    private LinkedList LL = new LinkedList();
    private final String [] FIGURES_STRING_LIST = {"Круг","Овал","Квадрат","Прямоугольник","Треугольник"};
    private final String [] SPEED_STRING_LIST = {"1", "5", "10", "20", "40", "60"};
    private static Color color;
    private JFrame f;
    private JButton b;
    private JButton b_select_color;
    private JTextField tf_figure;
    private JComboBox cb_speed;
    private JTextField tf_number_figure;
    private JTextField tf_new_speed;
    private JTextField tf_new_number_figure;
    private JButton b_apply;
    
    Figures () {
        this.addWindowListener(new WindowAdapter2());
        this.setBackground(Color.white);
        f = new JFrame();
        f.setSize (new Dimension(1400,100));
        f.setTitle("Контроль");
        f.setLayout(new GridLayout());
        f.addWindowListener(new WindowAdapter2());
        
        b = new JButton("Пуск");
        b.addActionListener(this);
        f.add(b);
        
        color = Color.black;
        
        b_select_color = new JButton ("Цвет");
        b_select_color.addActionListener(this);
        f.add (b_select_color);
        
        tf_figure = new JTextField ("Укажите тип ФИО");
        tf_figure.setActionCommand("Выбор фигуры");
        tf_figure.addActionListener(this);
        f.add (tf_figure);
        
        cb_speed = new JComboBox(SPEED_STRING_LIST);
        f.add (cb_speed);
        
        tf_new_speed = new JTextField("Новая скорость");
        f.add (tf_new_speed); 
        
        tf_number_figure = new JTextField ("№ ФИО (Макс. - 10)");
        f.add (tf_number_figure);
        
        tf_new_number_figure = new JTextField("Новый № ФИО");
        f.add (tf_new_number_figure);
        
        b_apply = new JButton("Изменить");
        b_apply.setActionCommand("Изменить");
        b_apply.addActionListener(this);
        f.add (b_apply);
        
        f.setVisible(true);
        this.setSize(800,300);
        this.setVisible(true);
        this.setLocation(300, 400);              
    }
    
    static Color getDefaultFiguresColor () {
        return color;
    }
    
    public void update (Observable o, Object arg) {
        repaint();
    }
    
    public void paint (Graphics g) {
        if (!LL.isEmpty()) {
            for (Object LL1 : LL) {
                Figure figure = (Figure) LL1;
                g.setColor(figure.getColorFigure());
                if (figure.getTypeFigure().equals ("Круг")) {
                    g.fillOval (figure.getX(), figure.getY(), 20, 20);                    
                }
                if (figure.getTypeFigure().equals ("Овал")) {
                    g.fillOval (figure.getX(), figure.getY(), 40, 20);                    
                }
                if (figure.getTypeFigure().equals ("Квадрат")) {
                    g.fillRect (figure.getX(), figure.getY(), 20, 20);                    
                }
                if (figure.getTypeFigure().equals ("Прямоугольник")) {
                    g.fillRect (figure.getX(), figure.getY(), 40, 20);                    
                }
                if (figure.getTypeFigure().equals ("Треугольник")) {
                    int[] arrayX = {figure.x, figure.x + 10, figure.x + 20}; 
                    int[] arrayY = {figure.y + 20 , figure.y, figure.y + 20}; 
                    g.fillPolygon(arrayX, arrayY, 3);
                }
                g.drawString (figure.getTextFigure(), figure.getX() + 10, figure.getY() + 30);
            }
        }
    }
    
    public void actionPerformed (ActionEvent e) {
        String str = e.getActionCommand();
        
        if (str.equals("Пуск")) {
            if (checkInput()) {
                Figure figure = new Figure(
                        Figures.color,
                        tf_number_figure.getText(),
                        tf_figure.getText(),
                        Integer.parseInt((String)cb_speed.getSelectedItem()),
                        ++Kursach.count);
                LL.add(figure);
                figure.addObserver(this);
            }
        }
        
        
        if (str.equals ("Изменить")) {
            if (checkInput()) {
                if (!LL.isEmpty()) {
                    for (Object LL1 : LL) {
                        Figure figure = (Figure) LL1;
                        if (figure.getTextFigure().equals (tf_number_figure.getText())){
                            figure.setSpeed (Integer.parseInt((String)tf_new_speed.getText()));
                            figure.setTextFigure (tf_new_number_figure.getText());
                            }
                        }
                    }
                }
    }
        
        if (str.equals("Цвет")) {
            Figures.color = JColorChooser.showDialog(Figures.this,"Выбор цвета фигуры", Color.black);
        }
        
        repaint();
        
}
    
    Dimension getSizeShowFrame () {
        return this.getSize();
    }
    
    Boolean checkInput () {
        Boolean res = false;
        for (int i = 0; i < FIGURES_STRING_LIST.length; i++)
            if (FIGURES_STRING_LIST [i].equals (tf_figure.getText()))
                res = true;
                if (Kursach.count>=10) {
                    JOptionPane.showMessageDialog(null, "Вы превысили количество фигур");
                    res = false;
                }
        return res;
    }
}
