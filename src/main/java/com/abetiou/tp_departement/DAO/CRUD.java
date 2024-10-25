package com.abetiou.tp_departement.DAO;

import java.util.List;

public interface CRUD <T,PK>{
    public void save(T entity);
    public void update(T entity);
    public void delete(PK id);
    public T findById(PK id);
    public List<T> findAll();
}
