package com.barracuda.tasks.politeh.practice_04_12_2020.calc.colors.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ShowColorCodeCommand implements MouseMotionListener {
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        Color color = e.getComponent().getGraphics().getColor();
//        System.out.println("Цвет: " + color.toString());
        ColorPickerWindow instance = ColorPickerWindow.getInstance();
        JPanel colorSquare = instance.getColorSquare();

        //работает некоррекнте:
//        Robot robot = null;
//        try {
//            robot = new Robot();
//        } catch (AWTException ex) {
//            ex.printStackTrace();
//        }
//        robot.setAutoDelay(200);
//        Point point = colorSquare.getLocationOnScreen();
//        Color color = robot.getPixelColor(point.x + colorSquare.getWidth() / 2, point.y + colorSquare.getHeight() / 2);
        //цвета в 10-м формате:
//        int red = color.getRed();
//        int green = color.getGreen();
//        int blue = color.getBlue();


        //сформировать строку с 16-ричным форматом цвета.
            //цвета в 10-м формате:
        int red = instance.getSlider01().getValue();
        int green = instance.getSlider02().getValue();
        int blue = instance.getSlider03().getValue();
            //цвета в 16-м формате:
        String red16 = Integer.toHexString(red);
        String green16 = Integer.toHexString(green);
        String blue16 = Integer.toHexString(blue);
            //строка с кодом цвета:
        String s = "#" + red16.toUpperCase() + green16.toUpperCase() + blue16.toUpperCase();
        //назначить новый текст и Записать эту строку в переменную toolTipText:
        colorSquare.setToolTipText(s);
        instance.setToolTipText(s);

    }
}
