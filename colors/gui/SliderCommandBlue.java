package com.barracuda.tasks.politeh.practice_04_12_2020.calc.colors.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderCommandBlue implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
        ColorPickerWindow instance = ColorPickerWindow.getInstance();

        JPanel colorSquare = instance.getColorSquare();
        Color oldColor = instance.getDefaultColor();
        int oldRedValue =  oldColor.getRed();
        int oldGreenValue = oldColor.getGreen();
        int newBlueValue = ((JSlider)e.getSource()).getValue();
        int oldAlpha = oldColor.getAlpha();

        Color newColor = new Color(oldRedValue, oldGreenValue, newBlueValue, oldAlpha);
        instance.setDefaultColor(newColor);
        colorSquare.setBackground(newColor);
        //System.out.println("slider 01 ChangeListener...value = " + newColor.toString()); //----------->закомментить. Это для теста.
    }
}