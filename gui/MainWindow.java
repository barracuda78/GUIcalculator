package com.barracuda.tasks.politeh.practice_04_12_2020.calc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.Container;
import javax.swing.JButton;

public class MainWindow extends JFrame {
    Container contentPane;
    public static MainWindow mainWindow = null;
    final int WIDTH = 300;
    final int HEIGHT = 200;

    private MainWindow(){

        setTitle("Выбор приложения");
        setSize(WIDTH, HEIGHT);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);//по центру

        JButton toCalculator = new JButton("Calculator");
        JButton toColorPicker = new JButton("Color Picker");

        ActionListener actionListener = new ClickReaction();
        toCalculator.addActionListener(actionListener);
        toColorPicker.addActionListener(actionListener);

        //можно просто добавлять, не в ContentPane
        //contentPane = getContentPane();
        add(toCalculator, JButton.CENTER);
        add(toColorPicker, JButton.CENTER);
        //менеджер компоновки:
        setLayout(new FlowLayout());

        mainWindow = this;
        setVisible(true);
    }

    public static MainWindow getInstance(){
        if(mainWindow == null){
            return new MainWindow();
        }
        mainWindow.setVisible(true);
        return mainWindow;
    }
}
