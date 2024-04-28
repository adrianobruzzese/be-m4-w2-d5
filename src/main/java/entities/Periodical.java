package org.example.entities;

import java.util.Random;

/**
 * Classe che rappresenta un periodico, estendendo PrintedMaterial.
 * Gestisce le informazioni specififiche per le pubblicazioni periodiche, come la frequenza di pubblicazione.
 */
public class Periodical extends PrintedMaterial {

    // Attributi specifici per i periodici
    private PublicationFrequency frequency;

    /**
     * Costruttore per creare un periodico.
     * @param title Titolo del periodico.
     * @param pagesNumber Numero di pagine del periodico.
     * Inizializza la frequenza del periodico con un valore casuale tra i valori disponibili.
     */
    public Periodical(String title, long pagesNumber) {
        super(title, pagesNumber);
        Random random = new Random();
        this.frequency = PublicationFrequency.values()[random.nextInt(PublicationFrequency.values().length)];
    }

    /**
     * Fornisce una rappresentazione testuale del periodico.
     * @return una stringa che descrive il periodico con titolo, frequenza, anno di pubblicazione, numero di pagine e ISBN.
     */
    @Override
    public String toString() {
        return "MAGAZINE: '" + title + "', frequency: " + frequency +
                ", publication year: " + publicationYear +
                ", pages: " + numberOfPages +
                ", ISBN: " + identifier + ".";
    }
}
