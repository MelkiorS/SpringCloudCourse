package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientRest {
    ClientRepository clientRepository;
    ClientDAO clientDao;

    @Autowired
    public ClientRest(ClientRepository clientRepository, ClientDAO clientDao) {
        this.clientRepository = clientRepository;
        this.clientDao = clientDao;
    }

    @RequestMapping("/create")
    public ClientEntity create(@RequestParam String name) {
        return clientDao.create(name);
    }

    @RequestMapping("/update/{id}")
    @ResponseBody
    public HttpStatus update(@PathVariable Integer id, @RequestParam String name) {
        if (clientDao.update(id, name)) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        clientRepository.delete(id);
    }

    @RequestMapping("/get")
    public List<ClientEntity> get(){
        return clientRepository.findAll();
    }

    @RequestMapping("/get/{id}")
    public ClientEntity get(@PathVariable Integer id){
        return clientRepository.findOne(id);
    }
}
