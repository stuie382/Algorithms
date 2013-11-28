/**
* Algorithms Project - Maven based Java project used to showcase various common algorithm implementations.
* Copyright 2013 - 2014 (c) Michael Hillman (thisishillman.co.uk)
* 
* This file is part of the larger, Algorithms project. The Algorithms project is 
* free software: you can redistribute it and/or modify it under the terms of the GNU General 
* Public License as published by the Free Software Foundation, either version 3 of the License, 
* or (at your option) any later version. This project is distributed in the hope that 
* it will be useful for educational purposes, but WITHOUT ANY WARRANTY; without even the implied 
* warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with the Algorithms project. 
* If not, see the gnu website.
*/
package hillman.algorithms.subdivision;

import hillman.algorithms.subdivision.catmull_clark.CatmullClark;
import hillman.algorithms.subdivision.root_three.RootThree;
import hillman.geometries.PolyhedronFactory;
import hillman.opengl.DrawingFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** This class represents a <code>KeyListener</code> used to detect specific key presses on an Swing JFrame and run
 * differing subdivision algorithms depending on said key press codes.
 * 
 * @author M Hillman
 * @version 1.0 (25/11/2013)
 */
public class SubdivisionKeyListener implements KeyListener {
    
    /** DrawingFrame used to draw OpenGL objects. */
    private DrawingFrame frame;
    
    /** String used to store which type of subdivision is currently being used. */
    private String subdivisionType;
    
    /** Initialises & stores a class variable containing the DrawingFrame.
     * 
     * @param frame DrawingFrame used to show OpenGl Objects.
     */
    public SubdivisionKeyListener(DrawingFrame frame, String subdivisionType) {
        this.frame = frame;
        this.subdivisionType = subdivisionType;
    }
    
    /** Detects key presses & runs the relevant subdivision algorithm, passing the resulting Polyhedron back
     * to the DrawingFrame. Current key codes are:
     * 
     * 1: Reset to Triangular-faced Unit Cube.
     * 2: Reset to Square-faced Unit Cube.
     * C: Subdivide.
     * D: Toggle on-screen debug message.
     * 
     * @param e KeyEvent fired when user presses key. 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_1 :
                frame.clearPolyhedrons();
                frame.addPolyhedron(PolyhedronFactory.getTriangleUnitCube());
                frame.drawString("");
            break;
            
            case KeyEvent.VK_2 :
                frame.clearPolyhedrons();
                frame.addPolyhedron(PolyhedronFactory.getSquareUnitCube());
                frame.drawString("");
            break;
                
            case KeyEvent.VK_D :
                frame.toggleDebug();
            break;
                
            case KeyEvent.VK_ENTER :
                switch(subdivisionType) {
                    case "catmull" :
                        CatmullClark cc = new CatmullClark(frame.getLastPolyhedron(), frame);
                        Thread catmullClarkThread = new Thread(cc);
                        catmullClarkThread.setName("Catmull-Clark Subdivision");
                        catmullClarkThread.start();
                    break;
                        
                    case "root3" :
                        RootThree rt3 = new RootThree(frame.getLastPolyhedron(), frame);
                        Thread rootThreeThread = new Thread(rt3);
                        rootThreeThread.setName("Root-Three Subdivision");
                        rootThreeThread.start();
                    break;
                }
            break;
                
            default :
                //Nothing!
            break;
        }
    }

    /** Detects key types from the user. Currently unused.
     * 
     * @param e KeyEvent fired when user types key. 
     */
    @Override
    public void keyTyped(KeyEvent e) {
        //Unused!
    }
    
    /** Detects key releases from the user. Currently unused.
     * 
     * @param e KeyEvent fired when user releases key. 
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //Unused!
    }
    
}
//End of class.