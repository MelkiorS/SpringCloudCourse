package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProcessingRest {
    ProcessingRepository processingRepository;
    AccountServiceClient accountServiceClient;
    CardServiceClient cardServiceClient;

    @Autowired
    public ProcessingRest(ProcessingRepository processingRepository, AccountServiceClient accountServiceClient, CardServiceClient cardServiceClient) {
        this.processingRepository = processingRepository;
        this.accountServiceClient = accountServiceClient;
        this.cardServiceClient = cardServiceClient;
    }

    @RequestMapping("/issue/{accountId}")
    public ProcessingEntity issue(@PathVariable Integer accountId) {
        String card = cardServiceClient.create();
        ProcessingEntity processingEntity = new ProcessingEntity();
        processingEntity.setAccountId(accountId);
        processingEntity.setCard(card);
        return processingRepository.save(processingEntity);
    }

    @RequestMapping("/checkout/{card}")
    public Boolean checkout(@PathVariable String card, @RequestParam BigDecimal sum){
        ProcessingEntity entity = processingRepository.findByCard(card);
        if(null == entity) return false;
         return accountServiceClient.checkout(entity.getId(), sum);
    }

    @RequestMapping("/get")
    public Map<Integer,String> get(@RequestParam List<Integer> accountIdList){
       List<ProcessingEntity> entities = processingRepository.findByAccountIdIn(accountIdList);
        final Map<Integer,String> map = new HashMap<Integer, String>();
        for(ProcessingEntity e: entities){
                map.put(e.getId(),e.getCard());
        }
        return map;
    }
}
