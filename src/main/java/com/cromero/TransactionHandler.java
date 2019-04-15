package com.cromero;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URI;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@Component
public class TransactionHandler {

    private TransactionService transactionService;

    public TransactionHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    ServerResponse handleGetAllTransaction(ServerRequest serverRequest) {
        return ok().body(transactionService.getAll());
    }

    ServerResponse handlePostTransaction(ServerRequest request) throws ServletException, IOException {
        Transaction transaction = request.body(Transaction.class);
        var result = transactionService.save(transaction);
        var uri = URI.create("/transaction/" + result.getId());
        return ServerResponse.created(uri).body(result);
    }

    ServerResponse handleGetTransactionById(ServerRequest r) {
        return ok().body(transactionService.findById(Long.parseLong(r.pathVariable("id"))));
    }
}
