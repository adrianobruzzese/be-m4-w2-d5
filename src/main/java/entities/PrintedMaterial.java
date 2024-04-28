package com.epicode.entities;

import java.time.LocalDate;
import java.util.Random;

/**
 * Classe astratta che rappresenta un elemento bibliografico generico e
 * fornisce una struttura di base per elementi come libri e rivise con attributi comuni.
 */
public abstract class PrintedMaterial {

    // Attributi comuni a tutti gli elementi bibliografici
    protected long identifier;
    protected String title;
    protected int publicationYear;
    protected long numberOfPages;

    /**
     * Costruttore per creare un elemento bibliografico.
     * @param title Titolo dell'elemento.
     * @param pagesNumber Numero di pagine dell'elemento. Deve essere almeno 10.
     */
    public PrintedMaterial(String title, long pagesNumber) {
        Random random = new Random();
        this.identifier = Math.abs(random.nextLong()); // Assegna un identificatore unico
        this.title = title;
        this.publicationYear = random.nextInt(1455, LocalDate.now().getYear()); // Anno di pubblicazione casuale dall'anno della nascita della stampa fino all'anno corrente
        if (pagesNumber < 10) {
            throw new IllegalArgumentException("Il numero di pagine deve essere almeno 10.");
        }
        this.numberOfPages = pagesNumber;
    }

    // Metodo per ottenere l'identificatore unico dell'elemento
    public long getId() {
        return identifier;
    }

    // Metodo per ottenere il titolo dell'elemento
    public String getTitle() {
        return title;
    }

    // Metodo per ottenere l'anno di pubblicazione
    public int getPublicationYear() {
        return publicationYear;
    }
}
