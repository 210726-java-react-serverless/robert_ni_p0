package com.revature.repositories;

public interface CrudRepository<T> {
    T findUser(String username, String password);
    T save(T newResource);
}
