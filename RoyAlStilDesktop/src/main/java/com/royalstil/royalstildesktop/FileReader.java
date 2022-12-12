package com.royalstil.royalstildesktop;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class FileReader {

    public static HashMap<String, String> settings;

    public final void confirmConfigSettings() throws IOException, URISyntaxException {
        settings = new HashMap<>();

        List<String> list = Files.readAllLines(Path.of(getClass().getResource("config").toURI()));

        for (String line : list) {
            String[] lineParams = line.split("=");
            settings.put(lineParams[0], lineParams[1]);
        }
    }
}
