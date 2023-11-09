//Conny.fu@iths.se Conny Fu
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

public class Main {
    static ArrayList<Product> katalog = new ArrayList<>();
    static ArrayList<VarukorgArtikel> varukorg = new ArrayList<>();
    static boolean isAdmin = false; // Kontrollerar om användaren är admin eller inte

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        login(); // Inloggning i början av programmet så man väljer mellan kund och admin
        basKatalog(); // För att ha produkter i början så det är inte tomt.


        if (isAdmin) {
            changeConsoleTextColor(Color.RED);
            System.out.println("Du är inloggad som administratör");
        } else {
            changeConsoleTextColor(Color.YELLOW);
            System.out.println("Du är inloggad som en kund");
        }

        String[] menyChoices = {
                "1. Vill du lägga till ett produkt till varukorgen.",
                "2. Vill du lägga till en ny produkt i katalogen.",
                "3. Se katalogen.",
                "4. Se varukorgen",
                "5. Ta bort produkt i katalogen.",
                "6. Lägg till rea på nuvarande produkt.",
                "7. Avsluta programmet."
        };

        while (true) {
            System.out.println("--------------");
            System.out.println("Välkommen till Frukt och Grönt. Vad vill du göra?");
            for (String menyChoice : menyChoices)
                System.out.println(menyChoice);

            try {
                int userInput = scanner.nextInt();

                switch (userInput) {
                    case 1 -> {
                        System.out.println("Du har valt att lägga till en produkt i varukorgen.");
                        addToVarukorg();
                    }
                    case 2 -> {
                        System.out.println("Du har valt att lägga till en ny produkt i katalogen.");
                        addToKatalog();
                    }
                    case 3 -> {
                        System.out.println("Du har valt att se katalogen.");
                        displayKatalog();
                    }
                    case 4 -> {
                        System.out.println("Du har valt att se varukorgen.");
                        displayVarukorg();
                    }
                    case 5 -> {
                        System.out.println("Du har valt att ta bort ett produkt i katalogen.");
                        removeKatalog();
                    }
                    case 6 -> {
                        System.out.println("Du har valt lägga till rea på en produkt.");
                        addSaleToProduct();
                    }
                    case 7 -> {
                        System.out.println("Avslutar programmet.");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Ogiltigt val. Var god och välj alternativen.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Ogiltig inmatning. Var god ange en siffra.");
                scanner.nextLine();
            }
        }
    }
    private static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Vill du logga in som admin eller kund? (admin/kund): ");
        String userType = scanner.nextLine().toLowerCase();

        isAdmin = userType.equals("admin");

        if (isAdmin) {
            System.out.println("Du är inloggad som administratör");
        } else {
            System.out.println("Du är inloggad som en kund");
        }
    }


    private static void addToVarukorg() {
        Scanner input = new Scanner(System.in);

        System.out.println("Ange produktens namn för att lägga till i varukorgen: ");
        String productName = input.nextLine();

        // Söker produkten i katalogen om det finns eller inte.
        Product selectedProduct = null;
        for (Product product : katalog) {
            if (product.getName().equalsIgnoreCase(productName)) {
                selectedProduct = product;
                break;
            }
        }
        if (selectedProduct == null) {
            System.out.println("Produkten hittades inte i katalogen.");
        } else {
            System.out.println("Ange antal av mängd av produkten att lägga till i varukorgen: ");
            int quantity = input.nextInt();

            // Här skapar vi ny VarukorgArtikel
            VarukorgArtikel varukorgArtikel = new VarukorgArtikel(selectedProduct, quantity);
            varukorg.add(varukorgArtikel);
            System.out.println(quantity + " st av " + productName + " har lagts till i varukorgen.");
        }
    }

