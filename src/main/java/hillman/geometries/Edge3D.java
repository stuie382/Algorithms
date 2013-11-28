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

import hillman.algorithms.subdivision.catmull_clark.CatmullClarkUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a three dimensional edge (comprised of two Vertex3D objects) in Euclidean space.
 * 
 * @author M Hillman
 * @version 1.0 (25-Nov-2013)
 */
public class Edge3D {
    
    /** Starting & ending Vertex3D objects, defining this Edge3D instance. */
    private Vertex3D start, end;
    
    /** Constructor that initialises with input starting & ending Vertex3D objects.
     * 
     * @param start Vertex3D, desired start of edge.
     * @param end Vertex3D, desired end of edge.
     */
    public Edge3D(Vertex3D start, Vertex3D end) {
        this.start = start;
        this.end = end;
    }
    
    /** Returns true if the input Vertex3D object equals this edge's starting
     * or ending vertex.
     * 
     * @param vertex Vertex3D for comparison.
     * @return true if this edge contains the input vertex.
     */
    public boolean containsVertex(Vertex3D vertex) {
        return getStart().equals(vertex) || getEnd().equals(vertex);
    }
    
    /** Calculates & returns a Vertex3D representing the midpoint of this
     * Edge3D instance.
     * 
     * @return Vertex3D, midpoint of this Edge3D instance.
     */
    public Vertex3D getMidpoint() {
        Vertex3D midVertex = new Vertex3D(0.0f, 0.0f, 0.0f);
        midVertex.setX((getStart().getX() + getEnd().getX()) / 2.0f);
        midVertex.setY((getStart().getY() + getEnd().getY()) / 2.0f);
        midVertex.setZ((getStart().getZ() + getEnd().getZ()) / 2.0f);
        return midVertex;
    }
    
    /** Returns an Edge3D object in the reverse direction of this instance.
     * 
     * @return Edge3D, reversed direction edge.
     */
    public Edge3D reverse() {
        return new Edge3D(getEnd(), getStart());
    }

    /** Returns the Vertex3D object representing the start of this edge.
     * 
     * @return Vertex3D, start of this edge.
     */
    public Vertex3D getStart() {
        return start;
    }

    /** Overwrites the Vertex3D object representing the start of this edge.
     * 
     * @param start Vertex3D, desired start of this edge.
     */
    public void setStart(Vertex3D start) {
        this.start = start;
    }
    
    /** Returns the Vertex3D object representing the end of this edge.
     * 
     * @return Vertex3D, end of this edge.
     */
    public Vertex3D getEnd() {
        return end;
    }

    /** Overwrites the Vertex3D object representing the end of this edge.
     * 
     * @param end Vertex3D, desired end of this edge.
     */
    public void setEnd(Vertex3D end) {
        this.end = end;
    }
    
    /** Performs equality check, false if input object is null or not an instance
     * of an Edge3D object. Returns true if this instance & the input Edge3D object's
     * start & end vertices match numerically.
     * 
     * @param obj input object for comparison
     * @return true if vertices all match.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Edge3D)) {
            return false;
        }
        Edge3D edge = (Edge3D) obj;
        if(getStart().equals(edge.getStart()) && getEnd().equals(edge.getEnd())) {
            return true;
        } 
        return false;
    }

    /** Generates hash code based on start & end vertices of this edge.
     * 
     * @return generated hash code.
     */
    @Override
    public int hashCode() {
        int hash = 67 * 7 + Objects.hashCode(this.start);
        hash = 67 * hash + Objects.hashCode(this.end);
        return hash;
    }
    
    /** Returns a textual representation of this Edge3D object, defined by it's comprising
     * start & end Vertex3D objects.
     * 
     * @return String representing this Edge3D instance.
     */
    @Override
    public String toString() {
        return getStart().toString() + " > " + getEnd().toString();
    }
    
}
//End of class.