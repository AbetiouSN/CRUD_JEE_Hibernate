package com.abetiou.tp_departement.DAO;


import com.abetiou.tp_departement.Entity.User;
import com.abetiou.tp_departement.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class DaoUser implements CRUD<User,Integer>{
    static Session session;
    static Transaction transaction;
    SessionFactory sessionFactory ;

    public  DaoUser(){
        session = HibernateUtil.getSessionFactory().openSession();
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void save(User entity) {
        try{
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        try{
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {

        try {
            User user = findById(id);
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User findById(Integer id) {
        try{
            User user = (User) session.get(User.class,id);
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try{
            users = session.createQuery("From User").list();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return users;
    }


    public void createAdmin() {
        session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            User admin = new User();
            admin.setNom("SOFIANE ABETIOU");
            admin.setPrenom("admin");
            admin.setEmail("admin");
            String hashedPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
            admin.setPassword(hashedPassword);

            System.out.println("Admin Details: " + admin);

            session.save(admin);
            transaction.commit();
            System.out.println("Administrateur inséré avec succès.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback en cas d'erreur
            }
            System.out.println("Erreur lors de l'insertion de l'administrateur: " + e.getMessage());
            e.printStackTrace();  // Plus d'informations sur l'erreur
        } finally {
            session.close();
        }
    }


    public User login(String email, String password) {
        User user = null;
        session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            user = (User) session.createQuery("FROM User WHERE email = :email")
                    .setParameter("email", email)
                    .uniqueResult();
            transaction.commit();
            // Vérification du mot de passe
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                return user; // Mot de passe correct
            } else {
                return null; // Nom d'utilisateur ou mot de passe incorrect
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion: " + e.getMessage());
        } finally {
            session.close();
        }
        return null;
    }

}
