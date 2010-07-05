package org.z.posarma;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class go {
    public static void main(String[] args) {

        
       
        JFrame window = new JFrame("ARMA UNIT GENERATOR");
        window.setContentPane( new JScrollPane(new Ecran()));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();
        window.setLocation(100, 100);
        window.setSize(800, 500);
        
    }

 
}

