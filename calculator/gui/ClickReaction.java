package com.barracuda.tasks.politeh.practice_04_12_2020.calc.calculator.gui;

import com.barracuda.tasks.politeh.practice_04_12_2020.calc.colors.gui.ColorPickerWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickReaction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        switch (actionCommand){
            case "Calculator":
                System.out.println("Нажат калькулятор");
                SwingUtilities.invokeLater(new Runnable(){

                    @Override
                    public void run() {
                        CalcWindow.getInstance();
                    }
                });
                break;
            case "Color Picker":
                System.out.println("Нажат Color Picker");
                SwingUtilities.invokeLater(new Runnable(){

                    @Override
                    public void run() {
                        ColorPickerWindow.getInstance();
                    }
                });
                break;
        }
    }
}
