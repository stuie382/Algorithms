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

package hillman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * LibrarySetup.java
 * 
 * @author Michael Hillman - CMCL Innovations
 * @version 1.0 (27-Nov-2013)
 * MavenTest - hillman
 */
public class LibrarySetup {

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
                System.out.println("NO 3D SUCKER!");
            }
            
            System.out.println("SET PATH AS: " + path);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            //Either the libaries cannot be loaded, or their's an Java/OS architecture mismatch.
        }
    }
}
