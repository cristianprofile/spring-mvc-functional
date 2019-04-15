package com.cromero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    RouterFunction<ServerResponse> routes(TransactionHandler transactionHandler) {
        var root = "";
        return route()
                .GET(root + "/transaction", transactionHandler::handleGetAllTransaction)
                .GET(root + "/transaction/{id}", transactionHandler::handleGetTransactionById).
                        POST(root + "/transaction", transactionHandler::handlePostTransaction).

                        onError(IllegalArgumentException.class, this::catchNullPointerException).
                        onError(NullPointerException.class, this::catchIllegalArgumentException).

                        filter(this::filterHandler)
                .build();
    }

    private ServerResponse filterHandler(ServerRequest serverRequest, HandlerFunction<ServerResponse> handlerFunction) throws Exception {
        try {
            logger.info("Init HandlerFilterFunction");
            return handlerFunction.handle(serverRequest);
        } finally {
            logger.info("End HandlerFilterFunction");
        }
    }

    private ServerResponse catchIllegalArgumentException(Throwable error, ServerRequest request) {
        logger.error("Error message NullPointer Exception: {}", error.getMessage());
        return ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    private ServerResponse catchNullPointerException(Throwable error, ServerRequest request) {
        logger.error("Error message IllegalArgumentException: {}", error.getMessage(), error);
        return ServerResponse.status(HttpStatus.NO_CONTENT).build();
    }
}
