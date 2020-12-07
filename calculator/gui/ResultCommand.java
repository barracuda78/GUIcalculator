package com.barracuda.tasks.politeh.practice_04_12_2020.calc.calculator.gui;

import com.barracuda.tasks.politeh.practice_04_12_2020.calc.calculator.calculations_logic.Calculator;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultCommand implements ActionListener {
    private static StringBuilder expression = new StringBuilder();
    @Override
    public void actionPerformed(ActionEvent e) {

        CalcWindow.display.setText("");
        //Создать экземпляр класса Calculator и вызвать метод калькулятора evaluate, передать в него эту строку.
        Calculator cl = new Calculator();
        //String answer = cl.evaluate("8-(-2-3)*5+(5-6*(-1-9))*2-1");
        System.out.println(expression.toString());
        String answer = cl.evaluate(expression.toString());
        //check to console:
        //System.out.println("Result of the statement is: " + answer);

        //Записать ответ в буфер обмена:
        StringSelection stringSelection = new StringSelection(answer);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        //забрать из него ответ и засунуть сюда:
        CalcWindow.display.setText(answer);
    }

    public static StringBuilder getExpression() {
        return expression;
    }

    public static void setExpression(StringBuilder expression) {
        ResultCommand.expression = expression;
    }
}
