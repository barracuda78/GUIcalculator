package com.barracuda.tasks.politeh.practice_04_12_2020.calc.colors.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;

public class ColorPickerWindow extends JFrame {

    private static ColorPickerWindow instance;
    private Container contentPane;
    private final int WIDTH = 390;
    private final int HEIGHT = 200;

    private JPanel colorSquare;
    private Color defaultColor;
    public int red;
    public int green;
    public int blue;
    public int alpha;


    private String toolTipText;
    //private static JPanel colorPanel;
    private JPanel sliderPanel;

    private BoundedRangeModel model = new DefaultBoundedRangeModel(125, 0, 0, 255);
    private JSlider slider01 = new JSlider(model);
    private JSlider slider02 = new JSlider(0, 255);
    private JSlider slider03 = new JSlider(0, 255);



    private ColorPickerWindow(){

        if(defaultColor == null){
            red = 125;
            green = 125;
            blue = 125;
            alpha = 255;
            defaultColor = new Color(red, green, blue, alpha);//прозрачность поменять на абсолютно непрозрачную!
        }

        if (toolTipText == null) {
            toolTipText = "";
        }

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

        ////////////////////////COLORSQUARE: //////////////////////////////////
        colorSquare = new JPanel(new FlowLayout());
        this.add(colorSquare, BorderLayout.WEST);
        colorSquare.setPreferredSize(new Dimension(150,150));
        colorSquare.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
        //Если я все правильно понимаю, то тебе может помочь JPanel ,
        // добавляешь ее на JFrame закрашиваешь ее panel.setBackground(Color.red) и потом добавляешь на нее нужный текст!
        MouseMotionListener mouseMotionListener = new ShowColorCodeCommand();
        colorSquare.addMouseMotionListener(mouseMotionListener);
        colorSquare.setBackground(defaultColor);
        colorSquare.setToolTipText(toolTipText);
        colorSquare.setVisible(true);

        ////////////////////////COLORPANEL: //////////////////////////////////

//        colorPanel = new JPanel(new FlowLayout());
//        colorPanel.setBorder(BorderFactory.createMatteBorder(10,10,0,10, Color.LIGHT_GRAY));
//
//        ColorSelectionModel colorSelectionModel = new DefaultColorSelectionModel();
//        JColorChooser colorChooser = new JColorChooser(colorSelectionModel);
//        colorPanel.add(colorChooser);
//        this.add(colorPanel, BorderLayout.CENTER);

        //jButton.addActionListener((a) -> jPanel.setBackground(JColorChooser.showDialog(null,"ColorChooser", Color.RED)));

        /////////////////////////SLIDERS: ////////////////////////////////////

        sliderPanel = new JPanel(new GridLayout(3,1, 10, 10));
        sliderPanel.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.LIGHT_GRAY));
        this.add(sliderPanel, BorderLayout.EAST);


        slider01.setOrientation(JSlider.HORIZONTAL);
        slider01.setMajorTickSpacing(255);
        slider01.setMinorTickSpacing(16);
        slider01.setPaintTicks(true);
        slider01.setPaintTrack(true);
        slider01.setPaintLabels(true);
        slider01.addChangeListener(new SliderCommandRed());
        sliderPanel.add(slider01, BorderLayout.NORTH);


        slider02.setOrientation(JSlider.HORIZONTAL);
        slider02.setMajorTickSpacing(255);
        slider02.setMinorTickSpacing(16);
        slider02.setPaintTicks(true);
        slider02.setPaintTrack(true);
        slider02.setPaintLabels(true);
        slider02.addChangeListener(new SliderCommandGreen());
        sliderPanel.add(slider02, BorderLayout.CENTER);


        slider03.setOrientation(JSlider.HORIZONTAL);
        slider03.setMajorTickSpacing(255);
        slider03.setMinorTickSpacing(16);
        slider03.setPaintTicks(true);
        slider03.setPaintTrack(true);
        slider03.setPaintLabels(true);
        slider03.addChangeListener(new SliderCommandBlue());
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


    public JPanel getColorSquare() {
        return colorSquare;
    }

    public void setColorSquare(JPanel colorSquare) {
        this.colorSquare = colorSquare;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public JPanel getSliderPanel() {
        return sliderPanel;
    }

    public void setSliderPanel(JPanel sliderPanel) {
        this.sliderPanel = sliderPanel;
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    public JSlider getSlider01() {
        return slider01;
    }

    public void setSlider01(JSlider slider01) {
        this.slider01 = slider01;
    }

    public JSlider getSlider02() {
        return slider02;
    }

    public void setSlider02(JSlider slider02) {
        this.slider02 = slider02;
    }

    public JSlider getSlider03() {
        return slider03;
    }

    public void setSlider03(JSlider slider03) {
        this.slider03 = slider03;
    }
}
