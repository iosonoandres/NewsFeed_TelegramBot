package ZonaFeedConClassi;

import java.util.ArrayList;
import java.util.HashMap;

public class Feedback //la classe feedback contiene tutte le informazioni necessarie da associare ad un link della notizia
{
    //potrei mettere un private String linkAssociatoAlFeedback oppure mettere il link come key nella mappa del gestoreFeedback, ho fatto la seconda per ora
    //TODO queste due sotto vanno mappe non arraylist
    private ArrayList<String> commenti;
    //nome utente + ": " + commento
    //associa una stringa utente a diversi commenti. al posto di arraylist potremmo mettere un oggetto così salviamo anche la data del commento
    private HashMap<String,Integer> voti; //qui ho fatto che i voti sono anonimi idk

    public Feedback(String username) {} //TODO
    public Feedback() //costruttore vuoto (TODO gestire feedback null)
    {
        this.commenti=new ArrayList<>();
        this.voti=new HashMap<>();
    }

    public ArrayList<String> getCommenti() {return this.commenti;}
    public HashMap<String,Integer> getVoti() {return this.voti;}
    //TUTTO QUESTO è FEEDBACK
}