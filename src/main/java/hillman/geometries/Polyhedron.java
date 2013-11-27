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
package hillman.geometries;

import java.util.ArrayList;
import java.util.Arrays;

/** Polyhedron
 * 
 * @author M Hillman
 * @version 1.0
 * 
 * Represents a three dimensional polyhedron with a number of flat faces & straight edges in Euclidean space.
 */
public class Polyhedron {
    
    /** Array list of faces that comprise this polyhedron. */
    private ArrayList<Face3D> faces;
    
    /** Constructor that initialises with an array of Face3D objects.
     * 
     * @param faces Face3D[], desired array of face objects.
     */
    public Polyhedron(Face3D... faces) {
        this.faces = new ArrayList<>(Arrays.asList(faces));
    }
    
    /** Constructor that initialises with an array list of Face3D objects.
     * 
     * @param faces ArrayList<Face3D>, desired array list of face objects.
     */
    public Polyhedron(ArrayList<Face3D> faces) {
        this.faces = faces;
    }
    
    /** Returns the Face3D array list that comprises this polyhedron.
     * 
     * @return ArrayList<Face3D>, face array list.
     */
    public ArrayList<Face3D> getFaceList() {
        return faces;
    }
    
    /** Returns an array list of all Edge3D objects that comprise this polyhedron. 
     * Note: these are return in order of edge construction, without any duplicates.
     * 
     * @return ArrayList<Edge3D>, unique array list of Edge3D objects.
     */
    public ArrayList<Edge3D> getEdgeList() {
        ArrayList<Edge3D> edgeList = new ArrayList<>();
        for(Face3D face : getFaceList()) {
            for(Edge3D e : face.getEdgeList()) {
                if(!edgeList.contains(e) && !edgeList.contains(e.reverse())) {
                    edgeList.add(e);
                }
            }
        }
        return edgeList;
    }
    
    /** Returns an array list of all Vertex3D objects that comprise this polyhedron.
     * Note: these are return in order of edge construction, without any duplicates.
     * 
     * @return ArrayList<Verte3D>, unique array list of Vertex3D objects.
     */
    public ArrayList<Vertex3D> getVertexList() {
        ArrayList<Vertex3D> vertList = new ArrayList<>();
        for(Face3D f : getFaceList()) {
            for(Edge3D e : f.getEdgeList()) {
                if(!vertList.contains(e.getStart())) {
                    vertList.add(e.getStart());
                }
                if(!vertList.contains(e.getEnd())) {
                    vertList.add(e.getEnd());
                }
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