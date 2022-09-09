package ZonaBot;

import ZonaFeedConClassi.FeedObj;

public interface IMetodiBot
{
    /*creo un interfaccia con tutti i metodi da coprire;
    in ogni if del metodo OnUpdateReceived anzichè fargli fare cose, gli faremo chiamare direttamente i metodi
     */


    void ScegliFeed(String ilfeedscelto);
    /*
    */
    void InviaNotizia(FeedObj feed);
    /* /l'utente sceglie e genera un feed con un tasto (con un altro metodo quindi);
    una volta fatto, questo metodo viene invocato prendendo in input il feed generato
    e spedisce all'utente la notizia in posizione i dell'arraylist di notizie del feed.
    Quando un FeedObj viene appena generato, il suo indice parte da 0 e aumenta per ogni notizia mandata.
    Se l'utente richiede una nuova notizia dello stesso feed, viene invocato questo metodo con lo stesso feedobj.*/
}
