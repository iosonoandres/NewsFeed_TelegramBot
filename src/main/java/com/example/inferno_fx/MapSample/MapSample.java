package com.example.inferno_fx.MapSample;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class MapSample {


    private static Scanner tastiera = new Scanner(System.in);
    private TreeMap<String, ArrayList<String>> mappa;

    public MapSample(){
        this.mappa = new TreeMap<>();
    }

    //getter e setter


    public void setMappa(TreeMap<String, ArrayList<String>> mappa) {
       this.mappa = mappa;
    }

    public TreeMap<String, ArrayList<String>> getMappa() {
        return this.mappa;
    }

    public void aggiungiCategoria(String categoria){
        String userInput = "";
            ArrayList<String> listaURL = new ArrayList<>();
            while(!userInput.equalsIgnoreCase("fine")){
                System.out.println("inserici un'url per " + categoria + ". Per terminare digita 'fine'");
                userInput = tastiera.nextLine();
                if(!userInput.equalsIgnoreCase("fine")) {
                    listaURL.add(userInput);
                }
            this.mappa.put(categoria,listaURL);
        }
    }

    public void aggiungiURL(String categoria, String URL){
        ArrayList<String> listaURL = this.mappa.get(categoria);
        listaURL.add("URL");
        this.mappa.replace(categoria, listaURL);
    }




    public void inizializza(){
        TreeMap<String,ArrayList<String>> mappaSample= this.getMappa();
        ArrayList<String> categorie = new ArrayList<>();
        System.out.println("CATEGORIE MAPPA");
        String userInput = "";
        while(!userInput.equalsIgnoreCase("fine")){
            System.out.println("inserici una categoria. Per terminare digita 'fine'");
            userInput = tastiera.nextLine();
            if(!userInput.equalsIgnoreCase("fine")) {
                categorie.add(userInput);
            }
        }
        System.out.println("URL PER LE CATEGORIE");

        userInput = "";
        for(String categoria: categorie){
            ArrayList<String> listaURL = new ArrayList<>();
            while(!userInput.equalsIgnoreCase("fine")){
                System.out.println("inserici un'url per " + categoria + ". Per terminare digita 'fine'");
                userInput = tastiera.nextLine();
                if(!userInput.equalsIgnoreCase("fine")) {
                    listaURL.add(userInput);
                }
            }
            mappaSample.put(categoria,listaURL);
        }

        setMappa(mappaSample);

    }


    public  String toString(){
        return (this.mappa).toString();
    }


}
