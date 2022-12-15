package com.royalstil.royalstildesktop;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class FileReader {

    public static HashMap<String, String> settings;

    public final void confirmConfigSettings(){
        settings = new HashMap<>();

        try{
            InputStream inputFile = getClass().getResourceAsStream("config");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputFile));

            String line;

            while((line = bufferedReader.readLine()) != null ){
                String[] lineParams = line.split("=");
                settings.put(lineParams[0], lineParams[1]);
            }

            /*
            List<String> list = Files.readAllLines(Paths.get(getClass().getResource("config").toURI()));
            for (String line : list) {

            }*/
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
