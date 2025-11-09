package com.myco.users.services;

import com.myco.users.entities.Contact;
import com.myco.users.exceptions.ApplicationException;

import java.util.List;

public interface ContactService {

    public Contact save(Contact contact) throws ApplicationException;
    public Contact remove(long id) throws ApplicationException;

    public Contact find(Long id);
    public List<Contact> findAll();

    public List<Contact> findAllByAppUser(String mobileNumber);
    public List<Contact> findAllByUserId(String uuid);
}
