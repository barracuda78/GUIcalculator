package com.barracuda.tasks.politeh.practice_04_12_2020.calc.calculator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CECommand implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //взять значение из набираемой строки, добавить туда введенные символы, записать обратно.

        //Это сброс, поэтому в набираемую строку записываем пустой стрингбилдер.
        ResultCommand.setExpression(new StringBuilder());
        CalcWindow.display.setText("0");
    }
}
