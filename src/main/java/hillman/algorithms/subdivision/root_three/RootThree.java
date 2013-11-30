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
package hillman.algorithms.subdivision.root_three;

import hillman.algorithms.subdivision.SubdivisionAlgorithm;
import hillman.algorithms.subdivision.SubdivisionHandler;
import hillman.geometries.Edge3D;
import hillman.geometries.Face3D;
import hillman.geometries.Polyhedron;
import hillman.geometries.PolyhedronFactory;
import hillman.geometries.Vertex3D;
import hillman.opengl.DrawingFrame;
import hillman.opengl.LibrarySetup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/** This class holds the core logic & iteration loops for the Kobbelt's Root-Three subdivision algorithm.
 * 
 * @author M Hillman
 * @version 1.0 (28/11/2013).
 */
public class RootThree implements SubdivisionAlgorithm, Runnable {

    /** Polyhedron for subdivision. */
    private Polyhedron polyhedron;
    
    /** <code>RootThreeUtilities</code> object used for additional geometric calculations. */
    private RootThreeUtilities utils;
    
    /** DrawingFrame object used as target for resulting polyhedron. */
    private DrawingFrame frame;
    
    /** Sets the Polyhedron object for subdivision, creates a RootThreeUtilities
     * object to handle additional mathematical calculations, finally creates a Thread
     * object & starts the subdivision proceedure,
     * 
     * @param polyhedron Polyhedron for subdivision.
     * @param frame DrawingFrame to push resulting Polyhedron to.
     */
    @Override
    public void subdivide(Polyhedron polyhedron, DrawingFrame frame) {
        this.polyhedron = polyhedron;
        this.frame = frame;
        this.utils = new RootThreeUtilities(polyhedron);
        
        Thread thread = new Thread(this);
        thread.setName("Catmull-Clark Subdivision");
        thread.start();
    }
    
    /** Main logic for the Kobbelt's Root-Three algorithm, pushes final polyhedron to the DrawingFrame when done. In essence,
     * this method fulfils the algorithm via the following steps:
     * 
     * 1. For every face in the original mesh, we insert a midpoint (defined as the average of the face's three original points).
     * 2. For each vertex in that face, calculate B (a function of the vertex's valence) as (where n is the valence):
     *      B = (4 - 2cos(2PI/n)) / 9n
     * 3. Each original vertex in that face in then relaxed following the equation below where P is the original vertex, B is a 
     * function of P's valence (see step 2), n is P's valence, S is the resulting relaxed vertex, and M total of all the 
     * vertices that ring vertex S.
     *      S = (1 - nB)P + BM
     * 4. Next we create three new faces for each face, from the relaxed vertex based on each edge's start vertex -> midpoint ->
     * the relaxed vertex based on that edge's end vertex.
     * 5. Finally the old edges are flipped to connect pairs of midpoints.
     * 6. All faces (carefully ignoring duplicates) are then added to a new polyhedron & pushed to the DrawingFrame
     * 
     * Note: Throws an IllegalArgumentException is subdivision is attempted on a non-triangular input mesh.
     */
    @Override
    public void run() {
        frame.drawString("Running Root-Three Subdivision...");
        ArrayList<Face3D> newFaces = new ArrayList<>();
        
        for(Face3D face : polyhedron.getFaceList()) {
            if(face.getNumberOfEdges() != 3) {
                throw new IllegalArgumentException("Cannot perform Root-Three subdivision on a non-triangluar face!");
            }
            
            Vertex3D midVertex = utils.getAverage(face.getVertexList());
            
            for(Edge3D originalEdge : face.getEdgeList()) {
                Vertex3D relaxedStart = relaxVertex(originalEdge.getStart());
                Vertex3D relaxedEnd = relaxVertex(originalEdge.getEnd());
                
                Vertex3D otherMidVertex = utils.getMidPointOfWingingFace(originalEdge, face);
                
                Edge3D newEdge1 = new Edge3D(relaxedStart, midVertex);
                Edge3D newEdge2 = new Edge3D(midVertex, otherMidVertex);
                Edge3D newEdge3 = new Edge3D(otherMidVertex, relaxedStart);
                Face3D newFace1 = new Face3D(newEdge1, newEdge2, newEdge3);
                
                Edge3D newEdge4 = new Edge3D(relaxedEnd, midVertex);
                Edge3D newEdge5 = new Edge3D(otherMidVertex, relaxedEnd);
                Face3D newFace2 = new Face3D(newEdge4, newEdge2, newEdge5);
                
                if(!utils.containsFace(newFaces, newFace1)) {
                    newFaces.add(newFace1);
                }
                if(!utils.containsFace(newFaces, newFace2)) {
                    newFaces.add(newFace2);
                }
            }
        }
        frame.clearPolyhedrons();
        frame.addPolyhedron(new Polyhedron(newFaces));
        frame.drawString("");
    }
    
    /** Given an original vertex as input, this methods grabs the sum of it's
     * neighbouring vertices, applies the B scalar & returns the now relaxed vertex.
     * 
     * @param originalVertex original mesh vertex to relax.
     * @return Vertex3D, relaxed vertex.
     */
    private Vertex3D relaxVertex(Vertex3D originalVertex) {
        float n = utils.getValence(originalVertex);
        Vertex3D relaxedVertex = new Vertex3D(0.0f, 0.0f, 0.0f);
        float scalar = 1.0f - (n * getB(originalVertex));
        
        relaxedVertex = utils.getVertexMultipliedByScalar(originalVertex, scalar);
        return utils.getVertexAddition(new ArrayList<>(
                Arrays.asList(relaxedVertex, getSumOfNeighbours(originalVertex))));
    }
    
    /** Given an original vertex as input, this simply sums the values of
     * it's neighbouring vertices into one vertex.
     * 
     * @param originalVertex original mesh vertex.
     * @return Vertex3D, sum of neighbouring vertices.
     */
    private Vertex3D getSumOfNeighbours(Vertex3D originalVertex) {
        float scalar = getB(originalVertex);
        Set<Vertex3D> vertexMask = utils.getSurroundingVertices(originalVertex);
        return utils.getVertexMultipliedByScalar(utils.getVertexAddition(vertexMask), scalar);
    }
    
    /** Calculates Kobbelt's B scalar as a function of a vertex's valence.
     * 
     * @param originalVertex vertex to calculate B for.
     * @return float, Kobbelt's B scalar factor.
     */
    private float getB(Vertex3D originalVertex) {
        float n = utils.getValence(originalVertex);
        float cos = (float) (2.0f * Math.cos(2.0 * Math.PI));
        float b = (4.0f - (cos / n)) / (9.0f * n);
        return b;
    }
    
    /** Initialises a <code>DrawingFrame</code> with a unit cube & displays on screen, ready for Catmull-Clark subdivision.
     * 
     * @param args Command line arguments. 
     */
    public static void main(String[] args) {
        LibrarySetup.setPath();
        DrawingFrame frame = new DrawingFrame("Root-Three Subdivision");
        frame.addKeyListener(new SubdivisionHandler(frame, new RootThree()));
        frame.addPolyhedron(PolyhedronFactory.getTriangleUnitCube());
        frame.showFrame();
    }
}
//End of class.
