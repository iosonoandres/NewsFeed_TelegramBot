package ZonaBot;

import ZonaAmministratore.Categorie;
import ZonaFeedConClassi.FeedObj;
import ZonaFeedConClassi.GestoreFeedback;
import ZonaFeedConClassi.Utente;
import ZonaFeedConClassi.gestoreGsonUtentone;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class myBotDef extends TelegramLongPollingBot {

    private Categorie categorieDisponibili = new Categorie("Categorie.json");
    private FeedObj feedDinamico;
    private String linkNuova;
    private GestoreFeedback gestoreDeiFeedback=new GestoreFeedback();
    private gestoreGsonUtentone gestoreDegliUtenti=new gestoreGsonUtentone();

    private Utente utenteTemporaneo= new Utente(null,null,false, 0);

    private boolean usabile;
    private boolean verifica;
    private boolean registrazione;
    private boolean confermaRegistrazione;

    private boolean accesso;
    private boolean confermaAccesso;

    private boolean confermaVoto;





    public static SendMessage login(SendMessage S){
        InlineKeyboardButton rispostaUno = new InlineKeyboardButton();
        InlineKeyboardButton rispostaDue = new InlineKeyboardButton();
        rispostaUno.setText("Non sono registrato 🫤");
        rispostaUno.setCallbackData("daRegistrare");
        rispostaDue.setText("Sono già registrato 😙");
        rispostaDue.setCallbackData("registrato");

        ArrayList<InlineKeyboardButton> risposteVarie = new ArrayList<>();
        risposteVarie.add(rispostaUno);
        risposteVarie.add(rispostaDue);

        InlineKeyboardMarkup perLogin = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();


        for(InlineKeyboardButton B : risposteVarie){
            rowInline.add(B);
        }
        rowsInline.add(rowInline);
        perLogin.setKeyboard(rowsInline);

        S.setReplyMarkup(perLogin);
        return S;


    }



    public static InlineKeyboardMarkup creatoreRowsInLine(ArrayList<InlineKeyboardButton> input) {
        InlineKeyboardMarkup daRestituire = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInlineDue = new ArrayList<>();

        for(int i=0; i<input.size();i++){
            if(i<=2){
                rowInline.add(input.get(i));
            }
            else{
                rowInlineDue.add(input.get(i));
            }
        }


        rowsInline.add(rowInline);
        rowsInline.add(rowInlineDue);
        // Add it to the message
        daRestituire.setKeyboard(rowsInline);
        return daRestituire;
    }

    // public static


    public static ArrayList<InlineKeyboardButton> creatorePulsantiNotizia(){
        InlineKeyboardButton rispostaVoto = new InlineKeyboardButton();
        rispostaVoto.setText("Vota ⭐️");
        rispostaVoto.setCallbackData("votonissimo");


        InlineKeyboardButton rispostaLasciaUnCommento = new InlineKeyboardButton();
        rispostaLasciaUnCommento.setText("Commenta 💡");
        rispostaLasciaUnCommento.setCallbackData("commentissimo");

        InlineKeyboardButton rispostaNuovaNotizia = new InlineKeyboardButton();
        rispostaNuovaNotizia.setText("Nuova news 📸");
        rispostaNuovaNotizia.setCallbackData("nuovissima");

        InlineKeyboardButton rispostaCommentiVari = new InlineKeyboardButton();
        rispostaCommentiVari.setText("Commenti on 🌍");
        rispostaCommentiVari.setCallbackData("mondocommenti");

        InlineKeyboardButton rispostaMediaVoti = new InlineKeyboardButton();
        rispostaMediaVoti.setText("Media feedback💡");
        rispostaMediaVoti.setCallbackData("mondovoti");

        ArrayList<InlineKeyboardButton> perInput = new ArrayList<>();
        perInput.add(rispostaVoto);
        perInput.add(rispostaNuovaNotizia);
        perInput.add(rispostaLasciaUnCommento);
        perInput.add(rispostaCommentiVari);
        perInput.add(rispostaMediaVoti);

        return perInput;
    }







    @Override

    public void onUpdateReceived(Update update) {


        if (update.hasCallbackQuery()){

            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_idone = update.getCallbackQuery().getMessage().getChatId();

            if(call_data.equals("sportissimo")){
                ArrayList<InlineKeyboardButton> perInput = creatorePulsantiNotizia();

                ArrayList<String> nuovo = categorieDisponibili.listaUrl("Sport");
                int posizioneRandomInFeed = (int) (Math.random()*nuovo.size());
                feedDinamico= new FeedObj(nuovo.get(posizioneRandomInFeed), "NotizieSport.json");

                String contenuto= feedDinamico.getNuovaNotizia().getLink();
                SendMessage notiziaSportiva = new SendMessage(String.valueOf(chat_idone), contenuto);
                notiziaSportiva.setReplyMarkup(creatoreRowsInLine(perInput));

                /* contenuto += "\n" + "  TITOLO: " + temp.getTitolo() + "\n" + "  AUTORE " + temp.getAuthor() + "\n" +
                            "  DESCRIPTION " + temp.getDescription() + "\n" + "  DATA " + temp.getData();*/

                try{
                    execute(notiziaSportiva);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }
            if(call_data.equals("politicissima")){
                ArrayList<InlineKeyboardButton> perInput = creatorePulsantiNotizia();

                ArrayList<String> nuovo = categorieDisponibili.listaUrl("Politica");
                int posizioneRandomInFeed = (int) (Math.random()*nuovo.size());
                feedDinamico= new FeedObj(nuovo.get(posizioneRandomInFeed), "NotiziePolitica.json");

                String contenuto= feedDinamico.getNuovaNotizia().getLink();
                SendMessage notiziaSportiva = new SendMessage(String.valueOf(chat_idone), contenuto);
                notiziaSportiva.setReplyMarkup(creatoreRowsInLine(perInput));

                /* contenuto += "\n" + "  TITOLO: " + temp.getTitolo() + "\n" + "  AUTORE " + temp.getAuthor() + "\n" +
                            "  DESCRIPTION " + temp.getDescription() + "\n" + "  DATA " + temp.getData();*/

                try{
                    execute(notiziaSportiva);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }

            if(call_data.equals("soldissimo")){
                ArrayList<InlineKeyboardButton> perInput = creatorePulsantiNotizia();

                ArrayList<String> nuovo = categorieDisponibili.listaUrl("Economia");
                int posizioneRandomInFeed = (int) (Math.random()*nuovo.size());
                feedDinamico= new FeedObj(nuovo.get(posizioneRandomInFeed), "NotizieEconomia.json");

                String contenuto= feedDinamico.getNuovaNotizia().getLink();
                SendMessage notiziaEconomia = new SendMessage(String.valueOf(chat_idone), contenuto);
                notiziaEconomia.setReplyMarkup(creatoreRowsInLine(perInput));

                /* contenuto += "\n" + "  TITOLO: " + temp.getTitolo() + "\n" + "  AUTORE " + temp.getAuthor() + "\n" +
                            "  DESCRIPTION " + temp.getDescription() + "\n" + "  DATA " + temp.getData();*/

                try{
                    execute(notiziaEconomia);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if(call_data.equals("techissimo")){
                ArrayList<InlineKeyboardButton> perInput = creatorePulsantiNotizia();

                ArrayList<String> nuovo = categorieDisponibili.listaUrl("Tech");
                int posizioneRandomInFeed = (int) (Math.random()*nuovo.size());
                feedDinamico= new FeedObj(nuovo.get(posizioneRandomInFeed), "NotizieTech.json");

                String contenuto= feedDinamico.getNuovaNotizia().getLink();
                SendMessage notiziaTech = new SendMessage(String.valueOf(chat_idone), contenuto);
                notiziaTech.setReplyMarkup(creatoreRowsInLine(perInput));

                /* contenuto += "\n" + "  TITOLO: " + temp.getTitolo() + "\n" + "  AUTORE " + temp.getAuthor() + "\n" +
                            "  DESCRIPTION " + temp.getDescription() + "\n" + "  DATA " + temp.getData();*/

                try{
                    execute(notiziaTech);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if(call_data.equals("spettacolissimo")){
                ArrayList<InlineKeyboardButton> perInput = creatorePulsantiNotizia();

                ArrayList<String> nuovo = categorieDisponibili.listaUrl("Spettacolo");
                int posizioneRandomInFeed = (int) (Math.random()*nuovo.size());
                feedDinamico= new FeedObj(nuovo.get(posizioneRandomInFeed), "NotizieSpettacolo.json");

                String contenuto= feedDinamico.getNuovaNotizia().getLink();
                SendMessage notiziaSportiva = new SendMessage(String.valueOf(chat_idone), contenuto);
                notiziaSportiva.setReplyMarkup(creatoreRowsInLine(perInput));

                /* contenuto += "\n" + "  TITOLO: " + temp.getTitolo() + "\n" + "  AUTORE " + temp.getAuthor() + "\n" +
                            "  DESCRIPTION " + temp.getDescription() + "\n" + "  DATA " + temp.getData();*/

                try{
                    execute(notiziaSportiva);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (call_data.equals("votonissimo")) {
                SendMessage N = new SendMessage();
                N.setChatId(chat_idone);
                N.setText("aggiungi o modifica il tuo voto che deve esser compreso tra 0 e 10");
                confermaVoto=true;
                try{
                    execute(N);
                } catch (TelegramApiException e) { //da sistemare sti catch
                    throw new RuntimeException(e);
                } catch(InputMismatchException E){
                    SendMessage S= new SendMessage(String.valueOf(chat_idone), "Non hai inserito un numero, riprova");
                }
            }

            if (call_data.equals("commentissimo")) {

                SendMessage chiediRisposta = new SendMessage();
                chiediRisposta.setChatId(String.valueOf(chat_idone));
                chiediRisposta.setText("per commentare la notizia rispondi al messaggio ");
                verifica = true; //TODO cambiare verifica in un nome che si capisca meglio


                try{
                    execute(chiediRisposta);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (call_data.equals("nuovissima")) {

                ArrayList<InlineKeyboardButton> perInput = creatorePulsantiNotizia();

                this.linkNuova = feedDinamico.getNuovaNotizia().getLink();
                SendMessage nuovaNotiziaMessaggio = new SendMessage(String.valueOf(chat_idone), linkNuova);
                nuovaNotiziaMessaggio.setReplyMarkup(creatoreRowsInLine(perInput));

                try{
                    execute(nuovaNotiziaMessaggio);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }



            if(call_data.equals("daRegistrare"))
            {
                //controlliamo se l'utente si era già registrato
                try {
                    boolean controlloId=gestoreDegliUtenti.checkChatId(gestoreDegliUtenti.readJsonLista(), chat_idone);
                    if(controlloId==true)
                    {
                        //"sei già registrato"
                        SendMessage S=new SendMessage(String.valueOf(chat_idone), "te sei già registrato che cazzo ti registri a fare");
                        try
                        {
                            execute(S);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }


                SendMessage primo = new SendMessage();
                primo.setText("inserisci il tuo nome utente");
                primo.setChatId(chat_idone);

                try{
                    execute(primo);

                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                registrazione=true;

            }

            if(call_data.equals("registrato")){

                SendMessage primo = new SendMessage();
                primo.setText("inserisci il tuo nome utente inserito in fase di registrazione");
                primo.setChatId(chat_idone);

                try{
                    execute(primo);

                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

                accesso=true;

            }

            if(call_data.equals("mondocommenti")){
                SendMessage start = new SendMessage(String.valueOf(chat_idone), "Commenti da tutto il mondo 🌍👇🏼");
                SendMessage commenti = new SendMessage(String.valueOf(chat_idone), gestoreDeiFeedback.outputFeedbackCommenti(feedDinamico.getCurrentNotizia()));
                try{
                    execute(start);
                    execute(commenti);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if(call_data.equals("mondovoti")){
                SendMessage start = new SendMessage();
                start.setChatId(chat_idone);
                start.setText("La media voti di questa news ⭐️👇🏼");
                Double D = gestoreDeiFeedback.outputFeedbackVotiMedia(feedDinamico.getCurrentNotizia());

                String finale = "🌞 "+  D.toString() + " 🌞";

                SendMessage risposta = new SendMessage(String.valueOf(chat_idone),finale);

                try{
                    execute(start);
                    execute(risposta);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }


        }

        //DA QUI PARTONO I COMANDI DEL BOT, QUINDI FINISCONO LE CALLBACKS

        if (update.hasMessage()) {

            //NON FUNZIONA ATTUALMENTE, RICHIEDO ASSISTENZA
            int data = update.getMessage().getDate();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String nomeUtente= update.getMessage().getFrom().getUserName();




            {



                if(update.getMessage().getText().equals("/start")){
                    //LOGOUT SECTION
                    try
                    {
                        ArrayList<Utente> listaTemporanea=gestoreDegliUtenti.readJsonLista();
                        for(Utente u : listaTemporanea)
                        {
                            if(chat_id==u.getChatId())
                            {
                                u.setOnline(false);
                                gestoreDegliUtenti.writeJson(listaTemporanea);
                                System.out.println("DEBUG: ho fatto logout aye aye");
                                break;
                            }
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    SendMessage messaggioDiBenvenuto = new SendMessage();
                    messaggioDiBenvenuto.setChatId(String.valueOf(chat_id));
                    messaggioDiBenvenuto.setText("Benvenuto in FireNewsBot☄️" + "\n" + "\n"+
                            "Progetto interamente sviluppato da Massimo Andres, Francesco Borcassa e Parsa Baghieri 🌞"
                            + "\n"+
                            "\n"+
                            "Se sei alle prime armi clicca pure il comando /help da tastiera 💡"
                            + "\n"+
                            "\n"+
                            "Buon divertimento 📚");

                    SendMessage risposta = new SendMessage();
                    risposta.setText("Utente, sei gia registrato o ti devi registrare? 😯");
                    risposta.setChatId(String.valueOf(chat_id));

                    SendMessage rispostaNuova = login(risposta);

                    try{
                        execute(messaggioDiBenvenuto);
                        execute(rispostaNuova);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(update.getMessage().getText().equals("/login"))
                {
                    SendMessage primo = new SendMessage();
                    primo.setText("inserisci il tuo nome utente inserito in fase di registrazione");
                    primo.setChatId(chat_id);

                    try{
                        execute(primo);

                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    accesso=true;
                    return;
                }

                if(update.getMessage().getText().equals("/signup"))
                {
                    //controlliamo se un utente con lo stesso chatid esiste già
                    try {
                        boolean controlloId=gestoreDegliUtenti.checkChatId(gestoreDegliUtenti.readJsonLista(), chat_id);
                        if(controlloId==true)
                        {
                            //"sei già registrato"
                            SendMessage S=new SendMessage(String.valueOf(chat_id), "te sei già registrato che cazzo ti registri a fare");
                            try
                            {
                                execute(S);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                            return;
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    SendMessage primo = new SendMessage();
                    primo.setText("inserisci il tuo nome utente");
                    primo.setChatId(chat_id);

                    try{
                        execute(primo);

                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    registrazione=true;
                    return;
                }

                if(update.getMessage().getText().equals("/logout"))
                {
                    try
                    {
                        ArrayList<Utente> listaTemporanea=gestoreDegliUtenti.readJsonLista();
                        for(Utente u : listaTemporanea)
                        {
                            if(chat_id==u.getChatId())
                            {
                                u.setOnline(false);
                                gestoreDegliUtenti.writeJson(listaTemporanea);
                                System.out.println("DEBUG: ho fatto logout aye aye");
                                break;
                            }
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }


                if(update.getMessage().getText().equals("/help")){

                    //controllo se l'utente è logged
                    try{
                        ArrayList<Utente> listaTemporaneaU=gestoreDegliUtenti.readJsonLista();
                        if (listaTemporaneaU.size()==0)
                        {
                            SendMessage S=new SendMessage(String.valueOf(chat_id), "AH! non esiste nessun utente registrato cosa ti faccio entrate");
                            execute(S);
                            return;
                        }
                        for(Utente u : listaTemporaneaU)
                        {
                            if(chat_id==u.getChatId())
                            {
                                if(u.getOnline()==false)
                                {
                                    SendMessage S=new SendMessage(String.valueOf(chat_id), "AH! te mica sei loggato bitch");
                                    try
                                    {
                                        execute(S);
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                    return;
                                }
                            }
                        }

                        SendMessage messaggioInformativo = new SendMessage();
                        messaggioInformativo.setChatId(String.valueOf(chat_id));
                        messaggioInformativo.setText("Ecco alcune informazioni utili: ️" + "\n" + "\n"+
                                "comando /sceglicategoria : usalo per cambiare la tua categoria di interesse 🎲"
                                + "\n"+
                                "\n"+
                                "comando /login : usalo per autenticarti in FireNews 🫶🏻"
                                + "\n"+
                                "\n"+
                                "comando /signup : usalo per registrarti ed usare i nostri servizi 📈");


                        execute(messaggioInformativo);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    return;

                }

                if(accesso==true){

                    String nomeTemp=update.getMessage().getText();
                    utenteTemporaneo.setUserName(nomeTemp);
                    SendMessage secondo = new SendMessage();
                    secondo.setText("ora inserisci la tua password");


                    secondo.setChatId(chat_id);

                    try{
                        execute(secondo);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    accesso=false;
                    confermaAccesso=true;
                    return;
                }

                if(confermaAccesso==true){

                    String passwordTemp = update.getMessage().getText();
                    utenteTemporaneo.setPassword(passwordTemp);

                    gestoreGsonUtentone G = new gestoreGsonUtentone();
                    boolean settaggioOnline=false;

                    settaggioOnline=  G.verificaUtenteJson(utenteTemporaneo);

                    if(settaggioOnline==true){
                        SendMessage S = new SendMessage(String.valueOf(chat_id), "Corretto, da adesso sei ufficialmente ONLINE ✅");
                        try{
                            execute(S);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        confermaAccesso=false;
                        return;
                    }
                    else
                    {
                        SendMessage M = new SendMessage(String.valueOf(chat_id), "Password o Username errati, riprova ⛔️");
                        try{
                            execute(M);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        confermaAccesso=false;
                        return;
                    }




                }

                if(registrazione==true){

                    String nomeTemp=update.getMessage().getText();
                    utenteTemporaneo.setUserName(nomeTemp);
                    SendMessage secondo = new SendMessage();
                    secondo.setText("ora inserisci la tua password");
                    utenteTemporaneo.setChatId(chat_id);


                    secondo.setChatId(chat_id);

                    try{
                        execute(secondo);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    registrazione=false;
                    confermaRegistrazione=true;

                    return;

                }

                if(confermaRegistrazione==true){

                    String passwordTemp = update.getMessage().getText();
                    utenteTemporaneo.setPassword(passwordTemp);
                    gestoreGsonUtentone G = new gestoreGsonUtentone();
                    boolean messaggione=true;

                    try {
                        messaggione= G.aggiungiUtenteJSON(utenteTemporaneo);

                    } catch (IOException e) {
                        System.out.println("NON SONO RIUSCITO A TROVARE LA PASSWORD");
                    }

                    if(messaggione==true){
                        SendMessage S = new SendMessage();
                        S.setText("REGISTRAZIONE ANDATA A BUON FINE");
                        S.setChatId(chat_id);
                        utenteTemporaneo.setUserName(null);
                        utenteTemporaneo.setPassword(null);
                        try{
                            execute(S);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if(!messaggione==true){
                        SendMessage S = new SendMessage();
                        S.setText("OPS, SEMBRA CHE TU SIA GIA REGISTRATO");
                        S.setChatId(chat_id);
                        utenteTemporaneo.setUserName(null);
                        utenteTemporaneo.setPassword(null);
                        try{
                            execute(S);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    confermaRegistrazione=false;
                    return;

                }




                if(verifica==true) //COMMENTI
                {
                    try {
                        gestoreDeiFeedback.aggiungiCommento(feedDinamico.getCurrentNotizia(), nomeUtente, message_text); //qua dentro la parentesi i comandi magici per prendere il commento e l'user
                        SendMessage verificona = new SendMessage();
                        verificona.setChatId(String.valueOf(chat_id));
                        verificona.setText("Commento aggiunto correttamente 💘 ");
                        execute(verificona);
                        verifica=false;
                        return; //giusto?
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                }
                if(confermaVoto==true){
                    try {
                        int voto = Integer.parseInt(update.getMessage().getText());
                        if(voto>=0 && voto <=10){
                            gestoreDeiFeedback.aggiungiVoto(feedDinamico.getCurrentNotizia(), nomeUtente, Integer.parseInt(update.getMessage().getText()));
                            SendMessage S = new SendMessage();
                            S.setChatId(chat_id);
                            S.setText("Voto aggiunto correttamente 📈");

                            try{
                                execute(S);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        confermaVoto=false;
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }



                if (update.getMessage().getText().equals("/sceglicategoria"))
                {
                    try
                    {
                        ArrayList<Utente> listaTemporaneaU=gestoreDegliUtenti.readJsonLista();
                        if (listaTemporaneaU.size()==0)
                        {
                            SendMessage S=new SendMessage(String.valueOf(chat_id), "AH! non esiste nessun utente registrato cosa ti faccio entrate");
                            execute(S);
                            return;
                        }
                        for(Utente u : listaTemporaneaU)
                        {

                            if(chat_id==u.getChatId())
                            {
                                if(u.getOnline()==false)
                                {
                                    SendMessage S=new SendMessage(String.valueOf(chat_id), "AH! te mica sei loggato bitch");
                                    try
                                    {
                                        execute(S);
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                    return; //?????? right
                                }
                            }
                        }

                        InlineKeyboardButton rispostaSport = new InlineKeyboardButton();
                        rispostaSport.setText("Sport ⚽️");
                        rispostaSport.setCallbackData("sportissimo");


                        InlineKeyboardButton rispostaPolitica = new InlineKeyboardButton();
                        rispostaPolitica.setText("Politica 💰");
                        rispostaPolitica.setCallbackData("politicissima");

                        InlineKeyboardButton rispostaSpettacolo = new InlineKeyboardButton();
                        rispostaSpettacolo.setText("Spettacolo 🎬");
                        rispostaSpettacolo.setCallbackData("spettacolissimo");

                        InlineKeyboardButton rispostaEconomia = new InlineKeyboardButton();
                        rispostaEconomia.setText("Economia 💲");
                        rispostaEconomia.setCallbackData("soldissimo");

                        InlineKeyboardButton rispostaTech = new InlineKeyboardButton();
                        rispostaTech.setText("Tech 👨🏼‍💻");
                        rispostaTech.setCallbackData("techissimo");

                        ArrayList<InlineKeyboardButton> perInput = new ArrayList<>();
                        perInput.add(rispostaSport);
                        perInput.add(rispostaPolitica);
                        perInput.add(rispostaSpettacolo);
                        perInput.add(rispostaEconomia);
                        perInput.add(rispostaTech);

                        SendPhoto nomeSendPhoto = SendPhoto.builder().chatId(String.valueOf(chat_id)).photo(new InputFile("https://www.theverge.com/2022/2/15/22935080/facebook-meta-news-feed-renaming-branding-political-content-misinformation")).build();
                        nomeSendPhoto.setReplyMarkup(creatoreRowsInLine(perInput));
                        execute(nomeSendPhoto);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }



                //METODO DI BACKUPPPPPPPPPPPP

                if (message_text.equals("/fotoInnocente")) {
                    var foto = SendPhoto.builder().chatId(String.valueOf(chat_id)).photo(new InputFile("https://static-ca-cdn.eporner.com/gallery/cl/RH/bmnjxXXRHcl/707023-hitomi-tanaka-nude.jpg")).build();
                    var valutazioni = SendPoll.builder().chatId(String.valueOf(chat_id)).question(" valuta questa news! ").option("1/3 😇").option("4/6 😄").option("7/10 🤩 ").build();
                    var randomico = SendDice.builder().chatId(String.valueOf(chat_id)).emoji("🎲").build();
                    try {
                        execute(foto);
                        execute(valutazioni);
                        execute(randomico);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            //CHIUSURA FUNZIONE ONUPDATERECIVED
        }



    }



    @Override
    public String getBotUsername() {
        return "fire_24_bot";
    }

    @Override
    public String getBotToken() {
        return "5363385439:AAF8HvDTYy59hsjRdI8ZtLeSobUghPJa4HU";
    }
}






