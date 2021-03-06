package com.helkaerea.tdd;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame{
    private static final long serialVersionUID = 1L;
    public static final String SNIPER_STATUS_NAME = "sniper status";
    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);

    public MainWindow() {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        add(sniperStatus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        //pack();
        setVisible(true);
    }

    private JLabel createLabel(String initialText) {
      JLabel result = new JLabel(initialText);
      result.setName(SNIPER_STATUS_NAME);
      result.setBorder(new LineBorder(Color.black));
    return result;
    }

    public void showStatus(String status) {
       sniperStatus.setText(status);
        
    }
}
