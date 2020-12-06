package com.barracuda.tasks.politeh.practice_04_12_2020.calc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalcWindow extends JFrame {
    public static CalcWindow window;
    final int WIDTH = 300;
    final int HEIGHT = 450;
    public static JLabel display;
    private static JPanel buttonPanel;  // отдельно панель с кнопками.

    private CalcWindow(){
        window = this;
        setTitle("Calculator");
        setSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);//по центру

        display = new JLabel("0", JLabel.RIGHT);
        display.setPreferredSize(new Dimension(this.getWidth(), 70));
        display.setFont(new Font("Arial", Font.BOLD, 36));
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 0,10));
        //менеджер компоновки:
        this.setLayout(new BorderLayout());
        //можно просто добавлять, не в ContentPane
        this.add(display, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(5,4,10,10)); //GridLayout - это менеджер компоновки
        buttonPanel.setBorder(BorderFactory.createMatteBorder(10,10,0,10, Color.LIGHT_GRAY));

        ActionListener clickCommand = new ClickCommand();
        ActionListener clickOperand = new ClickOperand();
        ActionListener CECommand = new CECommand();
        ActionListener resultCommand = new ResultCommand();
        ActionListener backspaceCommand = new BackspaceCommand();

        addButton("7", clickOperand);
        addButton("8", clickOperand);
        addButton("9", clickOperand);
        addButton("+", clickCommand);

        addButton("4", clickOperand);
        addButton("5", clickOperand);
        addButton("6", clickOperand);
        addButton("-", clickCommand);

        addButton("1", clickOperand);
        addButton("2", clickOperand);
        addButton("3", clickOperand);
        addButton("*", clickCommand);

        addButton("00", clickOperand);
        addButton("0", clickOperand);
        addButton(".", clickOperand);
        addButton("/", clickCommand);

        addButton("(", clickOperand);
        addButton(")", clickOperand);
        addButton("\u2190", backspaceCommand);
        addButton("CE", CECommand);

        this.add(buttonPanel, BorderLayout.CENTER);//можно просто добавлять, не в ContentPane

        JButton resultButton = new JButton("=");
        resultButton.setFont((new Font("Arial", Font.PLAIN, 20)));
        resultButton.addActionListener(resultCommand);
        resultButton.setPreferredSize(new Dimension(this.getWidth(), 70));
        resultButton.setBorder(BorderFactory.createMatteBorder(10,10,10,10, Color.ORANGE));
        this.add(resultButton, BorderLayout.SOUTH);//можно просто добавлять, не в ContentPane

        setVisible(true);
    }

    private void addButton(String text, ActionListener al){
        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(al);
        button.setMnemonic((int)text.charAt(0)); //чтобы кнопки управлялись с клаиватуры
        buttonPanel.add(button); //подцепили кнопку в buttonpanel чтобы кнопка не умерла с закрытием метода
    }

    public static CalcWindow getInstance(){
        if (window == null){
            return new CalcWindow();
        }
        window.setVisible(true);
        return window;
    }
}
