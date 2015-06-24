package ch_without_gui;

import java.awt.*;
import java.awt.event.*;
import java.util.* ;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Animation extends JPanel {
    
    private static int NUM_OF_ITEMS = 0;
    private static final int DIM_W = 400;
    private static final int DIM_H = 400;
    
    private JButton startButton;
    private Timer timer = null;
    private JButton Regenerate;
    private TextField tf;
    private JButton save;
    private JButton clear;
    
    private ArrayList<tPoint2> points;
    private tPoint2 currentNdomPt ;
    private tPoint2 TempNdomPt ;
    private int currentNdomIndex = 0 ;
    private int TempNdomIndex ;
    private int steps = 0 ;
    private int i ;
    private int j ;
    
    private boolean isDone = false ;
    
    // constructor
    public Animation() {
    	initPts();
        timer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isNdomFindDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    if(steps==0){
                       initNdomPts() ;
                    }else{
                       findOnlyNextPt();
                    }
                }
                repaint();
            }
        });
        //start
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        //reset
        Regenerate = new JButton("Regenerate");
        Regenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initPts();
                steps = 0 ;
                currentNdomIndex = 0 ;
                isDone = false ;
                repaint();
                startButton.setEnabled(true);
            }
        });
        //enter number of points
        tf = new TextField();
        save = new JButton("Save");
        save.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			NUM_OF_ITEMS = Integer.parseInt(tf.getText());
    		}
        });
        
        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			NUM_OF_ITEMS = 0;
                initPts();
                steps = 0 ;
                currentNdomIndex = 0 ;
                isDone = false ;
                repaint();
                startButton.setEnabled(true);
    		}
        });
        //add
        add(startButton);
        add(Regenerate);
        add(tf);
        add(save);
        add(clear);
        addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent e){
        		addPoint(new tPoint2(e.getX(),e.getY()));
        		NUM_OF_ITEMS ++;
         		repaint();
        	}
        });
    }
    
	public void addPoint(tPoint2 p){
		points.add(p);
	}
	
    public boolean isNdomFindDone() {
        return isDone == true;
    }

    //add textField to set # of points
    public void initPts(){//add textField
        Random r = new Random();
        points = new ArrayList<>(NUM_OF_ITEMS);
        for (int i=0; i<NUM_OF_ITEMS; i++){
            int x = Math.abs(r.nextInt()) % (DIM_W-100) + 50;
            int y = Math.abs(r.nextInt()) % (DIM_H-100) + 50;
            points.add(new tPoint2 (x,y));
        }
    }

    public void drawItem(Graphics g, tPoint2 pt){
    	int r;
        if(pt.isNdom){
            g.setColor(Color.RED);
            r = 8;
        }else{
            g.setColor(Color.BLUE);
            r = 5;
        }
        //g2d.drawLine(x, y, x, y);
        g.fillOval((int)pt.getX(),(int)pt.getY(), r, r) ;
    }
    
    public void initNdomPts() {
        i = 0;
        while(i < NUM_OF_ITEMS){
            if(points.get(i).IsGreaterXY(points.get(currentNdomIndex))){
                currentNdomIndex = i ;
            }
            i++ ;
            steps++ ;
        }
        points.get(currentNdomIndex).isNdom = true ;
    }
    
    
    public void findOnlyNextPt() {
        currentNdomPt = points.get(currentNdomIndex) ;
        TempNdomIndex = 0 ;
        isDone = true ;
        i = 0 ;
        while(i < NUM_OF_ITEMS){
            if(points.get(i).IsCandidateNdom(currentNdomPt)){
                isDone = false ;
                TempNdomIndex = i ;
                TempNdomPt = points.get(TempNdomIndex) ;
                break ;
            }
            i++ ;
            steps++ ;
        }
        j = i ;
        while(j < NUM_OF_ITEMS){
            if(points.get(j).IsGreaterXY(TempNdomPt) && points.get(j).IsCandidateNdom(currentNdomPt)){
                TempNdomIndex = j ;
                TempNdomPt = points.get(TempNdomIndex) ;
            }
            j++ ;
            steps++ ;
        }
        if(!isDone){
        currentNdomIndex = TempNdomIndex ;
        currentNdomPt = TempNdomPt ;
        points.get(currentNdomIndex).isNdom = true ;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int j = 0; j < NUM_OF_ITEMS; j++) {
            drawItem(g, points.get(j)) ;
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIM_W, DIM_H);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Ndom Find");
                frame.add(new Animation());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
            }
        });
        
    }
}
