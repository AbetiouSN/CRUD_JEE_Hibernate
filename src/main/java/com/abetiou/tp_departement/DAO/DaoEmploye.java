package com.abetiou.tp_departement.DAO;


import com.abetiou.tp_departement.Entity.Departement;
import com.abetiou.tp_departement.Entity.Employe;
import com.abetiou.tp_departement.Entity.User;
import com.abetiou.tp_departement.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DaoEmploye implements CRUD<Employe,Integer> {

    private final DaoUser daoUser = new DaoUser();
    private  final  DaoDepartement daoDepartement = new DaoDepartement();

    static Session session;
    static Transaction transaction;

    public DaoEmploye(){
        session = HibernateUtil.getSessionFactory().openSession();
    }



public void Add(Employe entity, Integer id) {
    Transaction transaction = null;
    try {
        transaction = session.beginTransaction();

        // Creer un nouvel user et le stocker
        User user = new User();
        user.setNom(entity.getIdUser().getNom());
        user.setPrenom(entity.getIdUser().getPrenom());
        user.setEmail(entity.getIdUser().getEmail());
        session.save(user);

        // Recuperer le departement existant avec l'ID fourni
        Departement departement = session.get(Departement.class, id);
        if (departement == null) {
            throw new Exception("Departement n'exist pas : " + id);
        }

        // Associer l'user et le departement a l'employe
        entity.setIdUser(user);
        entity.setIdDep(departement);

        // Sauvegarder l'employe
        session.save(entity);

        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) transaction.rollback();
        System.out.println("Error saving Employe: " + e.getMessage());
    }
}


    @Override
    public void save(Employe entity) {

    }

    @Override
    public void update(Employe entity) {
        try {
//            // Recupere User existant  and Departement
//            User user = entity.getIdUser();
//            Departement departement = entity.getIdDep();
//
//            entity.setIdUser(user);
//            entity.setIdDep(departement);

            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();

            System.out.println("Employe, User, and Departement updated successfully.");
        } catch (Exception e) {
            System.out.println("Error saving Employe: " + e.getMessage());
        }

    }

//    @Override
//    public void delete(Integer id) {
//        try{
//            Employe employe = findById(id);
//            daoUser.delete(employe.getIdUser().getId());
//            daoDepartement.delete(employe.getIdDep().getId());
//            transaction = session.beginTransaction();
//            session.remove(employe);
//            transaction.commit();
//
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

    @Override
    public void delete(Integer id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // trouve l'employe
            Employe employe = session.get(Employe.class, id);
            if (employe != null) {
                session.remove(employe);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error deleting Employe: " + e.getMessage());
        }
    }

    @Override
    public Employe findById(Integer id) {
        try{
            Employe employe = (Employe) session.get(Employe.class,id);
            return employe;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Employe> FindEmploysByIdDep(Integer id){
        List<Employe> employes = new ArrayList<>();
        try{
            transaction = session.beginTransaction();;
            employes = session.createQuery("from Employe e where e.idDep.id = :idDep", Employe.class)
                    .setParameter("idDep", id)
                    .list();
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return employes;
    }

    public int nbrEmploye(Departement departement){
        List<Employe> employes = new ArrayList<>();
        employes = FindEmploysByIdDep(departement.getId());
        return employes.size();
    }

    @Override
    public List<Employe> findAll() {
            List<Employe> employes = new ArrayList<>();
            try {
                transaction = session.beginTransaction();
                employes = session.createQuery("from Employe", Employe.class).list();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
            return employes;

    }
}
