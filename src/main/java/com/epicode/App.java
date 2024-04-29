package com.epicode;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import com.epicode.entities.PrintedMaterial;
import com.epicode.entities.Literature;
import com.epicode.entities.Periodical;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Classe principale per gestire un catalogo di opere letterarie e periodici.
 * Permette operazioni di aggiunta, rimozione, ricerca e salvataggio/caricamento dei dati.
 */
public class App {

    private static List<PrintedMaterial> catalogue = new ArrayList<>();

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
        catalogue.addAll(generateItems(50, App::createRandomLiterature));
        catalogue.addAll(generateItems(50, App::createRandomPeriodical));
    }

    private static List<PrintedMaterial> generateItems(int count, Supplier<PrintedMaterial> supplier) {
        return Stream.generate(supplier).limit(count).collect(Collectors.toList());
    }

    private static Literature createRandomLiterature() {
        Faker faker = new Faker(Locale.ITALY);
        return new Literature(faker.book().title(), faker.number().randomNumber(), faker.lordOfTheRings().character(), faker.book().genre());
    }

    private static Periodical createRandomPeriodical() {
        Faker faker = new Faker();
        return new Periodical(faker.commerce().productName(), faker.number().randomNumber());
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
                return true; // Esci dal programma
            default:
                System.err.println("Scelta non valida, riprova.");
        }
        return false; // Continua il programma
    }

    private static void addElementInteractive(Scanner scanner) {
        System.out.println("Vuoi aggiungere un'opera letteraria (L) o un periodico (P)?");
        String type = scanner.nextLine().toUpperCase();
        if (type.equals("L")) {
            addLiteratureInteractive(scanner);
        } else if (type.equals("P")) {
            addPeriodicalInteractive(scanner);
        } else {
            System.err.println("Carattere non valido!");
        }
    }

    private static void addLiteratureInteractive(Scanner scanner) {
        System.out.println("Inserisci titolo, numero di pagine, autore e genere dell'opera letteraria:");
        String title = scanner.nextLine();
        int pages = Integer.parseInt(scanner.nextLine());
        String author = scanner.nextLine();
        String genre = scanner.nextLine();
        Literature literature = new Literature(title, pages, author, genre);
        catalogue.add(literature);
        System.out.println("Opera letteraria aggiunta al catalogo!");
    }

    private static void addPeriodicalInteractive(Scanner scanner) {
        System.out.println("Inserisci titolo e numero di pagine del periodico:");
        String title = scanner.nextLine();
        int pages = Integer.parseInt(scanner.nextLine());
        Periodical periodical = new Periodical(title, pages);
        catalogue.add(periodical);
        System.out.println("Periodico aggiunto al catalogo!");
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
                .filter(e -> e instanceof Literature)  // Prima filtriamo per assicurarci che sia Literature
                .map(e -> (Literature) e)              // Ora possiamo fare il cast in modo sicuro
                .filter(l -> l.getAuthor().equalsIgnoreCase(author))
                .forEach(System.out::println);
    }



    private static void saveCatalogueToDisk() {
        try {
            File file = new File("src/catalogue.txt");
            FileUtils.write(file, catalogue.stream().map(PrintedMaterial::toString).collect(Collectors.joining("\n")), StandardCharsets.UTF_8);
            System.out.println("Catalogo salvato su disco.");
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio del file: " + e.getMessage());
        }
    }

    private static void printCatalogue() {
        catalogue.forEach(System.out::println);
    }
}
