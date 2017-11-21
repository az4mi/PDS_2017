package main.java.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton btn1;
    private JButton btn6;
    private JButton btn5;
    private JButton btn4;
    private JButton btn3;
    private JButton btn2;
    private JTextPane textArea;
    private JButton btn7;
    private JButton btn15;
    private JButton btn14;
    private JButton btn13;
    private JButton btn12;
    private JButton btn11;
    private JButton btn9;
    private JButton btn8;
    private JButton btn10;
    private JPanel tabItem3;
    private JPanel tabItem4;
    private JButton btn16;
    private JButton btn18;
    private JButton btn17;
    private JButton btn19;
    private JScrollBar scrollBar1;

    private JMenuBar menuBar;

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    private GUI() {
        init_MainFrame();
        init_Menu();
        init_actionListeners();
    }


    /*------------------------------------------------------------------------------------------------------------------
     * Initialization methods
     */

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

    public void init_actionListeners() {

        /*--------------------------------------------------------------------------------------------------------------
         * Method listeners
         */

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                method_1();
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                method_2();
            }
        });

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                method_3();
            }
        });

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                method_4();
            }
        });

        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                method_5();
            }
        });

        btn6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                method_6();
            }
        });


        /*--------------------------------------------------------------------------------------------------------------
         * Output method listeners
         */

        btn7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_1();
            }
        });

        btn8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_2();
            }
        });

        btn9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_3();
            }
        });

        btn10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_4();
            }
        });

        btn11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_5();
            }
        });

        btn12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_6();
            }
        });

        btn13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_7();
            }
        });

        btn14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_8();
            }
        });

        btn15.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_9();
            }
        });


        /*--------------------------------------------------------------------------------------------------------------
         * Display method listeners
         */

        btn16.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show_1();
            }
        });

        btn17.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show_2();
            }
        });

        btn18.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show_3();
            }
        });

        btn19.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show_4();
            }
        });


    }

    /*------------------------------------------------------------------------------------------------------------------
     * Methods
     */

    /**
     * Pridanie vozna do systemu a urcenie jeho polohy
     */
    public void method_1() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Vyradenie vozna z prevadzky
     */
    public void method_2() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Zaradenie vozna do vlaku
     */
    public void method_3() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Vyradenie vozna z vlaku
     */
    public void method_4() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Zmena polohy vozna v stanici (kolaj -> kolaj)
     */
    public void method_5() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Vyhladanie najblizsieho volneho vozna podla kriterii
     */
    public void method_6() {
        textArea.setText("Zatial nie je implementovane");
    }


    /*------------------------------------------------------------------------------------------------------------------
     * Output methods
     */

    /**
     * Zobrazenie aktualnej polohy voznov podla zadanych kriterii
     */
    public void output_1() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Zoznam voznov v konkretnej zeleznicnej stanici v zadanom case podla zadanych kriterii
     */
    public void output_2() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Zoznam voznov vo vlakoch podla roznych kriterii
     */
    public void output_3() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Vyhladanie aktualnej polohy konkretneho vozna
     */
    public void output_4() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * vyhladanie konkretneho vozna vratane historie jeho vyskytu za dane obdobie
     */
    public void output_5() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * vyhladanie skupin voznov podla roznych kriterii
     */
    public void output_6() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * statistika o voznoch vo vlaku vratane historie
     */
    public void output_7() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * statistika o voznoch v staniciach vratane historie
     */
    public void output_8() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * statistika o pracovnikoch a ich vykonoch z pohladu zadavania dat
     */
    public void output_9() {
        textArea.setText("Zatial nie je implementovane");
    }


    /*------------------------------------------------------------------------------------------------------------------
     * Display methods
     */

    /**
     * Zobraz pouzivatelov
     */
    public void show_1() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Zobraz stanice
     */
    public void show_2() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Zobraz typy voznov
     */
    public void show_3() {
        textArea.setText("Zatial nie je implementovane");
    }

    /**
     * Zobraz vlaky
     */
    public void show_4() {
        textArea.setText("Zatial nie je implementovane");
    }

}
