package com.barracuda.tasks.politeh.practice_04_12_2020.calc.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickOperand implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {


        String command = e.getActionCommand();

        //взять значение из набираемой строки, добавить туда введенные символы, записать обратно.
        StringBuilder exp = ResultCommand.getExpression();
        exp.append(command);
        ResultCommand.setExpression(exp);

        String oldValue = CalcWindow.display.getText();
        if(oldValue != null && oldValue.equals("0")){
            CalcWindow.display.setText(command);
        }else{
            CalcWindow.display.setText(oldValue + command);
        }
    }
}
