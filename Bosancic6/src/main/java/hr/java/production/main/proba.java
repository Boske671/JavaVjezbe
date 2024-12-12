public class proba {
  public static void main(String[] args) 
  {

    /*
    1.  u binarnu datoteku zapisat neki datum, svoje ime, prezime, ime nekog od jela i cijenu 
2. ⁠dat korisniku da unese path u kojem su datoteke vezane za projekt
3. ⁠ponudit odabir samo binarnih datoteka, i ako korisnik ne odabere kompatibilnu(to je ova iz prvog zad) zatrazit ponovo unos. 
Kad se odabere ta datoteka iz prvog zadatka treba ispisat podatke i usporedit cijenu jela iz datoteke s cijenom svakog jela u programu, i ispisat jel veca ili manja od tog iz datoteke,
i ako je manja zapisat ime jela i njihovu zbrojenu cijenu u meals2.txt
    */
String putanjaDatoteke1 = "dat/podatci.bin"; // Putanja za binarnu datoteku
        String datum = "12.12.2012.";
        String ime = "IME";
        String prezime = "PREZIME";
        String imeJela = "sarma";
        BigDecimal cijenaJela = new BigDecimal(400);


        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate datum1 = LocalDate.parse(datum, dateFormat);


        // Zapisivanje imena i prezimena u binarnu datoteku
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(putanjaDatoteke1))) {
            oos.writeUTF(datum);
            oos.writeUTF(ime); // Zapisuje ime u datoteku
            oos.writeUTF(prezime);  // Zapisuje prezime u datoteku
            oos.writeUTF(imeJela);
            oos.writeDouble(cijenaJela.doubleValue());
            System.out.println("Uspješno zapisano u " + putanjaDatoteke1);
        } catch (IOException e) {
            System.err.println("Greška prilikom zapisivanja u datoteku: " + e.getMessage());
        }








        List<String> putanje = new ArrayList<>();
        putanje.add("dat/podatci.bin");
        putanje.add(AddressesRepository.BINARY_ADDRESSES_FILE_PATH);
        putanje.add(CategoriesRepository.BINARY_CATEGORIES_FILE_PATH);
        putanje.add(ChefsRepository.BINARY_CHEFS_FILE_PATH);
        putanje.add(DeliverersRepository.BINARY_DELIVERERS_FILE_PATH);
        putanje.add(WaitersRepository.BINARY_WAITERS_FILE_PATH);
        putanje.add(IngredientsRepository.BINARY_INGREDIENTS_FILE_PATH);
        putanje.add(MealsRepository.BINARY_MEALS_FILE_PATH);
        putanje.add(OrdersRepository.BINARY_ORDERS_FILE_PATH);
        putanje.add(RestaurantsRepository.BINARY_RESTAURANTS_FILE_PATH);

        System.out.println("Ponudene putanje datoteke su: ");
        int indeks = 1;
        for (String string : putanje) {
            System.out.println(indeks + ".  " + string);
            indeks++;
        }

        int odabir = 0;
        String putanjaDatoteke2 = "asd";
        boolean uvjetPutanja = true;




        do {
            System.out.println("Odaberi jednu od ponudenih putanja upisom broja: ");
            odabir = sc.nextInt();
            if (odabir < 1 || odabir > putanje.size()) {
                System.out.println("Neispravan odabir. Probaj ponovo.");
            }
            else{
                uvjetPutanja = false;
            }

            sc.nextLine(); // Potroši newline
        }
        while (uvjetPutanja) ;






        putanjaDatoteke2 = putanje.get(odabir - 1).toString();
        System.out.println(putanjaDatoteke2);

        String procitanoImeJela = "";
        BigDecimal procitanaCijenaJela = BigDecimal.ZERO;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(putanjaDatoteke2))) {

            String readDatum = ois.readUTF();
            String readIme = ois.readUTF(); // Čita ime iz datoteke
            String readPrezime = ois.readUTF();  // Čita prezime iz datoteke
            procitanoImeJela = ois.readUTF();
            procitanaCijenaJela = BigDecimal.valueOf(ois.readDouble());

        } catch (IOException e) {
            System.err.println("Greška prilikom čitanja iz datoteke: " + e.getMessage());
        }




        // Usporedba cijena i zapisivanje manjih cijena u "meals2.txt"
        try (PrintWriter writer = new PrintWriter("dat/meals2.txt")) {
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (Meal meal : meals) {
                if (meal.getPrice().compareTo(procitanaCijenaJela) < 0) {
                    totalPrice = totalPrice.add(meal.getPrice());
                    writer.println(meal.getName());
                }

                System.out.println("Jelo: " + meal.getName() +
                        " Cijena: " + meal.getPrice() +
                        " -> " + (meal.getPrice().compareTo(procitanaCijenaJela) < 0 ? "Manja" : "Veća"));
            }

            writer.println("Ukupna cijena: " + totalPrice);
        } catch (IOException e) {
            System.out.println("Greška prilikom pisanja u 'meals2.txt': " + e.getMessage());
        }

        System.out.println("Podaci uspješno zapisani u 'meals2.txt'.");
  }
}
