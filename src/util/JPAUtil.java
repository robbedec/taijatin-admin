package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProvider;

public class JPAUtil {

    private  final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javaG10");

    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    private JPAUtil(){

    }
}
