package ch_without_gui;

// tPoint2 class
// defines a point in 2D plain
// include x, y coordinates
// include a comparison between two points based on x and y coordinates
// include a pointer points to the next and previous point based on certain criteria

// Mu Tian
// subject to modification
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
//import java.util.*;
//import java.lang.Double;

public class tPoint2 extends Point2D.Double
{
    
    public boolean isNdom = false ;
    
    public tPoint2 (int x, int y){
        super(x,y) ;
    }
    
    public tPoint2 (){
        super(0, 0) ; 
    }
    
    public boolean IsEqualAll (tPoint2 p) {
        return this.equals(p) ; // function equals taken from super class
    }
    
    // test if two points have the same x-coor but different y-coor
    public boolean IsEqualXonly (tPoint2 p) {
        return(this.x == p.getX() && this.y != p.getY()) ;
    }
    
    // test if two points have the same y-coor but different x-coor
    public boolean IsEqualYonly (tPoint2 p) {
        return(this.y == p.getY() && this.x != p.getX()) ;
    }
    
    // test if the current point have larger Y value
    public boolean IsGreaterY (tPoint2 p) {
        return (400-this.y) > (400-p.getY()) ;
    }
    
    // test if the current point have larger X value
    public boolean IsGreaterX (tPoint2 p) {
        return this.x > p.getX() ;
    }
    
    
    // is greater in x? if same x, if greater in y?
    public boolean IsGreaterXY(tPoint2 p) {
        if(this.x > p.getX()){
            return true ;
        }else if(this.x == p.getX() && (400-this.y) > (400-p.getY())){
            return true ;
        }else{
            return false ;
        }
        
    }
    
    // test if the current point if a candidate nondominated point as we match through out algorithms
    // i.e. the point has a smaller x value than the current nondominated(Ndom) point p
    // and a larger y value than the current nondominated point p
    public boolean IsCandidateNdom(tPoint2 p) {
        //return(this.x < p.getX() && this.y > p.getY()) ;
        if(this.x < p.getX() && (400-this.y) > (400-p.getY())){
            return true ;
        }else{
            return false ;
        }
    }
    
}


