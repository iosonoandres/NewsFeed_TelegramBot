package com.example.inferno_fx.OperazioniJSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import java.io.*;
import java.util.ArrayList;

public class gestoreGsonCategorie {

    public Categoria readJSON(String percorso) {

        try{
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(percorso)));

            Categoria categoria=gson.fromJson(bufferedReader, Categoria.class);
            return categoria;
        }
        catch(FileNotFoundException E){

            System.out.println("Non riesco a trovare il file");
            return new Categoria("Categoria Vuota",new ArrayList<>());
        }
    }

    //WRITER PER CATEGORIEWS
    public static void writeJSON(ArrayList<Categoria> categorie, String nomeFileOutput){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        FileWriter writer = null;
        try {
            writer = new FileWriter(nomeFileOutput);


            for(Categoria C: categorie){
                writer.write(gson.toJson(C));
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public ArrayList<Categoria> readJSONArray(String percorso) {
        ArrayList<Categoria> listaCategorie = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(percorso);
            Reader r = new InputStreamReader(is, "UTF-8");
            Gson gson = new GsonBuilder().create();
            JsonStreamParser p = new JsonStreamParser(r);

            while (p.hasNext()) {
                JsonElement e = p.next();
                if (((JsonElement) e).isJsonObject()) {
                    Categoria categoria = gson.fromJson(e, Categoria.class);
                    listaCategorie.add(categoria);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listaCategorie;
    }
}











