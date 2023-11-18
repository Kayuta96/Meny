//Conny.fu@iths.se Conny Fu
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

public class Main {
    static ArrayList<Product> katalog = new ArrayList<>();
    static ArrayList<VarukorgArtikel> varukorg = new ArrayList<>();
    static boolean isAdmin = false; // Håller koll om användaren är admin eller inte och sätter de false.

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

        System.out.println("Vill du logga in som admin eller kund? Skriv 'admin' eller tryck in enter: ");
        String userType = scanner.nextLine().toLowerCase();

        isAdmin = userType.equals("admin");

        if (isAdmin) {
            System.out.println("Du är inloggad som administratör");
        } else {
            System.out.println("Du är inloggad som en kund");
        }
    } //Här kan jag lägga till så att man skriver in kod efter "admin"


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

            // Feedback, fixa så att när man uppfyller kraven för rea så separerar det från den ordinära priset.
            int discountQuantity = 0; // Produkter I rea
            int regularQuantity; // Produkter I normala pris

            if (selectedProduct.getSalePrice() > 0 && quantity >= selectedProduct.getSaleQuantity()) {
                int saleQuantity = selectedProduct.getSaleQuantity();
                int remainingQuantity = quantity;
                while (remainingQuantity >= saleQuantity) {
                    discountQuantity += saleQuantity;
                    remainingQuantity -= saleQuantity;
                }
                regularQuantity = remainingQuantity;
            } else {
                regularQuantity = quantity;
            }

            // Här skapar vi ny Varukorgartikel för produkter på rea eller ordinära pris.
            if (discountQuantity > 0 || regularQuantity > 0) {
                VarukorgArtikel cartItem = new VarukorgArtikel(selectedProduct, quantity);
                varukorg.add(cartItem);

                double regularTotalPrice = regularQuantity * selectedProduct.getPrice();
                double discountTotalPrice = discountQuantity * selectedProduct.getSalePrice();

                System.out.println(quantity + " st av " + productName + " har lagts till i varukorgen.");
                System.out.println("Ordinarie pris: " + String.format("%.2f kr", regularTotalPrice));

                if (discountQuantity > 0) {
                    System.out.println("På rea: " + String.format("%.2f kr (REA! %d st för %.2f kr/st)", discountTotalPrice, selectedProduct.getSaleQuantity(), selectedProduct.getSalePrice()));
                }
            }
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
            Product product = varukorgArtikel.getProduct();
            int quantity = varukorgArtikel.getQuantity();
            double totalPrice = varukorgArtikel.getTotalPrice();

            if (product.getSalePrice() > 0) {
                int saleQuantity = product.getSaleQuantity();
                int discountQuantity = quantity / saleQuantity;
                int remainingQuantity = quantity % saleQuantity;

                double totalCost = (discountQuantity * product.getSalePrice());

                // Kontrollera om det finns produkter som inte omfattas av rabatten.
                if (remainingQuantity > 0) {
                    double extraCost = remainingQuantity * product.getPrice();
                    totalCost += extraCost;
                    System.out.println(quantity + "st " + product.getName() +
                            " (Detta produkt är på REA! " + String.format("%.2f kr/st vid köp av minst %d st)" +
                                    " Totalt: %.2f kr (Det tillkommer extra kostnad för %d extra st som är inte behörig för rabatt.)",
                            product.getSalePrice(), product.getSaleQuantity(), totalCost, remainingQuantity));
                } else {
                    System.out.println(quantity + "st " + product.getName() +
                            " (Detta produkt är på REA! " + String.format("%.2f kr/st vid köp av minst %d st)" + " Totalt: %.2f kr",
                            product.getSalePrice(), product.getSaleQuantity(), totalCost));
                }
            } else {
                System.out.println(quantity + "st " + product.getName() +
                        " (Ordinarie pris " + String.format("%.2f kr/st) " + "Totalt: %.2f kr", product.getPrice(), totalPrice));
            }
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
            System.out.println("Ange det nya priset: ");
            double salePrice = input.nextDouble();
            System.out.println("Ange antal produkter man måste köpa för rea: (t.ex 2 för 20kr)");
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