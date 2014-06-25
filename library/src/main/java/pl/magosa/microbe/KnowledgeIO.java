package pl.magosa.microbe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;

/**
 * Class represents mechanisms for reading/writing knowledge from file/to file.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class KnowledgeIO {
    public static void write(final NetworkKnowledge knowledge, final File file) throws IOException {
        Gson gson = new GsonBuilder().create();
        JsonElement root = gson.toJsonTree(knowledge);

        FileWriter fw = new FileWriter(file);
        gson.toJson(root, fw);
        fw.close();
    }

    public static NetworkKnowledge read(final File file) throws FileNotFoundException {
        FileReader fr = new FileReader(file);
        Gson gson = new GsonBuilder().create();
        NetworkKnowledge knowledge = gson.fromJson(fr, NetworkKnowledge.class);

        return knowledge;
    }
}
