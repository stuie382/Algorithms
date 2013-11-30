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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** Polyhedron
 * 
 * @author M Hillman
 * @version 1.0
 * 
 * Represents a three dimensional polyhedron with a number of flat faces & straight edges in Euclidean space.
 */
public class Polyhedron {
    
    /** Set of faces that comprise this polyhedron. */
    private Set<Face3D> faces;
    
    /** Constructor that initialises with an array list of Face3D objects.
     * 
     * @param faces Collection<Face3D>, desired array list of face objects.
     */
    public Polyhedron(Collection<Face3D> faces) {
        this.faces = new HashSet<>();
        for(Face3D face : faces) {
            this.faces.add(face);
        }
    }
    
    /** Returns the Face3D set that comprises this polyhedron.
     * 
     * @return Set<Face3D>, face set.
     */
    public Set<Face3D> getFaceList() {
        return faces;
    }
    
    /** Returns an hash set of all Edge3D objects that comprise this polyhedron. 
     * Note: these are return in order of edge construction, without any duplicates.
     * 
     * @return Set<Edge3D>, unique array list of Edge3D objects.
     */
    public Set<Edge3D> getEdgeList() {
        Set<Edge3D> edgeList = new HashSet<>();
        for(Face3D face : getFaceList()) {
            for(Edge3D e : face.getEdgeList()) {
                if(!edgeList.contains(e.reverse())) {
                    edgeList.add(e);
                }
            }
        }
        return edgeList;
    }
    
    /** Returns an set of all Vertex3D objects that comprise this polyhedron.
     * Note: these are return in order of edge construction, without any duplicates.
     * 
     * @return Set<Verte3D>, unique array list of Vertex3D objects.
     */
    public Set<Vertex3D> getVertexList() {
        Set<Vertex3D> vertList = new HashSet<>();
        for(Face3D f : getFaceList()) {
            for(Edge3D e : f.getEdgeList()) {
                vertList.add(e.getStart());
                vertList.add(e.getEnd());
            }
        }
        return vertList;
    }
    
    /** Returns a textual representation of this Polyhedron.
     * 
     * @return String, textual representation of this Polyhedron. 
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Face3D face : getFaceList()) {
            builder.append(face.toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}
//End of class.