package ZonaFeedConClassi;

import com.rometools.rome.feed.synd.SyndContent;

import java.util.Date;

public class Notizia {
    private String titolo; //ecc tutti i vari dati prendibili, per esempio con Notizia.titolo;
    private Date data;
    private SyndContent description; //SyndContent o String??
    private String author;
    private String link; //TODO: se il link tra due notizie è identico supponiamo che la notizia sia la stessa. usiamo il link come "id" della notizia

    //la notizia contiene una mappa che associa un utente ad un feedback.
    private Feedback feedbacks;
    //private Map<Utente, Feedback> ranking; //quando un utente inserisce un feedback controllare che non ne abbia già inserito uno. Se c'è già, modifica

    public Notizia(String titolo, Date data, SyndContent description, String author, String link) {
        this.titolo = titolo;
        this.data = data;
        this.description = description;
        this.author = author;
        this.link = link;
        this.feedbacks = new Feedback();

        //output di debug sta roba
        /*
        System.out.println(titolo); //titolo
        System.out.println("[" + data.toString() + "] "); //timestamp
        System.out.println(description.getValue()); //per scrivere la descrizione come stringa si fa getValue cuz è un SyndContent
        System.out.println(author); //autore
        System.out.println("source: " + link+"\n"); //link/fonte

         */

    }

    public String getTitolo() {
        return titolo;
    }

    public Date getData() {
        return data;
    }

    public SyndContent getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }

    public String toString() {
        return this.titolo + "\n" + this.data.toString() + "\n" + this.description.getValue() + " (scritto da " + this.author + ")\n source: " + this.link;
    }

    public Feedback getFeedback() {
        return this.feedbacks;
    }

    public void setFeedback(Feedback f) {
        this.feedbacks = f;
    } //utile quando si fa il confronto tra notizia e notizie commentate

//TUTTO QUESTO è NOTIZIA

}