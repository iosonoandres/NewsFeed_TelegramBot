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

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class myBotDef extends TelegramLongPollingBot {

    private Categorie categorieDisponibili = new Categorie("Categorie.json");
    private FeedObj feedDinamico;
    private String linkNuova;
    private GestoreFeedback gestoreDeiFeedback=new GestoreFeedback();

    private Utente utenteTemporaneo = new Utente(null,null);

    private boolean usabile;
    private boolean verifica;
    private boolean registrazione;
    private boolean conferma;

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
        for(InlineKeyboardButton B : input){
            rowInline.add(B);
        }
        rowsInline.add(rowInline);
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

        InlineKeyboardButton rispostaMostraCommenti = new InlineKeyboardButton();
        rispostaMostraCommenti.setText("commenti 🌍");
        rispostaMostraCommenti.setCallbackData("mondocommenti");

        InlineKeyboardButton rispostaMostraMediaVoti = new InlineKeyboardButton();
        rispostaMostraMediaVoti.setText("media voti 💫");
        rispostaMostraMediaVoti.setCallbackData("mondovoti");

        ArrayList<InlineKeyboardButton> perInput = new ArrayList<>();
        perInput.add(rispostaVoto);
        perInput.add(rispostaNuovaNotizia);
        perInput.add(rispostaLasciaUnCommento);
        perInput.add(rispostaMostraCommenti);
        perInput.add(rispostaMostraMediaVoti);
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
                feedDinamico= new FeedObj(nuovo.get(posizioneRandomInFeed));

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
                feedDinamico= new FeedObj(nuovo.get(posizioneRandomInFeed));

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
            if(call_data.equals("spettacolissimo")){
                ArrayList<InlineKeyboardButton> perInput = creatorePulsantiNotizia();

                ArrayList<String> nuovo = categorieDisponibili.listaUrl("Spettacolo");
                int posizioneRandomInFeed = (int) (Math.random()*nuovo.size());
                feedDinamico= new FeedObj(nuovo.get(posizioneRandomInFeed));

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



            if(call_data.equals("daRegistrare")){

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

                if(update.getMessage().getText().equals("/help")){

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

                    try{
                        execute(messaggioInformativo);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    return;

                }

                if(registrazione==true){

                        String nomeTemp=update.getMessage().getText();
                        utenteTemporaneo.setNome(nomeTemp);


                    SendMessage secondo = new SendMessage();
                    secondo.setText("ora inserisci la tua password");


                    secondo.setChatId(chat_id);

                    try{
                        execute(secondo);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    registrazione=false;
                    conferma=true;

                    return;

                }

                if(conferma==true){
                        String passwordTemp = update.getMessage().getText();
                        utenteTemporaneo.setPassword(passwordTemp);
                        System.out.println("BOTTT: "+ utenteTemporaneo.getUserName());
                        gestoreGsonUtentone G = new gestoreGsonUtentone();
                        boolean messaggione=true;

                        try {
                            messaggione= G.aggiungiUtenteJSON(utenteTemporaneo);
                            System.out.println(messaggione);
                            System.out.println(messaggione);

                        } catch (IOException e) {
                            System.out.println("NON SONO RIUSCITO A TROVARE LA PASSWORD");
                        }

                        if(messaggione==true){
                            SendMessage S = new SendMessage();
                            S.setText("REGISTRAZIONE ANDATA A BUON FINE");
                            S.setChatId(chat_id);
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
                        try{
                            execute(S);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    conferma=false;
                        return;

                }




                if(verifica==true) //COMMENTI
                {
                    try {
                        gestoreDeiFeedback.aggiungiCommento(feedDinamico.getCurrentNotizia(), nomeUtente, message_text); //qua dentro la parentesi i comandi magici per prendere il commento e l'user
                        return; //giusto?
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                if(confermaVoto==true){
                    try {
                        int voto = Integer.parseInt(update.getMessage().getText());
                        if(voto>=0 && voto <=10){
                            gestoreDeiFeedback.aggiungiVoto(feedDinamico.getCurrentNotizia(), nomeUtente, Integer.parseInt(update.getMessage().getText()));
                            System.out.println("voto aggiunto correttamente we hope");
                        }

                        confermaVoto=false;
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (update.getMessage().getText().equals("/sceglicategoria")) {



                    InlineKeyboardButton rispostaSport = new InlineKeyboardButton();
                    rispostaSport.setText("Sport ⚽️");
                    rispostaSport.setCallbackData("sportissimo");


                    InlineKeyboardButton rispostaPolitica = new InlineKeyboardButton();
                    rispostaPolitica.setText("Politica 💰");
                    rispostaPolitica.setCallbackData("politicissima");

                    InlineKeyboardButton rispostaSpettacolo = new InlineKeyboardButton();
                    rispostaSpettacolo.setText("Spettacolo 🎬");
                    rispostaSpettacolo.setCallbackData("spettacolissimo");

                    ArrayList<InlineKeyboardButton> perInput = new ArrayList<>();
                    perInput.add(rispostaSport);
                    perInput.add(rispostaPolitica);
                    perInput.add(rispostaSpettacolo);

                    SendPhoto nomeSendPhoto = SendPhoto.builder().chatId(String.valueOf(chat_id)).photo(new InputFile("https://www.theverge.com/2022/2/15/22935080/facebook-meta-news-feed-renaming-branding-political-content-misinformation")).build();
                    nomeSendPhoto.setReplyMarkup(creatoreRowsInLine(perInput));

                    try{
                        execute(nomeSendPhoto);
                    } catch (TelegramApiException e) {
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