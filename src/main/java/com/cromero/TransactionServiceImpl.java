package com.cromero;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AtomicLong counter = new AtomicLong();


    private final List<Transaction> transactions = new ArrayList<>(Set.of(
            new Transaction(counter.incrementAndGet(), 200, LocalDateTime.of(LocalDate.now(), LocalTime.now())  ,"rent house"),
            new Transaction(counter.incrementAndGet(), 500, LocalDateTime.of(LocalDate.now(), LocalTime.now())  ,"buy car")));

    @Override
    public Transaction save(Transaction transaction) {
       var transactionToAdd = new Transaction();
        BeanUtils.copyProperties(transaction,transactionToAdd);
        transactionToAdd.setId(counter.incrementAndGet());
        transactions.add(transactionToAdd);
        return transactionToAdd;
    }

    @Override
    public List<Transaction> getAll() {
        return this.transactions;
    }


    @Override
    public Transaction findById(Long id) {

        var transactionFound =
                this.transactions.stream().filter(transaction -> transaction.getId().equals(id)).findFirst().
                orElseThrow((() -> new IllegalArgumentException("Transaction not found!!Id:".concat(id.toString()))));

        return transactionFound;
    }


}
