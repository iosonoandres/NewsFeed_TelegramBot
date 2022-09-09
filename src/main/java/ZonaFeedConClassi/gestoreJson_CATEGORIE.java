package ZonaFeedConClassi;

import ZonaAmministratore.Categorie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class gestoreJson_CATEGORIE //questa classe è inutile si potrebbe eliminare
{

    public void aggiungiUrlACategoriaJson(Categorie cat, String categoria, String urlone) throws IOException //questo aggiunge un value ad un key
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        FileWriter writer = new FileWriter("ProvaPeCategorie.json");

        //writer.write("[");

        //aggiunge url all'oggetto Categorie, per ora non sul json
        if (cat.getMap().containsKey(categoria))
        {
            cat.listaUrl(categoria).add(urlone);
        }
        else
        {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(urlone);
            cat.getMap().put(categoria, temp);
        }
        writer.write(gson.toJson(cat));
        writer.close();

    }




    public void readJSON() throws FileNotFoundException
    {

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("Provazza.json"));

        Utente user = gson.fromJson(bufferedReader, Utente.class);
    }
    //commento per committare
}