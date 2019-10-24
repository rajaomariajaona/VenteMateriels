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

import Model.Hibernate.Province;
import Model.Hibernate.HibernateUtil;

public class ProvinceRepository {

    public static List<Province> getProvince() {
        Transaction transaction = null;
        List<Province> res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = session.createQuery("from Province").list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if(session.isOpen()){
                session.close();
            }
            return res;
        }
    }

    public static Province getProvince(String id) {
        Transaction transaction = null;
        Province res = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            res = (Province) session.get(Province.class, id);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if(session.isOpen()){
                session.close();
            }
            return res;
        }
    }
}
