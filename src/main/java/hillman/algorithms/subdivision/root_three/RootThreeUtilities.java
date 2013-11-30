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

import hillman.geometries.Edge3D;
import hillman.geometries.Face3D;
import hillman.geometries.Polyhedron;
import hillman.geometries.Vertex3D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** This class contains utility methods used in the <code>RootThree</code> subdivision algorithm. Whilst the RootThree
 * class contains the main logic, this class contains geometric traversal, numerical & search functions.
 * 
 * @author M Hillman
 * @version 1.0 (28/11/2013)
 */
public class RootThreeUtilities {

    /** Polyhedron object used as source for geometric calculations. */
    private Polyhedron polyhedron;
    
    /** Initialises RootThreeUtilities object with input polyhedron as the object being subdivided. 
     * 
     * @param polyhedron, Polyhedron object that's being subdivided.
     */
    public RootThreeUtilities(Polyhedron polyhedron) {
        this.polyhedron = polyhedron;
    }
    
    /** Given a collection of vertices, this method calculates the resulting average vertex.
     * 
     * @param vertices collection of input vertices.
     * @return resulting average vertex.
     */
    public Vertex3D getAverage(Collection<Vertex3D> vertices) {
        float x = 0.0f; float y = 0.0f; float z = 0.0f;
        for(Vertex3D vertex : vertices) {
            x += vertex.getX();
            y += vertex.getY();
            z += vertex.getZ();
        }
        return new Vertex3D((x / vertices.size()), (y / vertices.size()), (z / vertices.size()));
    }
    
    /** Given an input vertex, this method return a set of all the vertices in the polyhedron that ring
     * the input vertex.
     * 
     * @param vertex input vertex to find containing edge for.
     * @return Set<Vertex3D> all neighbouring vertices.
     */
    public Set<Vertex3D> getSurroundingVertices(Vertex3D vertex) {
        Set<Vertex3D> vertexList = new HashSet<>();
        for(Edge3D edge : polyhedron.getEdgeList()) {
            if(edge.getStart().equals(vertex)) {
                vertexList.add(edge.getEnd());
            } else if(edge.getEnd().equals(vertex)) {
                vertexList.add(edge.getStart());
            }
        }
        return vertexList;
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
     * @param vertices ArrayList<Vertex3D> to sum.
     * @return vertex representing the sum of input vertices.
     */
    public Vertex3D getVertexAddition(Collection<Vertex3D> vertices) {
        float x = 0.0f; float y = 0.0f; float z = 0.0f;
        for(Vertex3D vertex : vertices) {
            x += vertex.getX();
            y += vertex.getY();
            z += vertex.getZ();
        }
        return new Vertex3D(x, y, z);
    }
    
    
    /** Given a edge, this method returns a set of all faces that contain this edge.
     * 
     * @param edge input Edge3D to find winging faces for.
     * @return Set<Face3D> set of winging faces (size should always be 2).
     */
    public Set<Face3D> getWingingFaces(Edge3D edge) {
        Set<Face3D> faces = new HashSet<>();
        for(Face3D face : polyhedron.getFaceList()) {
            if(face.containsEdge(true, edge) && !faces.contains(face)) {
                faces.add(face);
            }
        }
        return faces;
    }
    
    /** Given an edge & it's containing face, this method will search for the only other face that
     * wings that edge and returns it's midpoint.
     * 
     * @param edge Edge that wings the desired face.
     * @param face Face that contains the input edge (used for equality check to find the other face),
     * @return Vertex3D, midpoint of only winging face.
     */
    public Vertex3D getMidPointOfWingingFace(Edge3D edge, Face3D face) {
        for(Face3D otherFace : polyhedron.getFaceList()) {
            if(otherFace.containsEdge(true, edge) && !otherFace.equals(face)) {
                return getAverage(otherFace.getVertexList());
            }
        }
        return null;
    }
    
    /** Returns true if the input collection contains the input face, regardless of edge or vertex order.
     * 
     * @param faceList input list of Face3D objects for searching.
     * @param face search target.
     * @return true if face is in collection (regardless of direction).
     */
    public boolean containsFace(Collection<Face3D> faceList, Face3D face) {
        for(Face3D faceFromList : faceList) {
            if(faceFromList.getVertexList().equals(face.getVertexList())) {
                return true;
            }
        }
        return false;
    }
    
}
//End of class.