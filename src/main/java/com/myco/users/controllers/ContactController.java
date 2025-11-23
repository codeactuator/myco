package com.myco.users.controllers;

import com.myco.users.entities.Contact;
import com.myco.users.services.AppUserService;
import com.myco.users.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("v1/contacts")
public class ContactController {

    private final ContactService contactService;
    private final AppUserService appUserService;

    public ContactController(ContactService contactService, AppUserService appUserService) {
        this.contactService = contactService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public String ping(){
        return HttpStatus.OK.name();
    }

    @GetMapping("/{userId}")
    public List<Contact> getContacts(@PathVariable String userId){
        return contactService.findAllByUserId(userId);
    }

    @GetMapping("/contactId/{contactId}")
    public Contact getContact(@PathVariable Long contactId){
        return contactService.find(contactId);
    }

    @PostMapping
    public Contact create(@RequestBody Contact contact){
        return contactService.save(contact);
    }

    @DeleteMapping("/{contactId}")
    public Contact delete(@PathVariable Long contactId){
        return contactService.remove(contactId);
    }
}
