package com.abetiou.tp_departement.DAO;


import com.abetiou.tp_departement.Entity.Departement;
import com.abetiou.tp_departement.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DaoDepartement implements CRUD<Departement,Integer>{

    static Session session;
    static Transaction transaction;

    public DaoDepartement(){
        session = HibernateUtil.getSessionFactory().openSession();
    }


    @Override
    public void save(Departement entity) {
         try{
             transaction = session.beginTransaction();
             session.save(entity);
             transaction.commit();
         }catch (Exception e){
             if (transaction != null) transaction.rollback();
             System.out.println(e.getMessage());
         }
    }

    @Override
    public void update(Departement entity) {
        try{
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Departement departement = findById(id);
            if (departement != null) {
                transaction = session.beginTransaction();
                session.remove(departement);
                transaction.commit();
            } else {
                System.out.println("No department found with ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Departement findById(Integer id) {
        try{
            Departement departement = (Departement) session.get(Departement.class,id);
            return departement;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Departement> findAll() {
        List<Departement> departements = new ArrayList<>();
        try{
            departements = session.createQuery("from Departement ").list();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return departements;
    }

}

