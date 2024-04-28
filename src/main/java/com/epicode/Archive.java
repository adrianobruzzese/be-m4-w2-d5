package com.epicode;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.example.entities.BibliographicalElements;
import org.example.entities.Book;
import org.example.entities.Magazine;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Classe per gestire un catalogo di libri e riviste.
 * Permette operazioni di aggiunta, rimozione, ricerca e salvataggio/caricamento dei dati.
 */
public class Archive {

    private static List<BibliographicalElements> catalogue = new ArrayList<>();

    public static void main(String[] args) {
        initializeCatalogue();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("---------------Benvenuto in EpiBooks!------------------");
        while (!exit) {
            showMenu();
            String choice = scanner.nextLine().toUpperCase();
            exit = handleUserInput(choice, scanner);
        }
        System.out.println("Programma terminato. Arrivederci!");
        scanner.close();
    }

    private static void initializeCatalogue() {
        catalogue.addAll(generateItems(50, Archive::createRandomBook));
        catalogue.addAll(generateItems(50, Archive::createRandomMagazine));
    }

    private static List<BibliographicalElements> generateItems(int count, Supplier<BibliographicalElements> supplier) {
        return Stream.generate(supplier).limit(count).collect(Collectors.toList());
    }

    private static Book createRandomBook() {
        Faker faker = new Faker(Locale.ITALY);
        return new Book(faker.book().title(), faker.number().randomNumber(), faker.lordOfTheRings().character(), faker.book().genre());
    }

    private static Magazine createRandomMagazine() {
        Faker faker = new Faker();
        return new Magazine(faker.commerce().productName(), faker.number().randomNumber());
    }

    private static void showMenu() {
        System.out.println("Inserisci una delle opzioni:");
        System.out.println("A - Aggiungi elemento");
        System.out.println("B - Rimuovi elemento");
        System.out.println("C - Cerca per ISBN");
        System.out.println("D - Cerca per anno di pubblicazione");
        System.out.println("E - Cerca per autore");
        System.out.println("F - Salva il catalogo");
        System.out.println("G - Visualizza il catalogo");
        System.out.println("0 - Esci");
    }

    private static boolean handleUserInput(String choice, Scanner scanner) {
        switch (choice) {
            case "A":
                addElementInteractive(scanner);
                break;
            case "B":
                removeElementByIsbnInteractive(scanner);
                break;
            case "C":
                searchByIsbnInteractive(scanner);
                break;
            case "D":
                searchByYearInteractive(scanner);
                break;
            case "E":
                searchByAuthorInteractive(scanner);
                break;
            case "F":
                saveCatalogueToDisk();
                break;
            case "G":
                printCatalogue();
                break;
            case "0":
                return true; // Exit the program
            default:
                System.err.println("Scelta non valida, riprova.");
        }
        return false; // Continue the program
    }

    private static void addElementInteractive(Scanner scanner) {
        System.out.println("Vuoi aggiungere un libro (B) o una rivista (M)?");
        String type = scanner.nextLine().toUpperCase();
        if (type.equals("B")) {
            addBookInteractive(scanner);
        } else if (type.equals("M")) {
            addMagazineInteractive(scanner);
        } else {
            System.err.println("Carattere non valido!");
        }
    }

    private static void addBookInteractive(Scanner scanner) {
        System.out.println("Inserisci titolo, numero di pagine, autore e genere del libro:");
        String title = scanner.nextLine();
        int pages = Integer.parseInt(scanner.nextLine());
        String author = scanner.nextLine();
        String genre = scanner.nextLine();
        Book book = new Book(title, pages, author, genre);
        catalogue.add(book);
        System.out.println("Libro aggiunto al catalogo!");
    }

    private static void addMagazineInteractive(Scanner scanner) {
        System.out.println("Inserisci titolo e numero di pagine della rivista:");
        String title = scanner.nextLine();
        int pages = Integer.parseInt(scanner.nextLine());
        Magazine magazine = new Magazine(title, pages);
        catalogue.add(magazine);
        System.out.println("Rivista aggiunta al catalogo!");
    }

    private static void removeElementByIsbnInteractive(Scanner scanner) {
        System.out.println("Inserisci l'ISBN dell'elemento da rimuovere:");
        long isbn = Long.parseLong(scanner.nextLine());
        if (catalogue.removeIf(e -> e.getId() == isbn)) {
            System.out.println("Elemento rimosso.");
        } else {
            System.err.println("ISBN non trovato!");
        }
    }

    private static void searchByIsbnInteractive(Scanner scanner) {
        System.out.println("Inserisci l'ISBN da cercare:");
        long isbn = Long.parseLong(scanner.nextLine());
        catalogue.stream()
                .filter(e -> e.getId() == isbn)
                .findFirst()
                .ifPresent(System.out::println);
    }

    private static void searchByYearInteractive(Scanner scanner) {
        System.out.println("Inserisci l'anno di pubblicazione da cercare:");
        int year = Integer.parseInt(scanner.nextLine());
        catalogue.stream()
                .filter(e -> e.getPublicationYear() == year)
                .forEach(System.out::println);
    }

    private static void searchByAuthorInteractive(Scanner scanner) {
        System.out.println("Inserisci l'autore da cercare:");
        String author = scanner.nextLine();
        catalogue.stream()
                .filter(e -> e instanceof Book)
                .map(e -> (Book) e)
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .forEach(System.out::println);
    }

    private static void saveCatalogueToDisk() {
        try {
            File file = new File("src/catalogue.txt");
            FileUtils.write(file, catalogue.stream().map(BibliographicalElements::toString).collect(Collectors.joining("\n")), StandardCharsets.UTF_8);
            System.out.println("Catalogo salvato su disco.");
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio del file: " + e.getMessage());
        }
    }

    private static void printCatalogue() {
        catalogue.forEach(System.out::println);
    }
}
