package com.myco.users.services;

import com.myco.users.entities.AppUser;
import com.myco.users.entities.Contact;
import com.myco.users.exceptions.ApplicationException;
import com.myco.users.repositories.AppUserRepository;
import com.myco.users.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService{

    private final ContactRepository contactRepository;
    private final AppUserRepository appUserRepository;

    public ContactServiceImpl(ContactRepository contactRepository, AppUserRepository appUserRepository) {
        this.contactRepository = contactRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Contact save(Contact contact) throws ApplicationException {
        return contactRepository.save(contact);
    }

    @Override
    public Contact remove(long id) throws ApplicationException {
        Contact contact = contactRepository.findById(id).orElse(new Contact());
        contactRepository.deleteById(id);
        return contact;
    }

    @Override
    public Contact find(Long id) {
        return contactRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public List<Contact> findAllByAppUser(String mobileNumber) {
        AppUser appUser = appUserRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found with mobile number: " + mobileNumber));
        return contactRepository.findByAppUser(appUser);
    }

    @Override
    public List<Contact> findAllByUserId(String uuid) {
        AppUser appuser = appUserRepository
                .findById(UUID.fromString(uuid)).orElseThrow();
        List<Contact> contactList = contactRepository.findByAppUser(appuser);
        return contactList;
    }
}
