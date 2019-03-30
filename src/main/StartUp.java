package main;

import domain.Address;
import util.JPAUtil;

import javax.persistence.EntityManager;

public class StartUp {
    public static void main(String[] args) {
        System.out.println("robbe");

        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Address a = new Address();
        a.setCity("brugge");
       // a.setAddressId(10);
        a.setCountry("belige");
        a.setNumber(5);
        a.setStreet("kkkk");
        a.setZipCode(8888);
       //a.setAddressId(100);
        entityManager.persist(a);
        entityManager.getTransaction().commit();
        entityManager.close();
        JPAUtil.getEntityManagerFactory().close();
        //System.out.println(us.getUserName());
    }
}
