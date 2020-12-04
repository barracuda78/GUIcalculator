package com.barracuda.tasks.politeh.practice_04_12_2020.calc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackspaceCommand implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        //взять значение из набираемой строки, удалить один символ с конца, записать обратно.
        StringBuilder exp = ResultCommand.getExpression();
        //что бы не было StringIndexOutOfBoundsException - проверка, что еще есть символы для стирания:
        if(exp.length()>0)
            exp.deleteCharAt(exp.length()-1);
        ResultCommand.setExpression(exp);

        //вывесли в окно новую строку с удаленным символом.
        CalcWindow.display.setText(exp.toString());
    }
}
