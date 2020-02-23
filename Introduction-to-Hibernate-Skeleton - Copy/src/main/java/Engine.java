import com.sun.xml.bind.v2.TODO;
import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(
                new InputStreamReader(
                        System.in));
    }

    @Override
    public void run() {
        //Ex 2
        // this.removeObjects();

        //Ex 3
//        try {
//            this.containsEmployee();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex 4
        //this.employeesWithSalaryOver50000();

        //Ex 5
        //this.employeesFromDepartment();

        //Ex 6
//        try {
//            addNewAddressAndUpdateEmployee();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex 7
        findAddressesWithThereEmployeeCount();
    }

    //Ex 2
    private void removeObjects() {
        this.entityManager.getTransaction().begin();

        this.entityManager
                .createQuery("UPDATE Town t " +
                        "set t.name = LOWER(t.name) " +
                        "where length(t.name) < 6")
                .executeUpdate();

        this.entityManager.getTransaction().commit();

        // По описателен начин за решаване ->>>
       /* List<Town> towns = this.entityManager
                .createQuery("SELECT t FROM Town t " +
                        "WHERE LENGTH(t.name) > 5 ", Town.class)
                .getResultList();

        this.entityManager.getTransaction().begin();

        towns.forEach(this.entityManager::detach);

        List<Town> remainingTowns = this.entityManager
                .createQuery("select t from Town t " +
                        "where length(t.name) < 6 ", Town.class)
                .getResultList();

        remainingTowns.forEach(this.entityManager::detach);
        remainingTowns.forEach(t -> t.setName(t.getName().toLowerCase()));
        remainingTowns.forEach(this.entityManager::merge);
        this.entityManager.flush();

        this.entityManager.getTransaction().commit();
        */
    }

    //Ex 3
    private void containsEmployee() throws IOException {
        System.out.println("Please, enter employee full name: ");

        String fullName = this.reader.readLine();

        try {
            Employee employee = this.entityManager
                    .createQuery("select e from Employee e " +
                            "where CONCAT(e.firstName, ' ', e.lastName) = :name", Employee.class)
                    .setParameter("name", fullName)
                    .getSingleResult();

            System.out.println("Yes");
        } catch (NoResultException nre) {
            System.out.println("No");
        }
    }

    //Ex 4
    private void employeesWithSalaryOver50000() {
        this.entityManager
                .createQuery("select e from Employee e " +
                        "where e.salary > 50000", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.println(e.getFirstName()));

    }

    //Ex 5
    private void employeesFromDepartment() {
        List<Employee> employees = this.entityManager
                .createQuery("select e from Employee e " +
                        "where e.department.id = 6 " +
                        "order by e.salary," +
                        "e.id", Employee.class)
                .getResultList();

        employees.forEach(e -> System.out.println(String.format(
                "%s %s from %s - $%.2f",
                e.getFirstName(),
                e.getLastName(),
                e.getDepartment().getName(),
                e.getSalary())));
    }

    //Ex 6
    private void addNewAddressAndUpdateEmployee() throws IOException {
        System.out.println("Enter employee last name: ");
        String lastName = this.reader.readLine();

        try {
            Employee employee = this.entityManager
                    .createQuery("SELECT DISTINCT e FROM Employee AS e " +
                            "WHERE e.lastName = :name", Employee.class)
                    .setParameter("name", lastName)
                    .getSingleResult();

            Address address = this.createNewAddress("Vitoshka 15");

            this.entityManager.getTransaction().begin();

            this.entityManager.detach(employee);
            employee.setAddress(address);
            this.entityManager.merge(employee);
            this.entityManager.flush();

            this.entityManager.getTransaction().commit();
        } catch (NoResultException ex) {
            System.out.println("Employee with this last name doesn't exist!");
        } catch (NonUniqueResultException nonUnique) {
            System.out.println("This last name isn't unique! The employee address can't be modified!");
        }
    }

    //Create new address to ex 6
    private Address createNewAddress(String textContent) {
        //Create new address
        Address address = new Address();
        address.setText(textContent);

        this.entityManager.getTransaction().begin();
        //set address to the database
        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();

        return address;
    }

    //Ex 7
    private void findAddressesWithThereEmployeeCount() {
        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a " +
                "order by a.employees.size desc, a.town.id asc", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(a -> System.out.println(
                String.format(
                "%s, %s - %d employees",
                        a.getText(),
                        a.getTown(),
                        a.getEmployees().size())
        ));
    }
}
