package com.epicode.entities;

public class Literature extends PrintedMaterial {

    // Attributi specifici per le opere letterarie
    private String author;
    private String genre;

    /**
     * Costruisce un'opera letteraria con dettagli specifici
     * @param title Titolo dell'opera.
     * @param pagesNumber Numero di pagine dell'opera
     * @param author Autore dell'opera
     * @param genre Genere letterario dell'opera.
     * @throws IllegalArgumentException se il numero di pagine èinferiore a 10.
     */
    public Literature(String title, long pagesNumber, String author, String genre) {
        super(title, pagesNumber);  // Chiama il costruttore della classe base
        this.author = author;
        this.genre = genre;
    }

    /**
     * Fornisce una rappresentazione testuale dell'opera letteraria.
     * @return una stringa che descrive l'opera con tutti i dettagli rilevanti.
     */
    @Override
    public String toString() {
        return String.format("BOOK: '%s' by %s, genre: %s, ISBN: %d, publication Year: %d, pages: %d.",
                title, author, genre, identifier, publicationYear, numberOfPages);
    }

    /**
     * Ritorna l'autore dell'opera letteraria.
     * @return il nome dell'autore.
     */
    public String getAuthor() {
        return author;
    }
}
