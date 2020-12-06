package com.barracuda.tasks.politeh.practice_04_12_2020.calc.gui;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import java.awt.*;

public class ColorPickerWindow extends JFrame {

    private static ColorPickerWindow instance;
    private Container contentPane;
    private final int WIDTH = 900;
    private final int HEIGHT = 400;
    private static JPanel colorPanel;
    private static JPanel sliderPanel;


    private ColorPickerWindow(){
        init();
    }

    private void init(){
        contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        setTitle("ColorPicker");
        setSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //setLocationRelativeTo(null);//по центру
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension resolution = t.getScreenSize();
        Point upperLeftCorner = new Point((int)((resolution.getWidth())/3) - WIDTH/2, (int)(resolution.getHeight())/3);
        setLocation(upperLeftCorner);

        ////////////////////////COLORPANEL: //////////////////////////////////
        //colorPanel = new JPanel(new GridLayout(1,2,10,10)); //GridLayout - это менеджер компоновки
        colorPanel = new JPanel(new FlowLayout());
        colorPanel.setBorder(BorderFactory.createMatteBorder(10,10,0,10, Color.LIGHT_GRAY));

        ColorSelectionModel colorSelectionModel = new DefaultColorSelectionModel();
        JColorChooser colorChooser = new JColorChooser(colorSelectionModel);
        colorPanel.add(colorChooser);
        this.add(colorPanel, BorderLayout.CENTER);

        /////////////////////////SLIDERS: ////////////////////////////////////
        BoundedRangeModel model = new DefaultBoundedRangeModel(128, 0, 0, 255);
        sliderPanel = new JPanel(new GridLayout(3,1, 10, 10));
        sliderPanel.setBorder(BorderFactory.createMatteBorder(10,10,0,10, Color.LIGHT_GRAY));
        this.add(sliderPanel, BorderLayout.CENTER);

        JSlider slider01 = new JSlider(model);
        slider01.setOrientation(JSlider.HORIZONTAL);
        slider01.setMajorTickSpacing(255);
        slider01.setMinorTickSpacing(16);
        slider01.setPaintTicks(true);
        slider01.setPaintTrack(true);
        slider01.setPaintLabels(true);
        sliderPanel.add(slider01, BorderLayout.NORTH);

        JSlider slider02 = new JSlider(0, 255);
        slider02.setOrientation(JSlider.HORIZONTAL);
        slider02.setMajorTickSpacing(255);
        slider02.setMinorTickSpacing(16);
        slider02.setPaintTicks(true);
        slider02.setPaintTrack(true);
        slider02.setPaintLabels(true);
        sliderPanel.add(slider02, BorderLayout.CENTER);

        JSlider slider03 = new JSlider(0, 255);
        slider03.setOrientation(JSlider.HORIZONTAL);
        slider03.setMajorTickSpacing(255);
        slider03.setMinorTickSpacing(16);
        slider03.setPaintTicks(true);
        slider03.setPaintTrack(true);
        slider03.setPaintLabels(true);
        sliderPanel.add(slider03, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static ColorPickerWindow getInstance(){
        ColorPickerWindow localInctance = instance;
        if(localInctance == null){
            synchronized (ColorPickerWindow.class){
                localInctance = instance;
                if(localInctance == null){
                    instance = localInctance = new ColorPickerWindow();
                }
            }
        }
        instance.setVisible(true);
        return instance;
    }

}
