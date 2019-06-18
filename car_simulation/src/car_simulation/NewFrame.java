/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_simulation;

import com.sun.javafx.binding.StringFormatter;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.event.*;

import java.text.NumberFormat;

import javax.swing.*;

//import car_simulation.Control;
/**
 *
 * @author lukas
 */
public class NewFrame extends JPanel {

    private double moc = 0;
    private double speed = 0;
    private double rpm = 0;
    private int[] gearTab = {0, 1, 2, 3, 4, 5};
    //private int i = 0;
    private int gear = 0;
    final private int rpmStarMin = 900;
    private boolean engineStatus = false;
    private String engineStatusText = "off";

    //Labels 
    private JLabel speedLabel;
    private JLabel rpmLabel;
    private JLabel gearLabel;
    private JLabel engineStatusTextLabel;

    //string for labels
    private static String speedString = "predkosc";
    private static String rpmString = "RPM";
    private static String gearString = "bieg";
    private static String engineStatusTextString = "Status silnika";

    //field to date entry
    private JFormattedTextField speedField;
    private JFormattedTextField rpmField;
    private JFormattedTextField gearField;
    private JFormattedTextField engineStatusTextField;

    //to format and parse numbers
    private NumberFormat speedFormat;
    private NumberFormat rpmFormat;
    private NumberFormat gearFormat;

    //constructor
    public NewFrame() {
        super(new BorderLayout());

        setUpFormats();
        ListenerForKeys lkeys = new ListenerForKeys();

        //Create labels
        speedLabel = new JLabel(speedString);
        rpmLabel = new JLabel(rpmString);
        gearLabel = new JLabel(gearString);
        engineStatusTextLabel = new JLabel(engineStatusTextString);
        //create text field and set up
        speedField = new JFormattedTextField(speedFormat);
        //speedField.setValue(speed);
        speedField.setValue(speed);
        speedField.setColumns(4);
        speedField.setEditable(false);
        speedField.addKeyListener(lkeys);

        rpmField = new JFormattedTextField(rpmFormat);
        rpmField.setValue(rpm);
        rpmField.setColumns(4);
        rpmField.setEditable(false);
        rpmField.addKeyListener(lkeys);

        gearField = new JFormattedTextField(gearFormat);
        gearField.setValue(gear);
        gearField.setColumns(4);
        gearField.setEditable(false);
        gearField.addKeyListener(lkeys);

        engineStatusTextField = new JFormattedTextField(engineStatusText);
        engineStatusTextField.setValue(engineStatusText);
        engineStatusTextField.setColumns(10);
        engineStatusTextField.setEditable(false);
        engineStatusTextField.addKeyListener(lkeys);

        // conetctin labels to contents
        speedLabel.setLabelFor(speedField);
        rpmLabel.setLabelFor(rpmField);
        gearLabel.setLabelFor(gearField);
        engineStatusTextLabel.setLabelFor(engineStatusTextField);

        //add labels to panel
        JPanel labelPanel = new JPanel(new GridLayout(0, 1));
        labelPanel.add(speedLabel);
        labelPanel.add(rpmLabel);
        labelPanel.add(gearLabel);
        labelPanel.add(engineStatusTextLabel);

        // add field to panel(new panel for  this)
        JPanel fieldPanel = new JPanel(new GridLayout(0, 1));
        fieldPanel.add(speedField);
        fieldPanel.add(rpmField);
        fieldPanel.add(gearField);
        fieldPanel.add(engineStatusTextField);

        //add control to panel
        //JPanel controlPanel = new JPanel(new GridLayout(0, 1));
        //controlPanel.addKeyListener(lkeys);
        //add panels to this.panel
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //add(controlPanel, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.CENTER);
        add(fieldPanel, BorderLayout.LINE_END);

    }

    private static void createGUI() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        JFrame frame = new JFrame("Car_simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width / 4, screenSize.height / 4);
        frame.setLocation(screenSize.height / 4, screenSize.width / 4);

        frame.add(new NewFrame());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            createGUI();

        });
    }

    //formats
    private void setUpFormats() {
        speedFormat = NumberFormat.getNumberInstance();
        rpmFormat = NumberFormat.getNumberInstance();
        gearFormat = NumberFormat.getNumberInstance();

    }

    public class ListenerForKeys implements KeyListener {
        //biegi + z, biegi - x, włąćz silnik s, wyląćz silnik d

        @Override
        public void keyPressed(KeyEvent evt) {

            if (engineStatus == true) {

                if (evt.getKeyCode() == evt.VK_UP && gear != 0) {

                    speed += (1 / (double) gear);
                    rpm = (60 * speed) /  gear;

                    if (speed >= 230) {
                        speed = 230;
                        speedField.setValue(speed);
                    }
                    if (rpm >= 4600) {
                        rpm = 4600;
                        rpmField.setValue(rpm);
                    }
                    speedField.setValue((int) speed);
                    rpmField.setValue((int) rpm);

                    
                }

            }
            //break
            if (evt.getKeyCode() == evt.VK_DOWN) {

                int k;
                if (engineStatus == true) {
                    speed -= 1;
                    rpm = (60 * speed) / (double) gear;
                    if (speed < 0) {
                        speed = 0;
                    }
                    if (rpm < 0)
                    {
                        rpm = 0;
                    }
                    
                    rpmField.setValue((int)rpm);
                    speedField.setValue((int)speed);
                     
                
                }
                


                }

                if (evt.getKeyCode() == evt.VK_Z) {
                    if (engineStatus == true) {
                        try {
                            ++gear;
                            gear = gearTab[gear];
                            gearField.setValue(gearTab[gear]);

                        } catch (ArrayIndexOutOfBoundsException e) {
                            --gear;
                            gear = gearTab[gear];
                            gearField.setValue(gearTab[gear]);
                        }
                    }
                }
                if (evt.getKeyCode() == evt.VK_X) {
                    if (engineStatus == true) {
                        try {
                            --gear;
                            gear = gearTab[gear];
                            gearField.setValue(gear);

                        } catch (ArrayIndexOutOfBoundsException e) {
                            ++gear;
                            gear = gearTab[gear];
                            gearField.setValue(gearTab[gear]);
                        }
                    }
                }
                //start engine
                if (evt.getKeyCode() == evt.VK_S) {
                    engineStatusText = "on";
                    engineStatus = true;
                    engineStatusTextField.setValue("on");

                }
                if (evt.getKeyCode() == evt.VK_D) {
                    engineStatusText = "off";
                    engineStatus = false;
                    engineStatusTextField.setValue("off");
                }

            }

            @Override
            public void keyReleased
            (KeyEvent evt
            
                ) {
            Runnable r = () -> {

                    while (speed > 0 && evt.getKeyCode() != evt.KEY_RELEASED) {
                        try {
                            synchronized (this) {
                                speed -= 1;
                                rpm = (60 * speed) / (double) gear;
                                if(speed < 0){
                                    speed = 0;
                                }
                                if (rpm < 0){
                                    rpm = 0;
                                }
                                speedField.setValue((int) speed);
                                rpmField.setValue((int) rpm);
                                Thread.sleep(2000);
                            }
                        } catch (InterruptedException e) {
                        }
                    }

                };
                Thread t = new Thread(r);
                t.start();

            }

            @Override
            public void keyTyped (KeyEvent evt
            
            
        
    

) {

        }
        
            
            
        
        
        
    }

}
