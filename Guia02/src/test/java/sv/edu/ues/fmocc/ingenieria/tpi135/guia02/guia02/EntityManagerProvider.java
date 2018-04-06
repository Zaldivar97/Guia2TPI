/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.ues.fmocc.ingenieria.tpi135.guia02.guia02;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 * @author zaldivar
 */
public class EntityManagerProvider implements TestRule {

    private static EntityManagerProvider object;
    private final EntityManager em;
    private final EntityTransaction tx;

    private EntityManagerProvider(String unitName) {
        this.em = Persistence.createEntityManagerFactory(unitName).createEntityManager();
        this.tx = this.em.getTransaction();
    }

    public static EntityManagerProvider getInstance(String unitName) {
        if (object == null) {
            object = new EntityManagerProvider(unitName);
        }
        return object;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
                getEm().clear();
                getEm().close();
            }
        };
    }

    public EntityManager getEm() {
        return em;
    }

    public EntityTransaction getTx() {
        return tx;
    }
    public void killInstance(){
        object=null;
    }

}
