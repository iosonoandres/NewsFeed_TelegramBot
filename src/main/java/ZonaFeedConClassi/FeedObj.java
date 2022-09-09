package ZonaFeedConClassi;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class FeedObj {

    public LinkedList<Notizia> newsList;
    private ListIterator<Notizia> iteratore;
    private Notizia currentNotizia; //sarà una variabile per reference
    private GestoreFeedback lista; //lista notizie già commentate

    public FeedObj(String sourceURL) //TODO secondo me dobbiamo passargli anche l'utente come variabile, feedObj non deve avere necessariamente avere un private Utente tho
    {
        try
        {
            newsList = new LinkedList<Notizia>();
            // sourceURL = "https://www.ansa.it/sito/ansait_rss.xml"; //qui bisogna fare che l'url cambia sempre
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(sourceURL))); //e se non fosse XML però?? da cambiare eventualmente
            List<SyndEntry> entries = feed.getEntries(); //getEntries deriva dalle librerie di rome. che fa???
            Iterator<SyndEntry> itEntries = entries.iterator(); //?????????????''
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

    public void GenerateNewsList(Iterator<SyndEntry> itEntries)
    {
        //creo la mappa con le notizie commentate (non so se creare una mappa o un oggetto gestorefeedback)
        lista=new GestoreFeedback(); //LISTA = GESTOREFEEDBACK

        while (itEntries.hasNext())
        {
            SyndEntry entry = itEntries.next();


            newsList.add(new Notizia(entry.getTitle(), entry.getPublishedDate(), entry.getDescription(), entry.getAuthor(), entry.getLink()));

            //se la lista delle notizie commentate ha come key il link, allora possiamo aggiungere il feedback alla notizia appena generata
            if (lista.getListaNotizieCommentate().containsKey(entry.getLink()))
            {
                System.out.println("il porco dio di lista è una mappa: ");
                System.out.println("mappa: "+lista.getListaNotizieCommentate());
                System.out.println("ora solo il value: "+lista.getListaNotizieCommentate().get(entry.getLink()));
                Feedback f=(lista.getListaNotizieCommentate()).get(entry.getLink());
                newsList.getLast().setFeedback(f);
                System.out.println("è stato trovato un feedback e in teoria l'ho aggiunto alla notizia che stavo generando");
            }
        }
        System.out.println("FeedObj: notizie generate correttamente! in teoria");
        iteratore=newsList.listIterator();


        currentNotizia=this.getNuovaNotizia(); //occhio che quando l'utente sceglie una categoria, la prima notizia da inviare è currentNotizia e non getNuovaNotizia()
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
            //bisognerebbe fare send message che ha finito, vab metto system output for now
            System.out.println("notizie finite! scelga un altro feed pls");
            return null;
        }
        //else gli diamo errore o ricominciamo ad iterare dall'inizio? per ora gli diciamo che son finite le notizie del feed
    }

    public Notizia getCurrentNotizia() {return this.currentNotizia;}
    public LinkedList<Notizia> getNewsList()
    {
        return newsList;
    }
    public GestoreFeedback getGestoreFeedBack() {return lista;}
    //commento per committare
}