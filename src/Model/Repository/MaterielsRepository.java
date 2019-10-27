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
import Model.Hibernate.HibernateUtil;
import org.hibernate.Query;

public class MaterielsRepository {

    public static void postMateriels(Materiels c) {
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
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public static Materiels getMateriels(int id) {
        Transaction transaction = null;
        Materiels res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = (Materiels) session.get(Materiels.class, id);
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

    public static List<Materiels> getMateriels() {
        Transaction transaction = null;
        List<Materiels> res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = session.createQuery("from Materiels").list();
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
    public static List<Materiels> getMaterielsDispo() {
        Transaction transaction = null;
        List<Materiels> res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = session.createQuery("from Materiels where quantiteStock > 0").list();
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

    public static Materiels putMateriels(Materiels oldValue, Materiels newValue) {

        Transaction transaction = null;
        Session session = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query q = session.createSQLQuery("UPDATE `materiels` SET `num_serie` = ?, `nom_materiels` = ?, `prix_materiels` = ?, `quantite_stock` = ?, `categorie_categorie` = ? WHERE `materiels`.`num_serie` = ?");
            q.setInteger(0, newValue.getNumSerie());
            q.setString(1, newValue.getNomMateriels());
            q.setFloat(2, newValue.getPrixMateriels());
            q.setInteger(3, newValue.getQuantiteStock());
            q.setString(4, newValue.getCategorie().getCategorie());
            q.setInteger(5, oldValue.getNumSerie());
            q.executeUpdate();

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
            return newValue;
        }
    }

    public static Materiels putMateriels(Materiels oldValue) {

        Transaction transaction = null;
        Session session = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(oldValue);
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
            return oldValue;
        }
    }

    public static Materiels deleteMateriels(Materiels c) {
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
