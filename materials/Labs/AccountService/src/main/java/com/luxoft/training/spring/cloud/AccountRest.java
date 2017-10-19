package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountRest {
    AccountDAO accountDAO;
    AccountRepository accountRepository;

    @Autowired
    public AccountRest(AccountDAO accountDAO, AccountRepository accountRepository) {
        this.accountDAO = accountDAO;
        this.accountRepository = accountRepository;
    }

    @RequestMapping("/create")
    public void create(@RequestParam Integer id){
        accountDAO.create(id);
    }

    @RequestMapping("/fund/{id}")
    public boolean fund(@PathVariable Integer id, @RequestParam BigDecimal sum){
        return accountDAO.addBalance(id, sum.abs());
    }

//     checkout
}
