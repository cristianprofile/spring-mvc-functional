package com.cromero;

import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);

    List<Transaction> getAll();

    Transaction findById(Long id);
}
