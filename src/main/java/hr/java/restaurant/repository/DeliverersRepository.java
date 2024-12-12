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

public class DeliverersRepository extends AbstractRepository<Deliverer> {

    @Override
    public Deliverer findById(long id) {
        List<Deliverer> deliverers = new ArrayList<>();
        DeliverersRepository deliverersRepository = new DeliverersRepository();
        deliverers = deliverersRepository.readFromFile();
        Deliverer deliverer;
        deliverer = deliverers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        return deliverer;
    }

    @Override
    public List<Deliverer> findAll() {
        return List.of();
    }

    @Override
    public List<Deliverer> readFromFile() {
        List<Deliverer> deliverers  = new ArrayList<>();
        File file = new File(FileNames.DeliverersFileName);
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
                deliverers.add(new Deliverer(id, name, lastName, new Contract(salary, startDate, endDate, ContractType.valueOf(ContractTypeString.toUpperCase())), new Bonus(bonus)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deliverers;
    }

    @Override
    public void save(List<Deliverer> deliverers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileNames.DeliverersFileName)))) {
            for (Deliverer deliverer : deliverers) {
                bw.write(deliverer.getId() + "\n");
                bw.write(deliverer.getFirstName() + "\n");
                bw.write(deliverer.getLastName() + "\n");
                bw.write(deliverer.getSalaryAndBonusZaposlenika() + "\n");
                bw.write(deliverer.getContract().getStartDate() + "\n");
                bw.write(deliverer.getContract().getEndDate() + "\n");
                bw.write(deliverer.getContract().getContractType() + "\n");
                bw.write(deliverer.getBonus() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeListToBinaryFile(List<Deliverer> deliverers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileNames.DeliverersBinaryFileName))){
            oos.writeObject(deliverers);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Deliverer> readListFromBinaryFile() {
        List<Deliverer> deliverers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileNames.DeliverersBinaryFileName))) {
            deliverers = (List<Deliverer>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return deliverers;
    }
}
