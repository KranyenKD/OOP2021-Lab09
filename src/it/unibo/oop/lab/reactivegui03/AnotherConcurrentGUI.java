package it.unibo.oop.lab.reactivegui03;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//import it.unibo.oop.lab.reactivegui02.ConcurrentGUI;
//import it.unibo.oop.lab.reactivegui02.ConcurrentGUI.Agent;

public class AnotherConcurrentGUI extends JFrame{
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JPanel canvas;
    private final JButton up;
    private final JButton down;
    private final JButton stop;
    final JLabel display;
    final Agent ctrl = new Agent();
    
    public AnotherConcurrentGUI() {
        super();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas = new JPanel();
        display = new JLabel();
        canvas.add(display);
        up =new JButton("up");
        up.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub
                ctrl.setUp();
            }
            
        });
        canvas.add(up);
        down =new JButton("down");
        down.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub
                ctrl.setDown();
            }
            
        });
        canvas.add(down);
        stop =new JButton("stop");
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub
                ctrl.stopCount();
            }
            
        });
        canvas.add(stop);
        this.setContentPane(canvas);
        //this.pack();
        new Thread(ctrl).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                
                    // TODO Auto-generated method stub
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    ctrl.stopCount();
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            AnotherConcurrentGUI.this.up.setEnabled(false);
                            AnotherConcurrentGUI.this.down.setEnabled(false);
                            AnotherConcurrentGUI.this.stop.setEnabled(false);
                        }
                        
                    });
                    
                    
                
            }
            
        }).start();
        
        this.setVisible(true);
    }
  
    class Agent implements Runnable {
        
        private volatile boolean stop;
        private volatile int counter;
        private volatile boolean op;
        
        public Agent() {
            stop = true;
            counter = 0;
            op = true;
        }
        
        @Override
        public void run() {
            while(stop) {
                
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                           
                            display.setText(Integer.toString(Agent.this.counter));
                            if(Agent.this.stop == false) {
                                
                            }
                        }
                    });
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(op) {
                 counter++;   
                }
                else {
                    counter--;
                }
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        }
        
        public void setUp() {
            this.op = true;
        }
        
        public void setDown() {
            this.op = false;
        }
        
        public void stopCount() {
            this.stop =false;
            
        }
        
    }
}
