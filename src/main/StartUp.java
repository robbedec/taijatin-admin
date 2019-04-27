package main;

import domain.User;
import repository.GenericDao;
import repository.GenericDaoJpa;

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

        GenericDao<User> userRepo = new GenericDaoJpa<>(User.class);
        List<User> userList = userRepo.getAll();

        User u = userList.get(0);

        /*
        System.out.println("Users:");
        for(User u : userList){
            System.out.printf("ID: %s, USERNAME: %s%n%s%n", u.getId(), u.getUserName(), u.getType());
            System.out.println(u.getAddressByAddressId().getStreet());
            System.out.println();
            for (Comment com : u.getCommentsById()){
                System.out.println(com.getCommentText());
            }
        }*/
    }
}
