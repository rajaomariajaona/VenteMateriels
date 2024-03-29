/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository;

/**
 *
 * @author snowden
 */
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import Model.Hibernate.Materiels;
import Model.Hibernate.Contient;
import Model.Hibernate.Commande;
import Model.Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;

public class ContientRepository {

    public static void postContient(Contient c) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(c);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (e instanceof ConstraintViolationException) {
                throw e;
            } else {
                e.printStackTrace();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public static Contient getContient(int id) {
        Transaction transaction = null;
        Contient res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = (Contient) session.get(Contient.class, id);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
            return res;
        }
    }

    public static List<Contient> getContient() {
        Transaction transaction = null;
        List<Contient> res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = session.createQuery("from Contient").list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
            return res;
        }
    }

    public static List<Contient> getContient(Commande cmd) {
        Transaction transaction = null;
        List<Contient> res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query q = session.createQuery("from Contient where commande.numCommande = ?");
            q.setInteger(0, cmd.getNumCommande());
            res = q.list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
            return res;
        }
    }
    public static Contient getContient(Commande cmd, Materiels mat) {
        Transaction transaction = null;
        Contient res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query q = session.createQuery("from Contient where commande.numCommande = ? and materiels.numSerie = ?");
            q.setInteger(0, cmd.getNumCommande());
            q.setInteger(1, mat.getNumSerie());
            res = (Contient) q.uniqueResult();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
            return res;
        }
    }

    public static Contient putContient(Contient c) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(c);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
            return c;
        }
    }

    public static Contient deleteContient(Contient c) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(c);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
            return c;
        }
    }
}
