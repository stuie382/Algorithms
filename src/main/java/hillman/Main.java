/**
* Copyright 2013 (c) Michael Hillman
* 
* This file is part of the larger, "Hillman Subdivision" project. The Subdivision project is 
* free software: you can redistribute it and/or modify it under the terms of the GNU General 
* Public License as published by the Free Software Foundation, either version 3 of the License, 
* or (at your option) any later version. The Subdivision project is distributed in the hope that 
* it will be useful for educational purposes, but WITHOUT ANY WARRANTY; without even the implied 
* warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with the Subdivision project. 
* If not, see the gnu website.
*/
package hillman;

import hillman.geometries.PolyhedronFactory;
import hillman.opengl.DrawingFrame;

/** The <code>Main</code> class is the entry point into this Subdivision program & initialises a <code>DrawingFrame</code>
 * object used to contain all OpenGL visualisations. Additionally, a factory produce unit cube is passed to the
 * <code>DrawingFrame</code> as the initial drawing object.
 * 
 * @author M Hillman
 * @version 1.0 (25/11/2013)
 */
public class Main {
    
    /** Initialises a <code>DrawingFrame</code> with a unit cube & displays on screen.
     * 
     * @param args Command line arguments. 
     */
    public static void main(String[] args) {
        LibrarySetup.setPath();
        
        DrawingFrame frame = new DrawingFrame("Sudivision Example");
        frame.addPolyhedron(PolyhedronFactory.getTriangleUnitCube());
        frame.showFrame();
    }
}
//End of class.