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
import java.util.ArrayList;
import java.util.Arrays;

/** This class contains utility methods used in the <code>CatmullClark</code> subdivision algorithm. Whilst the CatmullClark
 * class contains the main logic, this class contains geometric traversal, numerical & search functions.
 * 
 * @author M Hillman
 * @version 1.0 (25/11/2013)
 */
public class CatmullClarkUtils {
    
    /** Polyhedron object used as source for geometric calculations. */
    private Polyhedron polyhedron;

    /** Initialises CatmullClarkUtils object with input polyhedron as the object being subdivided. 
     * 
     * @param polyhedron, Polyhedron object that's being subdivided.
     */
    public CatmullClarkUtils(Polyhedron polyhedron) {
        this.polyhedron = polyhedron;
    }
    
    /** Given a edge, this method returns all faces that contain this edge.
     * 
     * @param edge input Edge3D to find winging faces for.
     * @return ArrayList<Face3D> list of winging faces (size should always be 2).
     */
    public ArrayList<Face3D> getWingingFaces(Edge3D edge) {
        ArrayList<Face3D> faces = new ArrayList<>();
        for(Face3D face : polyhedron.getFaceList()) {
            if(face.containsEdge(true, edge) && !faces.contains(face)) {
                faces.add(face);
            }
        }
        return faces;
    }
    
    /** Returns the Catmull-Clark face point for the input face. 
     * Note: this method assumes the face is enclosed by it's edges.
     * 
     * @param face, input Face3D to calculate face point for.
     * @return Vertex3D, resulting Catmull-Clark face point.
     */
    public Vertex3D getFacePoint(Face3D face) {
        return getAverage(face.getVertexList());
    }
    
    /** Calculates & returns the edge point for this edge as defined by Catmull & Clark
     * as the average of this edge's midpoint and the face points of it's two winging
     * faces.
     * 
     * @param edge Edge3D, edge to calculate edge point for.
     * @param fp1 Vertex3D, face point of first winging face.
     * @param fp2 Vertex3D, face point of second winging face.
     * @return Vertex3D, edge point for this edge.
     */
    public Vertex3D getEdgePoint(Edge3D edge, Vertex3D fp1, Vertex3D fp2) {
        return getAverage(new ArrayList<>(Arrays.asList(edge.getMidpoint(), fp1, fp2)));
    }
    
    /** Given a vertex, this method returns a list of all edges in a particular face that contain this vertex.
     * 
     * @param face Face3D face to search within.
     * @param vertex Vertex3D to use for edge search.
     * @return ArrayList<Edge3D> list of edges in the input face that contain the input vertex.
     */
    public ArrayList<Edge3D> getEdgesContainingVertex(Face3D face, Vertex3D vertex) {
        ArrayList<Edge3D> edges = new ArrayList<>();
        for(Edge3D edge : face.getEdgeList()) {
            if(edge.containsVertex(vertex) && !edges.contains(edge)) {
                edges.add(edge);
            }
        }
        return edges;
    }
    
    /** Given an input vertex, this method return a list of all faces in the polyhedron that contains the vertex.
     * 
     * @param vertex input vertex to find containing faces for.
     * @return ArrayList<Face3D> all faces that contain the input vertex.
     */
    public ArrayList<Face3D> getSurroundingFaces(Vertex3D vertex) {
        ArrayList<Face3D> faceList = new ArrayList<>();
        for(Face3D face : polyhedron.getFaceList()) {
            if(face.containsVertex(vertex) && !faceList.contains(face)) {
                faceList.add(face);
            }
        }
        return faceList;
    }
    
    /** Given an input vertex, this method return a list of all edges in the polyhedron that contains the vertex.
     * 
     * @param vertex input vertex to find containing edge for.
     * @return ArrayList<Edge3D> all edges that contain the input vertex.
     */
    public ArrayList<Edge3D> getSurroundingEdges(Vertex3D vertex) {
        ArrayList<Edge3D> edgeList = new ArrayList<>();
        for(Edge3D edge : polyhedron.getEdgeList()) {
            if(edge.containsVertex(vertex) && !edgeList.contains(edge) && !edgeList.contains(edge.reverse())) {
                edgeList.add(edge);
            }
        }
        return edgeList;
    }
    
    /** Given a list of vertices, this method calculates the resulting average vertex.
     * 
     * @param vertices list of input vertices.
     * @return resulting average vertex.
     */
    public Vertex3D getAverage(ArrayList<Vertex3D> vertices) {
        float x = 0.0f; float y = 0.0f; float z = 0.0f;
        for(Vertex3D vertex : vertices) {
            x += vertex.getX();
            y += vertex.getY();
            z += vertex.getZ();
        }
        return new Vertex3D((x / vertices.size()), (y / vertices.size()), (z / vertices.size()));
    }
    
    /** Returns the valence of a give Vertex3D object, as in how many edges connect to that vertex.
     * 
     * @param vertex Vertex3D to find valence of.
     * @return int, valence of input Vertex3D.
     */
    public float getValence(Vertex3D vertex) {
        float valence = 0;
        for(Face3D face : polyhedron.getFaceList()) {
            if(face.containsVertex(vertex)) {
                valence += 1.0f;
            }
        }
        return valence;
    }
    
    /** Given a vertex and scalar float this method returns the resulting vertex if the input vertex is
     * divided by the scalar value.
     * 
     * @param vert input vertex for division.
     * @param scalar scalar to divide vertex by.
     * @return Vertex3D, resulting vertex after division.
     */
    public Vertex3D getVertexDividedByScalar(Vertex3D vert, float scalar) {
        float x = vert.getX();
        float y = vert.getY();
        float z = vert.getZ();
        return new Vertex3D((float)(x / scalar), (float)(y / scalar), (float)(z / scalar));
    }
    
    /** Given a vertex and scalar float this method returns the resulting vertex if the input vertex is
     * multiplied by the scalar value.
     * 
     * @param vert input vertex for multiplication.
     * @param scalar scalar to multiply vertex by.
     * @return Vertex3D, resulting vertex after multiplication.
     */
     public Vertex3D getVertexMultipliedByScalar(Vertex3D vert, float scalar) {
        float x = vert.getX() * scalar;
        float y = vert.getY() * scalar;
        float z = vert.getZ() * scalar;
        return new Vertex3D(x, y, z);
    }
    
    /** Returns the sum vertex of an input array of vertex objects.
     * 
     * @param vertices Vertex3D[] to sum.
     * @return vertex representing the sum of input vertices.
     */
    public Vertex3D getVertexAddition(Vertex3D... vertices) {
        float x = 0.0f; float y = 0.0f; float z = 0.0f;
        for(int i = 0; i < vertices.length; i++) {
            x += vertices[i].getX();
            y += vertices[i].getY();
            z += vertices[i].getZ();
        }
        return new Vertex3D(x, y, z);
    }
     
}
//End of class.