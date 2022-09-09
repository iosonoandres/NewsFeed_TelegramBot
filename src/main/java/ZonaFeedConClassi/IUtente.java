package ZonaFeedConClassi;

public interface IUtente //è giusto una classe per capire dove mettere e come mettere i vari metodi
{
    public void InteragisciCon(String link);
    /* questo metodo dovrebbe essere invocato ogni volta che
    * l'utente interagisce con una notizia;
    * la notizia interagita verrà messa nel file json dell'utente, tipo il database delle notizie interagite */
}
