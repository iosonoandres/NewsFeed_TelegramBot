package ZonaBot;

import ZonaAmministratore.Categorie;
import ZonaFeedConClassi.FeedObj;
import ZonaFeedConClassi.Notizia;
import ZonaFeedConClassi.gestoreJson;
import ZonaFeedConClassi.gestoreJson_CATEGORIE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rometools.rome.io.FeedException;

import java.io.IOException;
import java.util.LinkedList;

public class mainGsonPrincipale {

    public static void main(String[] args) throws FeedException, IOException {

        gestoreJson GESON = new gestoreJson();
        FeedObj vaffanculo = new FeedObj("https://www.repubblica.it/rss/homepage/rss2.0.xml");
        LinkedList<Notizia> questeNotizie = vaffanculo.newsList;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();


        gestoreJson_CATEGORIE GESONperCat = new gestoreJson_CATEGORIE();


        Categorie categorieDaPrendere = new Categorie("andale.json");


        try {
             GESON.writeJSON(questeNotizie);
             //GESONperCat.aggiungiUrlACategoriaJson(cat, "Sport", "bell url");

        }catch(IOException e){
            System.out.println("errore Json");
        }


    }




}
