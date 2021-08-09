package com.revature.repositories;

public interface CrudRepository<T> {

    T findById(int id);
    T save(T newResource);
    T update(T updateResource);
    boolean delete(String deleteResource);

}
