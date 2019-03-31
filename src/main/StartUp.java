package main;

import domain.Address;
import domain.Attendance;
import domain.Comment;
import domain.User;
import repository.AddressDao;
import repository.GenericDao;
import repository.GenericDaoJpa;
import util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class StartUp {
    public static void main(String[] args) {
/*
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Address a = new Address();
        a.setCity("brugge");
        a.setCountry("belige");
        a.setNumber(5);
        a.setStreet("kkkk");
        a.setZipCode(8888);

        entityManager.persist(a);
        entityManager.getTransaction().commit();
        entityManager.close();
        JPAUtil.getEntityManagerFactory().close();*/

        GenericDao<Address> addressRepo = new GenericDaoJpa<>(Address.class);
        List<Address> lijst = addressRepo.getAll();
        for(Address a : lijst){
            System.out.printf("ID: %s, GEMEENTE: %s%n", a.getAddressId(), a.getCity());
        }
        System.out.println(lijst.size());

        GenericDao<User> userRepo = new GenericDaoJpa<>(User.class);
        List<User> userList = userRepo.getAll();
        System.out.println("Users:");
        for(User u : userList){
            System.out.printf("ID: %s, USERNAME: %s%n", u.getId(), u.getUserName());
            System.out.println(u.getAddressByAddressId().getStreet());
            System.out.println();
            for (Comment com : u.getCommentsById()){
                System.out.println(com.getCommentText());
            }
        }
    }
}
