/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.box;

import java.io.File;

/**
 * Utility class to manage files and folders
 * @author albarral
 */
public class Files
{
    // check if given path exists
    public static boolean checkPathExists(String path)
    {
        boolean bvalid = false;
        if (!path.isEmpty())
        {
            File file = new File(path);
            bvalid = (file.exists());
        }

        if (!bvalid)
        {
            System.out.println("Files: path does not exist " + path);                                       
        }
        return bvalid;
    }

    public static boolean cleanFolder(String path)
    {
        File folder = new File(path);
        // assure it's a folder
        if (folder.isDirectory())
        {
            File[] files = folder.listFiles();
            if (files != null) 
            {
                // delete each file in the folder
                for (File file : files)
                {
                    if (file.isFile())
                        file.delete();
                }
            }
            return true;
        }
        // if not a folder, skip
        else
        {
            System.out.println("Files: cleanFolder() failed, path does not exist " + path);                                       
            return false;
        }
    }    
}
