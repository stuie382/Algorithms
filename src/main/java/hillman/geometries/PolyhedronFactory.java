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

/** This PolyhedronFactory class contains static methods to generate simple Polyhedron objects.
 * 
 * @author M Hillman
 * @version 1.0 (25/11/2013)
 */ 
public class PolyhedronFactory {
    
    /** Returns a unit cube comprised of triangular faces with centre at origin in the form of a Polyhedron object.
     * 
     * @return Polyhedron, unit cube at origin.
     */
    public static Polyhedron getTriangleUnitCube() {
        Vertex3D vertA = new Vertex3D(-0.5f, -0.5f, 0.5f);
        Vertex3D vertB = new Vertex3D(0.5f, -0.5f, 0.5f);
        Vertex3D vertC = new Vertex3D(0.5f, 0.5f, 0.5f);
        Vertex3D vertD = new Vertex3D(-0.5f, 0.5f, 0.5f);
        Vertex3D vertE = new Vertex3D(-0.5f, -0.5f, -0.5f);
        Vertex3D vertF = new Vertex3D(0.5f, -0.5f, -0.5f);
        Vertex3D vertG = new Vertex3D(0.5f, 0.5f, -0.5f);
        Vertex3D vertH = new Vertex3D(-0.5f, 0.5f, -0.5f);
        
        Edge3D edgeA = new Edge3D(vertA, vertB);
        Edge3D edgeB = new Edge3D(vertB, vertC);
        Edge3D edgeC = new Edge3D(vertC, vertD);
        Edge3D edgeD = new Edge3D(vertD, vertA);
        Edge3D edgeE = new Edge3D(vertB, vertF);
        Edge3D edgeF = new Edge3D(vertF, vertG);
        Edge3D edgeG = new Edge3D(vertG, vertC);
        Edge3D edgeH = new Edge3D(vertG, vertH);
        Edge3D edgeI = new Edge3D(vertE, vertH);
        Edge3D edgeJ = new Edge3D(vertF, vertE);
        Edge3D edgeK = new Edge3D(vertH, vertD);
        Edge3D edgeL = new Edge3D(vertE, vertA);
        Edge3D edgeM = new Edge3D(vertA, vertC);
        Edge3D edgeN = new Edge3D(vertG, vertB);
        Edge3D edgeO = new Edge3D(vertH, vertF);
        Edge3D edgeP = new Edge3D(vertE, vertD);
        Edge3D edgeQ = new Edge3D(vertD, vertG);
        Edge3D edgeR = new Edge3D(vertA, vertF);
        
        Face3D faceA = new Face3D(edgeA, edgeB, edgeM.reverse());
        Face3D faceB = new Face3D(edgeC, edgeD, edgeM);
        Face3D faceC = new Face3D(edgeE, edgeF, edgeN);
        Face3D faceD = new Face3D(edgeG, edgeB.reverse(), edgeN.reverse());
        Face3D faceE = new Face3D(edgeH.reverse(), edgeF.reverse(), edgeO);
        Face3D faceF = new Face3D(edgeJ, edgeI, edgeO);
        Face3D faceG = new Face3D(edgeI.reverse(), edgeK.reverse(), edgeP);
        Face3D faceH = new Face3D(edgeD.reverse(), edgeL, edgeP.reverse());
        Face3D faceI = new Face3D(edgeA.reverse(), edgeE.reverse(), edgeR);
        Face3D faceJ = new Face3D(edgeJ.reverse(), edgeL.reverse(), edgeR.reverse());
        Face3D faceK = new Face3D(edgeC.reverse(), edgeG.reverse(), edgeQ.reverse());
        Face3D faceL = new Face3D(edgeH, edgeK, edgeQ);
        
        Polyhedron cube = new Polyhedron(faceA, faceB, faceC, faceD, faceE, faceF, faceG, faceH, faceI, faceJ, faceK, faceL);
        return cube;
    }
    
    /** Returns a unit cube comprised of square faces with centre at origin in the form of a Polyhedron object.
     * 
     * @return Polyhedron, unit cube at origin.
     */
    public static Polyhedron getSquareUnitCube() {
        Vertex3D vertA = new Vertex3D(-0.5f, -0.5f, 0.5f);
        Vertex3D vertB = new Vertex3D(0.5f, -0.5f, 0.5f);
        Vertex3D vertC = new Vertex3D(0.5f, 0.5f, 0.5f);
        Vertex3D vertD = new Vertex3D(-0.5f, 0.5f, 0.5f);
        Vertex3D vertE = new Vertex3D(-0.5f, -0.5f, -0.5f);
        Vertex3D vertF = new Vertex3D(0.5f, -0.5f, -0.5f);
        Vertex3D vertG = new Vertex3D(0.5f, 0.5f, -0.5f);
        Vertex3D vertH = new Vertex3D(-0.5f, 0.5f, -0.5f);
        
        Edge3D edgeA = new Edge3D(vertA, vertB);
        Edge3D edgeB = new Edge3D(vertB, vertC);
        Edge3D edgeC = new Edge3D(vertC, vertD);
        Edge3D edgeD = new Edge3D(vertD, vertA);
        Edge3D edgeE = new Edge3D(vertB, vertF);
        Edge3D edgeF = new Edge3D(vertF, vertG);
        Edge3D edgeG = new Edge3D(vertG, vertC);
        Edge3D edgeH = new Edge3D(vertG, vertH);
        Edge3D edgeI = new Edge3D(vertE, vertH);
        Edge3D edgeJ = new Edge3D(vertE, vertF);
        Edge3D edgeK = new Edge3D(vertH, vertD);
        Edge3D edgeL = new Edge3D(vertA, vertE);
        
        Face3D faceA = new Face3D(edgeA, edgeB, edgeC, edgeD);
        Face3D faceB = new Face3D(edgeE, edgeF, edgeG, edgeB.reverse());
        Face3D faceC = new Face3D(edgeH, edgeI, edgeJ, edgeF);
        Face3D faceD = new Face3D(edgeK, edgeD, edgeL, edgeI.reverse());
        Face3D faceE = new Face3D(edgeC.reverse(), edgeG.reverse(), edgeH, edgeK);
        Face3D faceF = new Face3D(edgeA, edgeE, edgeJ.reverse(), edgeL.reverse());
        
        Polyhedron cube = new Polyhedron(faceA, faceB, faceC, faceD, faceE, faceF);
        return cube;
    }
    
}
//End of class.