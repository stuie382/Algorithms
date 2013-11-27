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

/**
 * This class represents a three-dimensional float based Vertex in Euclidean space.
 * 
 * @author M Hillman 
 * @version 1.0 (25-Nov-2013)
 */
public class Vertex3D {

    /** Used for floating point equality checks. */
    private static final float EPSILON = 0.0000001f;
    
    /** Vertex components. */
    private float x, y, z;
    
    /** Constructor that initialises with the input float components.
     * 
     * @param x X float, dimension component.
     * @param y Y float, dimension component.
     * @param z Z float, dimension component.
     */
    public Vertex3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /** Returns the X dimensional component of this Vector3D instance.
     * 
     * @return float, X component.
     */
    public float getX() {
        return x;
    }

    /** Overwrite the X dimensional component for this Vector3D instance.
     * 
     * @param x float, desired X component.
     */
    public void setX(float x) {
        this.x = x;
    }

    /** Returns the Y dimensional component of this Vector3D instance.
     * 
     * @return float, Y component.
     */
    public float getY() {
        return y;
    }

    /** Overwrite the Y dimensional component for this Vector3D instance.
     * 
     * @param y float, desired Y component.
     */
    public void setY(float y) {
        this.y = y;
    }

    /** Returns the Z dimensional component of this Vector3D instance.
     * 
     * @return float, Z component.
     */
    public float getZ() {
        return z;
    }

    /** Overwrite the Z dimensional component for this Vector3D instance.
     * 
     * @param z float, desired Z component.
     */
    public void setZ(float z) {
        this.z = z;
    }

    /** Two Vertex3D objects are evaluated as equal if all float components
     * evaluate as equals. Returns false if the input object is null or not
     * an instance of Vertex3D.
     * 
     * @param obj Object, object for comparison.
     * @return boolean, true if all float components are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Vertex3D)) {
            return false;
        }
        Vertex3D vertex = (Vertex3D) obj;
        boolean xMatch = Math.abs(getX() - vertex.getX()) < EPSILON;
        boolean yMatch = Math.abs(getY() - vertex.getY()) < EPSILON;
        boolean zMatch = Math.abs(getZ() - vertex.getZ()) < EPSILON;
        return (xMatch && yMatch && zMatch);
    }

    /** Generates hash code based on X, Y & Z float components.
     * 
     * @return int, generated hash code.
     */
    @Override
    public int hashCode() {
        int hash = 89 * 3 + Float.floatToIntBits(this.x);
        hash = 89 * hash + Float.floatToIntBits(this.y);
        hash = 89 * hash + Float.floatToIntBits(this.z);
        return hash;
    }
    
    /** Returns a textual representation of this Vertex3D object.
     * 
     * @return String, components of this Vertex3D in string form.
     */
    @Override
    public String toString() {
        return ("(" + x + ", " + y + ", " + z + ")");
    }
}
//End of class.