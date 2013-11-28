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
package hillman.opengl;

import hillman.algorithms.subdivision.SubdivisionKeyListener;
import com.jogamp.opengl.util.FPSAnimator;
import hillman.geometries.Edge3D;
import hillman.geometries.Face3D;
import hillman.geometries.Polyhedron;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.JLabel;


/** Represents an Swing JFrame containing a GLCanvas to visualise OpenGl drawings. Also contains a list of
 * Polyhedron objects to draw to screen.
 * 
 * @author M Hillman
 * @version 1.0 (25/11/2013)
 */
public class DrawingFrame extends JFrame implements GLEventListener {
    
    /** ArrayList to store Polyhedron objects. */
    private ArrayList<Polyhedron> polyhedrons;
    
    /** GLCanvas for drawing OpenGL objects to screen. */
    private GLCanvas canvas;
    
    /** GLU object for access to the OpenGL Utility Library. */
    private GLU glu;
    
     /** JLabel used to display text (far easier than rendering text in OpenGL. */
    private JLabel messageLabel;
    
    /** Initialises a GLCanvas within a JFrame & adds a WindowListener to halt the program
     * if the window is closed.
     * 
     * @param title String, desired frame title. 
     */
    public DrawingFrame(String title) {
        super(title);
        
        polyhedrons = new ArrayList<>();
        canvas = new GLCanvas();
        
        messageLabel = new JLabel();
        messageLabel.setOpaque(true);
        messageLabel.setBackground(Color.WHITE);
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setMinimumSize(new Dimension(100, 35));
        messageLabel.setMaximumSize(new Dimension(100, 35));
        messageLabel.setPreferredSize(new Dimension(100, 35));
        messageLabel.setFont(new Font("Courier", Font.PLAIN, 11));
        
        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(messageLabel, BorderLayout.SOUTH);
        addWindowListener(new ExitOnCloseAdaptor());
        
        glu = new GLU();
    }
    
    /** Sets frame size, location & displays on screen. */
    public void showFrame() {
        canvas.addGLEventListener(this);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /** Get an ArrayList of all currently on-screen Polyhedrons.
     * 
     * @return ArrayList<Polyhedron> all on-screen polyhedrons.
     */
    public ArrayList<Polyhedron> getPolyhedrons() {
        return polyhedrons;
    }
    
    /** Returns the most recently added Polyhedron object.
     * 
     * @return 
     */
    public Polyhedron getLastPolyhedron() {
        if(polyhedrons.isEmpty()) {
            return null;
        }
        return polyhedrons.get(polyhedrons.size() - 1);
    }
    
    /** Clears all currently drawn polyhedrons. */
    public void clearPolyhedrons() {
        polyhedrons.clear();
    }
    /** Adds the input Polyhedron to the ArrayList of currently drawn polyhedrons.
     * 
     * @param poly Polyhedron to append to ArrayList.
     */
    public void addPolyhedron(Polyhedron poly) {
        polyhedrons.add(poly);
    }
    
    /** Displays message in the GLCanvas (with some crude left-side padding).
     * 
     * @param message message to display.
     */
    public void drawString(String message) {
        messageLabel.setText("  " + message);
    }
    
    /** Toggles on-screen debug message displaying polyhedron meta data. */
    public void toggleDebug() {
        StringBuilder builder = new StringBuilder();
        if(messageLabel.getText().contains("Vertices")) {
            builder.append("");
        } else {
            builder.append("Vertices: ");
            builder.append(getLastPolyhedron().getVertexList().size());
            builder.append(", ");
            builder.append("Edges: ");
            builder.append(getLastPolyhedron().getEdgeList().size());
            builder.append(", ");
            builder.append("Faces: ");
            builder.append(getLastPolyhedron().getFaceList().size());
        }
        drawString(builder.toString());
    }
    
    /** Called by the drawable immediately after the OpenGL context is initialized for the first
    * time. Specifies which Matrix Stack to use, set's perspective & the camera location as well as 
    * adding an Animator object used to show rotation later on.
    * 
    * @param drawable the GLDrawable object.
    */
    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        GL2 gl2 = gl.getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        glu.gluPerspective(60, 1.0, 0.0, 100.0);
        glu.gluLookAt(0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
        
        FPSAnimator animator = new FPSAnimator(drawable, 25, true);
        animator.start();
    }

    /** Main OpenGl drawing method. Clears the Color & Depth buffers before continuing to draw each Polyhedron based on
     * it's comprising Face3D, Edge3D and Vertex3D objects. Flushes OpenGl work to the GPU after all polyhedrons have
     * been drawn.
     * 
     * @param drawable the GLDrawable object.
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl2.glRotatef(1.0f, 0.0f, 1.0f, 0.0f); 
        gl.glLineWidth(1.0f);
        gl2.glColor3f(0.0f, 0.0f, 0.0f);
        
        for(Polyhedron poly : polyhedrons) {
            
            for(Face3D face : poly.getFaceList()) {
                
                gl2.glBegin(GL.GL_LINES);
                for(Edge3D edge : face.getEdgeList()) {
                    gl2.glVertex3f(edge.getStart().getX(), edge.getStart().getY(), edge.getStart().getZ());
                    gl2.glVertex3f(edge.getEnd().getX(), edge.getEnd().getY(), edge.getEnd().getZ());
                } 
                gl2.glEnd();
            }
        }
        gl.glFlush();
    }

    /** Called by the drawable during the first repaint after the GlCanvas has been resized. Not used here as resizing
     * the containing JFrame is forbidden.
    * 
    * @param drawable the GLDrawable object.
    * @param x the X Coordinate of the viewport rectangle.
    * @param y the Y coordinate of the viewport rectangle.
    * @param width the new width of the window.
    * @param height the new height of the window.
    */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { 
         //Unused!
    }
    
    @Override
    public void dispose(GLAutoDrawable glad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /** Static inner named class used as a WindowAdapter to exit the program if the attached window is closed. */
    public static class ExitOnCloseAdaptor extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
//End of class.