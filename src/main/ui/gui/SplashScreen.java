package ui.gui;

import javax.swing.*;
import java.awt.*;

import static java.awt.Image.SCALE_DEFAULT;

// Represents splash screen with image, progress bar, and status message
public class SplashScreen {
    JFrame frame;
    JLabel image;
    JProgressBar progressBar;
    JLabel statusMessage;

    // EFFECTS: constructs Splash Screen with visible frame, image, progress bar, and status message
    public SplashScreen() {
        createFrame();
        addImage();
        frame.setVisible(true);
        addProgressBar();
        addStatusMessage();
        runProgressBar();
    }

    // MODIFIES: this
    // EFFECTS: creates frame and sets its title, layout, size, location and background colour
    public void createFrame() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);
    }


    // MODIFIES: this
    // EFFECTS: adds image to frame and scales it and sets its bounds
    public void addImage() {
        image = new JLabel();
        image.setIcon(new ImageIcon(new ImageIcon("src/main/ui/gui/logo.png").getImage().getScaledInstance(400,
                400, SCALE_DEFAULT)));
        image.setBounds(50,25,400,400);
        frame.add(image);
    }

    // MODIFIES: this
    // EFFECTS: adds status message to frame and sets its bounds and font
    public void addStatusMessage() {
        statusMessage = new JLabel();
        statusMessage.setBounds(200, 450, 200, 40);
        statusMessage.setFont(new Font("arial", Font.BOLD, 15));
        frame.add(statusMessage);
    }

    // MODIFIES: this
    // EFFECTS: adds progress bar to frame and sets its bounds, border, font, progress string, background colour,
    // foreground colour, and value
    public void addProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setBounds(50, 425, 400, 25);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        frame.add(progressBar);
    }

    // MODIFIES: this
    // EFFECTS: runs progress bar by updating its value and status message to display the percentage loaded
    public void runProgressBar() {
        int i = 0;

        while (i <= 100) {
            try {
                Thread.sleep(50);
                progressBar.setValue(i);
                statusMessage.setText("LOADING..." + Integer.toString(i) + "%");
                i++;
                if (i == 100) {
                    frame.dispose();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
