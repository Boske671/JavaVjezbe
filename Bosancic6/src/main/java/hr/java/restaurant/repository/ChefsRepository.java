package hr.java.restaurant.repository;

import hr.java.restaurant.enumeration.ContractType;
import hr.java.restaurant.model.*;
import hr.java.utils.FileNames;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChefsRepository extends AbstractRepository<Chef> {

    @Override
    public Chef findById(long id) {
        List<Chef> chefs = new ArrayList<>();
        ChefsRepository chefsRepository = new ChefsRepository();
        chefs = chefsRepository.readFromFile();
        Chef chef;
        chef = chefs.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        return chef;
    }

    @Override
    public List<Chef> findAll() {
        return List.of();
    }

    @Override
    public List<Chef> readFromFile() {
        List<Chef> chefs = new ArrayList<>();
        File file = new File(FileNames.ChefsFileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Long id = Long.parseLong(line);
                String name = br.readLine();
                String lastName = br.readLine();
                BigDecimal salary = BigDecimal.valueOf(Double.parseDouble(br.readLine()));
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                LocalDate startDate = LocalDate.parse(br.readLine(), dateFormat);
                LocalDate endDate = LocalDate.parse(br.readLine(), dateFormat);
                String ContractTypeString = br.readLine();
                BigDecimal bonus = BigDecimal.valueOf(Double.parseDouble(br.readLine()));
                chefs.add(new Chef(id, name, lastName, new Contract(salary, startDate, endDate, ContractType.valueOf(ContractTypeString.toUpperCase())), new Bonus(bonus)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chefs;
    }

    @Override
    public void save(List<Chef> chefs) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileNames.ChefsFileName)))){
            for (Chef chef : chefs) {
                bw.write(chef.getId() + "\n");
                bw.write(chef.getFirstName() + "\n");
                bw.write(chef.getLastName() + "\n");
                bw.write(chef.getSalaryAndBonusZaposlenika() + "\n");
                bw.write(chef.getContract().getStartDate() + "\n");
                bw.write(chef.getContract().getEndDate() + "\n");
                bw.write(chef.getContract().getContractType() + "\n");
                bw.write(chef.getBonus() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeListToBinaryFile(List<Chef> chefs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileNames.ChefsBinaryFileName))){
            oos.writeObject(chefs);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Chef> readListFromBinaryFile() {
        List<Chef> chefs = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileNames.ChefsBinaryFileName))) {
            chefs = (List<Chef>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return chefs;
    }
}
