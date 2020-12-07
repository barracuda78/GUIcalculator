package com.barracuda.tasks.politeh.practice_04_12_2020.calc.colors.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderCommandRed implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
        ColorPickerWindow instance = ColorPickerWindow.getInstance();

        JPanel colorSquare = instance.getColorSquare();
        Color oldColor = instance.getDefaultColor();
        int newRedValue = ((JSlider)e.getSource()).getValue();
        int oldGreenValue =  oldColor.getGreen();
        int oldBlueValue = oldColor.getBlue();
        int oldAlpha = oldColor.getAlpha();

        Color newColor = new Color(newRedValue, oldGreenValue, oldBlueValue, oldAlpha);
        //instance.setDefaultColor(newColor);
        colorSquare.setBackground(newColor);
        //instance.setColorSquare(new JPanel(new FlowLayout()));
        //System.out.println("slider 01 ChangeListener...value = " + newColor.toString()); //----------->закомментить. Это для теста.
    }
}
