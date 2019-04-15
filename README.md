# Testing new WebMvc.fn model in Spring mvc #

Testing new WebMvc.fn programming model in Spring MVC. Better approach to get more functional definition
of controllers using route. (similar to webflux module)


    GET localhost:8080/transaction   ---> get all transactions

    GET localhost:8080/transaction/1 ----> get transaction with id 1

    GET localhost:8080/transaction/2222 ----> throw exception http response 204 (No content)

    GET localhost:8080/transaction   ---> get all transactions

    POST localhost:8080/transaction   ---> create a transactions


Router configuration:

     @Bean
     RouterFunction<ServerResponse> routes(TransactionHandler transactionHandler) {
        var root = "";
        return route()
                .GET(root + "/transaction", transactionHandler::handleGetAllTransaction)
                .GET(root + "/transaction/{id}", transactionHandler::handleGetTransactionById).
                        POST(root + "/transaction", transactionHandler::handlePostTransaction).
     //Defined Exception handler behaviour code:

                        onError(IllegalArgumentException.class, this::catchNullPointerException).
                        onError(NullPointerException.class, this::catchIllegalArgumentException).

                        filter(this::filterHandler)
                .build();
    }

Code catching exceptions:

    private ServerResponse filterHandler(ServerRequest serverRequest, HandlerFunction<ServerResponse> handlerFunction) throws Exception {
        try {
            logger.info("Init HandlerFilterFunction");
            return handlerFunction.handle(serverRequest);
        } finally {
            logger.info("End HandlerFilterFunction");
        }
    }

Defined Exception handler behaviour code:

    private ServerResponse catchIllegalArgumentException(Throwable error, ServerRequest request) {
        logger.error("Error message NullPointer Exception: {}", error.getMessage());
        return ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    private ServerResponse catchNullPointerException(Throwable error, ServerRequest request) {
        logger.error("Error message IllegalArgumentException: {}", error.getMessage(), error);
        return ServerResponse.status(HttpStatus.NO_CONTENT).build();
    }
    
 
Transaction handler configuration (controller code using ServerResponse builder):

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

