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

import Model.Hibernate.Commande;
import Model.Hibernate.HibernateUtil;

public class CommandeRepository {
    public static void postCommande(Commande c) {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(c);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally{
            if(session.isOpen()){
                session.close();
            }
        }
    }
    public static Commande getCommande(int id) {
        Transaction transaction = null;
        Commande res = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = (Commande) session.get(Commande.class, id);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally{
            if(session.isOpen()){
                session.close();
            }
            return res;
        }
    }
    public static List<Commande> getCommande() {
        Transaction transaction = null;
        List<Commande> res = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = session.createQuery("from Commande").list();
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally{
            if(session.isOpen()){
                session.close();
            }
            return res;
        }
    }
     public static Commande putCommande(Commande c) {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(c);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally{
            if(session.isOpen()){
                session.close();
            }
            return c;
        }
    }
         public static Commande deleteCommande(Commande c) {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(c);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally{
            if(session.isOpen()){
                session.close();
            }
            return c;
        }
    }
}