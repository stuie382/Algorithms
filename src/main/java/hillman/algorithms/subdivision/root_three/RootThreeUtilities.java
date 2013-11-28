/*
 * Copyright (c) 2012-2013 CMCL Innovations - All Rights Reserved
 * 
 * This application and all inherent data, source files, information and graphics are 
 * the copyright and sole property of Computational Modelling Cambridge Ltd (CMCL Innovations). 
 * 
 * Any unauthorised redistribution or reproduction of part, or all, of the contents of this 
 * application in any form is prohibited under UK Copyright Law. You may not, except with the 
 * express written permission of CMCL Innovations, distribute or commercially exploit this
 * application or it's content. All other rights reserved.
 * 
 * For more information please contact support@cmclinnovations.com
 */

package hillman.algorithms.subdivision.root_three;

import hillman.geometries.Edge3D;
import hillman.geometries.Face3D;
import hillman.geometries.Polyhedron;
import hillman.geometries.Vertex3D;
import java.util.ArrayList;
import java.util.Arrays;

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
    
    /** Given an input vertex, this method return a list of all the vertices in the polyhedron that ring
     * the input vertex.
     * 
     * @param vertex input vertex to find containing edge for.
     * @return ArrayList<Vertex3D> all neighbouring vertices.
     */
    public ArrayList<Vertex3D> getSurroundingVertices(Vertex3D vertex) {
        ArrayList<Vertex3D> vertexList = new ArrayList<>();
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
    public Vertex3D getVertexAddition(ArrayList<Vertex3D> vertices) {
        float x = 0.0f; float y = 0.0f; float z = 0.0f;
        for(int i = 0; i < vertices.size(); i++) {
            x += vertices.get(i).getX();
            y += vertices.get(i).getY();
            z += vertices.get(i).getZ();
        }
        return new Vertex3D(x, y, z);
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
                return getAverage(new ArrayList<>(Arrays.asList(otherFace.getVertexList().get(0), 
                        otherFace.getVertexList().get(1), otherFace.getVertexList().get(2))));
            }
        }
        return null;
    }
    
    /** Returns true if the input list contains the input face, regardless of edge or vertex order.
     * 
     * @param faceList input list of Face3D objects for searching.
     * @param face search target.
     * @return true if face is in list (regardless of direction).
     */
    public boolean containsFace(ArrayList<Face3D> faceList, Face3D face) {
        for(Face3D faceFromList : faceList) {
            if(faceFromList.containsVertex(face.getVertexList().get(0)) && faceFromList.containsVertex(face.getVertexList().get(1))
                    && faceFromList.containsVertex(face.getVertexList().get(2))) {
                return true;
            }
        }
        return false;
    }
    
}
//End of class.