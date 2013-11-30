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
package hillman.geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a three dimensional geometric face (comprised of N Edge3D objects) in Euclidean space.
 * 
 * @author M Hillman
 * @version 1.0 (25-Nov-2013)
 */
public class Face3D {

    /** Set of Edge3D objects comprising this face. */
    private Set<Edge3D> edges;
    
    /** Constructor that initialises a Face3D object with the input array of Edge3D objects.
     * 
     * @param edges Edge3D[], desired edge array.
     */
    public Face3D(Edge3D... edges) {
       this.edges = new HashSet<>();
       this.edges.addAll(Arrays.asList(edges));
    }
    
    /** Returns the Edge3D set comprising this Face3D instance. 
     * 
     * @return Set<Edge3D>, edge array underlying this face.
     */
    public Set<Edge3D> getEdgeList() {
        return edges;
    }

    /** Returns a set of all Vertex3D objects that comprise this face. Note: these 
     * are return in order of edge construction, without any duplicates.
     * 
     * @return Set<Vertex3D>, unique array of Vertex3D objects.
     */
    public Set<Vertex3D> getVertexList() {
        Set<Vertex3D> vertices = new HashSet<>();
        for(Edge3D edge : getEdgeList()) {
            vertices.add(edge.getStart());
            vertices.add(edge.getEnd());
        }
        return vertices;
    }
    
    /** Returns the number of edges comprising this face.
     * 
     * @return int, number of edges (0 is edge array list is null).
     */
    public int getNumberOfEdges() {
        return (edges == null) ? 0 : edges.size();
    }
    
    /** Returns true if this face contains the input edge.
     * 
     * @param bidirectional boolean, including matching for reversed edges (true = include, false = exclude).
     * @param edge Edge3D, input edge to search for.
     * @return boolean, true if edge is found.
     */
    public boolean containsEdge(boolean bidirectional, Edge3D edge) {
        for(Edge3D e : getEdgeList()) {
            if(e.equals(edge)) {
                return true;
            }
            if(bidirectional && e.equals(edge.reverse())) {
                return true;
            }
        }
        return false;
    }
    
    /** Returns true if this face contains the input vertex.
     * 
     * @param vertex Vertex3D, input vertex to search for.
     * @return boolean, true if edge is found.
     */
    public boolean containsVertex(Vertex3D vertex) {
        for(Vertex3D vert : getVertexList()) {
            if(vert.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    /** Performs equality check between this instance & input object. Returns false
     * if the input object is null or not an instance of Face3D. Returns true if every
     * edge in this instance equals the corresponding edge in the input face.
     * 
     * @param obj Input Face3D for comparison.
     * @return true if all edges match.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Face3D)) {
            return false;
        }
        Face3D face = (Face3D) obj;
        if(getNumberOfEdges() != face.getNumberOfEdges()) {
            return false;
        }
        return face.getEdgeList().equals(getEdgeList());
    }

    /** Generates hash code based on edge list.
     * 
     * @return int, generated edge list. 
     */
    @Override
    public int hashCode() {
        return 19 * 7 + Objects.hashCode(this.edges);
    }

    /** Returns a textual representation of this face based on it's comprising Edge3D objects.
     * 
     * @return textual representation of this face.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Vertex3D vert : getVertexList()) {
            builder.append(vert.toString());
            builder.append(", ");
        }
        builder.replace(builder.length() - 3, builder.length() - 1, "");
        return builder.toString();
    }
    
}
//End of class.
