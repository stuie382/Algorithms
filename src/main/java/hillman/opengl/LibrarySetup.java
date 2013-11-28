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
package hillman.opengl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * Detects OS manufacturer & arch to set the library path (primarily for JOGL and GlueGen libraries).
 * 
 * @author M Hillman
 * @version 1.0 (27/11/2013)
 */
public class LibrarySetup {

    /** Determines Operating System & hardware architecture to set the Java library path to the appropriate folder.
     * Primarily used to point towards the correct JOGL & GlueGen native libraries. */
    public static void setPath() {
        boolean sixtyFourBitOS = false;
        boolean sixtyFourBitJVM = false;
        
        sixtyFourBitJVM = System.getProperty("os.arch").contains("64");
        String path = new File("").getAbsolutePath() + "/lib/";
        
        if(System.getProperty("os.name").toLowerCase().contains("window")) {
            path += "windows/";
            sixtyFourBitOS = (System.getenv("ProgramFiles(x86)") != null);
        } else {
            path += "linux/";
            try {
                Process proc = Runtime.getRuntime().exec("uname -a");
                BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line = "";
                while((line = br.readLine()) != null) {
                    if(line.contains("x86_64")) {
                        sixtyFourBitOS = true;
                    }
                }
                proc.destroy();
            } catch(IOException e) { }
        }
        
        try {
            final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
            usrPathsField.setAccessible(true);
            final String[] paths = (String[])usrPathsField.get(null);
            final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);

            if(sixtyFourBitOS && sixtyFourBitJVM) {
                path += "64/";
                newPaths[newPaths.length - 1] = path;
                usrPathsField.set(null, newPaths);
            } else if(!sixtyFourBitOS && !sixtyFourBitJVM) {
                path += "32/";
                newPaths[newPaths.length - 1] = path;
                usrPathsField.set(null, newPaths);
            } else {
                JOptionPane.showMessageDialog(null, "Cannot determine OS/Architecture for native libraries. Please contact M Hillman.");
                System.exit(0);
            }
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "Cannot determine OS/Architecture for native libraries. Please contact M Hillman.");
            System.exit(0);
        }
    }
    
}
//End of class.