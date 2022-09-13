package ZonaFeedConClassi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class FeedObj {

    public TreeSet<Notizia> newsList; //FIFO
    private Iterator<Notizia> iteratore;
    private Notizia currentNotizia; //sarà una variabile per reference
    private GestoreFeedback lista; //lista notizie già commentate
    private String nomeFile;

    public FeedObj(String sourceURL, String nomeFile) //TODO secondo me dobbiamo passargli anche l'utente come variabile, feedObj non deve avere necessariamente avere un private Utente tho
    {
        try
        {
            newsList = new TreeSet<Notizia>();
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(sourceURL)));
            List<SyndEntry> entries = feed.getEntries();
            Iterator<SyndEntry> itEntries = entries.iterator();
            this.nomeFile=nomeFile;
            this.GenerateNewsList(itEntries);
            //iteratore=newsList.listIterator(); viene fatto in generatenewslist
        }
        catch(IOException e)
        {
            System.out.println("errore, input non valido");
        }
        catch(FeedException e)
        {
            System.out.println("errore, non è stato possibile generare il feed");
            //da fare che il feed viene generato in modo default?
        }
    }

    public void GenerateNewsList(Iterator<SyndEntry> itEntries) {
        //leggo il file delle notizie commentate e le inserisco in una mappa
        lista = new GestoreFeedback(); //LISTA = GESTOREFEEDBACK

        //leggo le notizie salvate da file e le metto in una lista Temporanea
        ArrayList<Notizia> listaNotizieSalvate = new ArrayList<Notizia>();
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            FileReader fileread = new FileReader(nomeFile);
            BufferedReader bufferedReader = new BufferedReader(fileread);
            Type mapType = new TypeToken<ArrayList<Notizia>>(){}.getType();
            listaNotizieSalvate = gson.fromJson(bufferedReader, mapType);
        } catch (FileNotFoundException e) {
            System.out.println("FeedObj non ha trovato nessun file, presumiamo sia vuoto");
        }
        //vediamo se legge
        System.out.println("CIAO STO LEGGENDO: ECCO:");
        //System.out.println(listaNotizieSalvate.get(0).getTitolo());
        //genero una lista di news nuove togliendo quelle che sono nel file delle notizie rimosse
        ArrayList<String> listaNotizieRimosse=new ArrayList<String>();
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            FileReader fileread = new FileReader("NotizieRimosse.json");
            BufferedReader bufferedReader = new BufferedReader(fileread);
            Type mapType = new TypeToken<ArrayList<Notizia>>(){}.getType();
            listaNotizieRimosse = gson.fromJson(bufferedReader, mapType);
        } catch (FileNotFoundException e) {
            System.out.println("Non trovo il file NotizieRimosse.json");
        }

        while (itEntries.hasNext()) {
            SyndEntry entry = itEntries.next();

            if(!listaNotizieRimosse.contains(entry.getLink()))
            {
                newsList.add(new Notizia(entry.getTitle(), entry.getPublishedDate(), entry.getDescription(), entry.getAuthor(), entry.getLink()));
            }


        }
        //unisco notizie nuove con notizie vecchie, facendo l'ennesimo while santa maria
        for(Notizia s:listaNotizieSalvate)
        {
            if(!newsList.contains(s))
            {
                newsList.add(s);
            }
        }

        //scrivo su file
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            FileWriter writer = new FileWriter(nomeFile);
            String ilToJson = gson.toJson(newsList); //TODO vedere se pure qua serve la maletta del Type
            writer.write(ilToJson);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //ora altro ciclo: se la lista delle notizie commentate ha come key il link, allora possiamo aggiungere il feedback alla notizia appena generata
        for (Notizia N : newsList) {
            if (lista.getListaNotizieCommentate().containsKey(N.getLink())) {
                System.out.println("il porco dio di lista è una mappa: ");
                System.out.println("mappa: " + lista.getListaNotizieCommentate());
                System.out.println("ora solo il value: " + lista.getListaNotizieCommentate().get(N.getLink()));
                Feedback f = (lista.getListaNotizieCommentate()).get(N.getLink());
                newsList.last().setFeedback(f); //first o last???????
                System.out.println("è stato trovato un feedback e in teoria l'ho aggiunto alla notizia che stavo generando");
            }
        }
        System.out.println("FeedObj: notizie generate correttamente");
        iteratore = newsList.iterator();
        currentNotizia = this.getNuovaNotizia(); //occhio che quando l'utente sceglie una categoria, la prima notizia da inviare è currentNotizia e non getNuovaNotizia()
    }

    public Notizia getNuovaNotizia()
    {
        if (iteratore.hasNext())
        {
            this.currentNotizia=iteratore.next();
            return this.currentNotizia;
        }
        else
        {
            //TODO bisognerebbe fare send message che ha finito, vab metto system output for now
            System.out.println("notizie finite! scelga un altro feed pls");
            return null;
        }
        //else gli diamo errore o ricominciamo ad iterare dall'inizio? per ora gli diciamo che son finite le notizie del feed
    }

    public Notizia getCurrentNotizia() {return this.currentNotizia;}
    public TreeSet<Notizia> getNewsList()
    {
        return newsList;
    }
    public GestoreFeedback getGestoreFeedBack() {return lista;}
}