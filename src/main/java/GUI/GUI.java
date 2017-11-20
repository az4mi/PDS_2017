package main.java.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by mi5ho on 20.11.2017.
 */
public class GUI extends JFrame {

    private JPanel mainPanel;
    private JPanel controlPanel;
    private JPanel outputPanel;
    private JTabbedPane tabPanel;
    private JPanel tabItem1;
    private JPanel tabItem2;
    private JButton pridanieVoznaDoSystemuButton;
    private JButton vyhladanieNajblizsiehoVolnehoVoznaButton;
    private JButton zmenaPolohyVoznaVButton;
    private JButton vyradenieVoznaZVlakuButton;
    private JButton zaradenieVoznaDoVlakuButton;
    private JButton vyradenieVoznaZPrevadzkyButton;

    private JMenuBar menuBar;

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    private GUI() {
        init_MainFrame();
        init_Menu();
    }

    public void init_MainFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(1000,600));
        this.setLocation(200,200);
    }

    public void init_Menu() {
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(80,63,65));
        menuBar.setForeground(new Color(200,200,10));
        this.setJMenuBar(menuBar);



        JMenu app = new JMenu("App");
        app.setForeground(new Color(200,200,10));



        JMenuItem newApp = new JMenuItem("New");

        app.add(newApp);



        menuBar.add(app);

    }

}
