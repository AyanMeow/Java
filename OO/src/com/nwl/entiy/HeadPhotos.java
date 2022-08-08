package com.nwl.entiy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HeadPhotos {
    
    File file=null;
    File [] files =null;
    List<String> hsphotos=null;
    
    public HeadPhotos() {
       /*  file=new File(HeadPhotos.class.getClassLoader().getResource("image").getFile());
        files=file.listFiles(); */
    }

    public List<String> getPhotos() {
        hsphotos=new ArrayList<String>();
        //int len=files.length;
        for(int i=0;i<4;i++)
        {
            /* String name=files[i].getName();
            if(!name.equals("img.jpg")) */
                hsphotos.add(i+".jpg");
        }
        return hsphotos;
    }
}
