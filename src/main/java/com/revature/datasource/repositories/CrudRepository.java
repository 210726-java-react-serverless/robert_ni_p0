package com.revature.datasource.repositories;

public interface CrudRepository<T> {

    T findById(String id);
    T save(T newResource);
    T update(T updateResource);
    boolean delete(String deleteResource);

}
