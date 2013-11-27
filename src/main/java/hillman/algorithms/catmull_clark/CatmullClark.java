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

package hillman.algorithms.catmull_clark;

import hillman.geometries.Edge3D;
import hillman.geometries.Face3D;
import hillman.geometries.Polyhedron;
import hillman.geometries.Vertex3D;
import hillman.opengl.DrawingFrame;
import java.util.ArrayList;

/** This class holds the core logic & iteration loops for the Catmull-Clark subdivision algorithm.
 * 
 * @author M Hillman
 * @version 1.0 (25/11/2013).
 */
public class CatmullClark implements Runnable {
    
    /** Polyhedron for subdivision. */
    private Polyhedron polyhedron;
    
    /** <code>CatmullClarkUtils</code> object used for additional geometric calculations. */
    private CatmullClarkUtils utils;
    
    /** DrawingFrame object used as target for resulting polyhedron. */
    private DrawingFrame frame;
    
    /** Initialises with an input Polyhedron object for subdivision, also creates a CatmullClarkUtilities
     * object to handle additional mathematical calculations.
     * 
     * @param polyhedron Polyhedron for subdivision.
     * @param frame DrawingFrame to push resulting Polyhedron to.
     */
    public CatmullClark(Polyhedron polyhedron, DrawingFrame frame) {
        this.polyhedron = polyhedron;
        this.frame = frame;
        this.utils = new CatmullClarkUtils(polyhedron);
    }
    
    /** Main logic for the Catmull-Clark algorithm, pushes final polyhedron to the DrawingFrame when done. In essence,
     * this method fulfils the algorithm via the following steps:
     * 
     * 1. For every vertex in the original mesh (S), calculate the new weighted vertex point - 
     *      a. Get the average of all the face points for the faces surrounding the vertex (F)
     *      b. Get the average of all the edge mid-points for the edges containing the vertex (R)
     *      c. Calculate the valence of the point (n).
     *      d. Calculate the final vertex point: (F/n) + (2R/n) + ((n-3)S/n).
     * 2. For every face containing the original vertex, calculate it's face point & the edge points of it's edges that
     * contain the original vertex.
     * 3. For each of these faces, create a new face constructed of the following vertices:
     *      vertexPoint -> edgePoint1 -> facePoint -> edgePoint2 -> vertexPoint
     * 4. Plug new faces into a Polyhedron object.
     */
    @Override
    public void run() {
        frame.drawString("Running Catmull-Clark Subdivision...");
        ArrayList<Face3D> newFaces = new ArrayList<>();
        
        for(Vertex3D vertexS : polyhedron.getVertexList()) {
            
            float valence = utils.getValence(vertexS);
            
            Vertex3D vertexF = getVertexF(vertexS);
            Vertex3D vertexR = getVertexR(vertexS);
            
            vertexF = utils.getVertexDividedByScalar(vertexF, valence);
            vertexR = utils.getVertexDividedByScalar(utils.getVertexMultipliedByScalar(vertexR, 2), valence);
            
            Vertex3D adjustedS = utils.getVertexMultipliedByScalar(vertexS, (valence - 3.0f));
            adjustedS = utils.getVertexDividedByScalar(adjustedS, valence);
            
            Vertex3D vertexPoint = utils.getVertexAddition(vertexF, vertexR, adjustedS);
        
            for(Face3D face : utils.getSurroundingFaces(vertexS)) {
                Face3D newFace = new Face3D();
                ArrayList<Vertex3D> edgePoints = new ArrayList<>();
                
                for(Edge3D edge : utils.getEdgesContainingVertex(face, vertexS)) {
                    ArrayList<Face3D> wings = utils.getWingingFaces(edge); 
                    Vertex3D facePoint1 = utils.getFacePoint(wings.get(0));
                    Vertex3D facePoint2 = utils.getFacePoint(wings.get(1));
                    edgePoints.add(utils.getEdgePoint(edge, facePoint1, facePoint2));
                }
                
                newFace.getEdgeList().add(new Edge3D(vertexPoint, edgePoints.get(0)));
                newFace.getEdgeList().add(new Edge3D(edgePoints.get(0), utils.getFacePoint(face)));
                newFace.getEdgeList().add(new Edge3D(utils.getFacePoint(face), edgePoints.get(1)));
                newFace.getEdgeList().add(new Edge3D(edgePoints.get(1), vertexPoint));
                newFaces.add(newFace);
            }
        }
        frame.clearPolyhedrons();
        frame.addPolyhedron(new Polyhedron(newFaces));
        frame.drawString("");
    }
    
    /** Given an input vertex, this method returns a new vertex representing the average of all the face points of all
     * the faces that contain the input vertex.
     * 
     * @param vertexS input vertex to get Catmull-Clark F value for.
     * @return Vertex3D, average of surrounding face points.
     */
    public Vertex3D getVertexF(Vertex3D vertexS) {
        ArrayList<Vertex3D> facePoints = new ArrayList<>();
        for(Face3D face : utils.getSurroundingFaces(vertexS)) {
            facePoints.add(utils.getFacePoint(face));
        }
        return utils.getAverage(facePoints);
    }
    
    /** Given an input vertex, this method returns a new vertex representing the average of all the edge mid-points of all
     * the edges that contain the input vertex.
     * 
     * @param vertexS input vertex to get Catmull-Clark R value for.
     * @return Vertex3D, average of surrounding edge mid-points.
     */
    public Vertex3D getVertexR(Vertex3D vertexS) {
        ArrayList<Vertex3D> edgeMids = new ArrayList<>();
        
        for(Edge3D edge : utils.getSurroundingEdges(vertexS)) {
            edgeMids.add(edge.getMidpoint());
        }
        return utils.getAverage(edgeMids);
    }
    
}
//End of class.