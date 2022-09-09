package ZonaFeedConClassi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.LinkedList;

public class gestoreJson {

     public void writeJSON(LinkedList<Notizia> tipo) throws IOException { //TODO fixare
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();



        Gson gson = builder.create();
        FileWriter writer = new FileWriter("Provazza.json");

        writer.write("[");

        for(Notizia N: tipo){
            if (!(tipo.indexOf(N)== tipo.size()-1)){
            writer.write(gson.toJson(N)+ ",");
            }
            else {
                writer.write(gson.toJson(N));

            }
        }
         writer.write("]");

         writer.close();
     }



    public void readJSON() throws FileNotFoundException {

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("Provazza.json"));

        Utente user = gson.fromJson(bufferedReader, Utente.class);
    }

    //commento per committare
}