    // Uppdaterade addToKatalog för att kontrollera om användaren är admin eller inte.
    private static void addToKatalog() {
        if (!isAdmin) {
            System.out.println("Endast administratörer kan lägga till produkter i katalogen.");
            return;
        }
        Scanner input = new Scanner(System.in);

        System.out.println("Ange produktnamn:");
        String name = input.nextLine().trim();

        System.out.println("Ange styckpris: ");
        double price = input.nextDouble();

        System.out.println("Ange kilopriset: ");
        double priceKG = input.nextDouble();
        input.nextLine();

        System.out.println("Ange varugrupp");
        String productGroup = input.nextLine();

        Product newProduct = new Product(name, price, priceKG, productGroup);
        katalog.add(newProduct);
        System.out.println(name + " | " + String.format("%.2f kr/st", price) + " | "
                + String.format("%.2f kr/kg", priceKG) + " | " + productGroup + " är tillagd i katalogen.");
        choice2();
    }

    public static void choice2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Lägg till produkt i katalogen.");
        if (isAdmin) {
            System.out.println("2. Ta bort produkt från katalogen.");
        }
        System.out.println("3. Gå tillbaka till huvudmenyn.");

        String choice2 = scanner.next();
        switch (choice2) {
            case "1" -> addToKatalog();
            case "2" -> {
                if (isAdmin) {
                    removeKatalog();
                } else {
                    System.out.println("Ogiltigt val.");
                }
            }
            case "3" -> {
            }
            // Gå tillbaka till huvudmenyn.
            default -> System.out.println("Ogiltigt val.");
        }
    }

    private static void displayKatalog() {
        System.out.println("Katalogen:");
        for (Product product : katalog) {
            System.out.println(product.toString());
        }
    }

    // Uppdaterade removeKatalog för att kontrollera om användaren är admin eller inte.
    private static void removeKatalog() {
        if (!isAdmin) {
            System.out.println("Endast administratörer kan ta bort produkter från katalogen.");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Vad heter produkten som du vill ta bort från katalogen?: ");
        String productName = input.nextLine();

        Product productToRemove = null;
        for (Product product : katalog) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productToRemove = product;
                break;
            }
        }
        if (productToRemove != null) {
            katalog.remove(productToRemove);
            System.out.println(productName + " har tagits bort från katalogen.");
        } else {
            System.out.println("Produkten hittades inte i katalogen.");
        }
    }

    private static void basKatalog() {
        // Detta lägger till några produkter i början
        katalog.add(new Product("Äpple", 10.0, "Frukt", 5.0));
        katalog.add(new Product("Banan", 8.0, "Frukt", 4.0));
        katalog.add(new Product("Potatis", 12.0, "Rotfrukt", 6.0));
        katalog.add(new Product("Kiwi", 15.0, "Frukt", 7.0));
        katalog.add(new Product("Apelsin", 7.0, "Frukt", 3.0));
        katalog.add(new Product("Ananas", 11.0, "Frukt", 15.0));
    }

    private static void displayVarukorg() {
        System.out.println("Varukorgen:");
        for (VarukorgArtikel varukorgArtikel : varukorg) {
            System.out.println(varukorgArtikel.toString());
        }
    }
    private static void addSaleToProduct() {
        if (!isAdmin) {
            System.out.println("Endast administratörer kan lägga till rea på produkter!");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Ange produktens namn som du vill sätta rea på.");
        String productName = input.nextLine();

        Product selectedProduct = null;
                for (Product product : katalog) {
                    if (product.getName().equalsIgnoreCase(productName)) {
                        selectedProduct = product;
                                break;
                    }
                }
        if (selectedProduct == null) {
            System.out.println("Produkten hittades inte i katalogen.");
        } else {
            System.out.println("Ange rea-pris: ");
            double salePrice = input.nextDouble();
            System.out.println("Ange rea-mängd (t.ex 2 för 20kr)");
            int saleQuantity = input.nextInt();
            selectedProduct.setSale(salePrice, saleQuantity);

            System.out.println("Rea har lagts till på " + productName + ": " +
                    "Nuvarande pris: " + selectedProduct.getPrice() + " kr/st, " +
                    "Rea-pris: " + selectedProduct.getSalePrice() + " kr/st vid köp av minst " +
                    selectedProduct.getSaleQuantity() + " st.");
        }
    }

    private static void changeConsoleTextColor(Color color) {
        System.out.print("\033[38;2;" + color.getRed() + ";" + color.getGreen() + ";" + color.getBlue() + "m");
    }
}