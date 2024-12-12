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

public class WaitersRepository extends AbstractRepository<Waiter> {

    @Override
    public Waiter findById(long id) {
        List<Waiter> waiters = new ArrayList<>();
        WaitersRepository waitersRepository = new WaitersRepository();
        waiters = waitersRepository.readFromFile();
        Waiter waiter;
        waiter = waiters.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        return waiter;
    }

    @Override
    public List<Waiter> findAll() {
        return List.of();
    }

    @Override
    public List<Waiter> readFromFile() {
        List<Waiter> waiters = new ArrayList<>();
        File file = new File(FileNames.WaitersFileName);
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
                waiters.add(new Waiter(id, name, lastName, new Contract(salary, startDate, endDate, ContractType.valueOf(ContractTypeString.toUpperCase())), new Bonus(bonus)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return waiters;
    }

    @Override
    public void save(List<Waiter> waiters) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileNames.WaitersFileName)))) {
            for (Waiter waiter : waiters) {
                bw.write(waiter.getId() + "\n");
                bw.write(waiter.getFirstName() + "\n");
                bw.write(waiter.getLastName() + "\n");
                bw.write(waiter.getSalaryAndBonusZaposlenika() + "\n");
                bw.write(waiter.getContract().getStartDate() + "\n");
                bw.write(waiter.getContract().getEndDate() + "\n");
                bw.write(waiter.getContract().getContractType() + "\n");
                bw.write(waiter.getBonus() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void writeListToBinaryFile(List<Waiter> waiters) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileNames.WaitersBinaryFileName))){
            oos.writeObject(waiters);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Waiter> readListFromBinaryFile() {
        List<Waiter> waiters = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileNames.WaitersBinaryFileName))) {
            waiters = (List<Waiter>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return waiters;
    }
}
