package com.example.stock.service;

import com.example.stock.aop.LettuceLock;
import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LettuceLockStockService {

    private final StockRepository stockRepository;

    public LettuceLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    @Transactional
    @LettuceLock
    public void decrease(Long id, Long quantity){

        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
    }
}
