package com.barracuda.tasks.politeh.practice_04_12_2020.calc.calculator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickCommand implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
                //реализовать самому
        String command = e.getActionCommand();

        //взять значение из набираемой строки, добавить туда введенные символы, записать обратно.
        StringBuilder exp = ResultCommand.getExpression();
        exp.append(command);
        ResultCommand.setExpression(exp);

        CalcWindow.display.setText(CalcWindow.display.getText() + command);
    }
}
