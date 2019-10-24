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

import Model.Hibernate.Client;
import Model.Hibernate.HibernateUtil;

public class ClientRepository {
    public static void postClient(Client c) {
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
    public static Client getClient(int id) {
        Transaction transaction = null;
        Client res = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = (Client) session.get(Client.class, id);
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
    public static List<Client> getClient() {
        Transaction transaction = null;
        List<Client> res = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = session.createQuery("from Client").list();
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
     public static Client putClient(Client c) {
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
            if (session.isOpen()){
                session.close();
            }
            return c;
        }
    }
         public static Client deleteClient(Client c) {
             Session session = null;
        Transaction transaction = null;
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
            if (session.isOpen()){
                session.close();
            }
            return c;
        }
    }
}