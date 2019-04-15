package com.cromero;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringMvcFunctionalApplicationTests {

    @Autowired TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    @Test
    public void getTransactionOk() {

        Transaction transaction = restTemplate.getForObject("http://localhost:" + this.port + "/transaction/1",
                Transaction.class);
        assertThat(transaction).isNotNull();
        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getId()).isEqualTo(1);
    }


    @Test
    public void getTransactionNoContent() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + this.port +
                        "/transaction/1232323", HttpMethod.GET, requestEntity, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);;
    }


    @Test
    public void postTransactionOk() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(233);
        transaction1.setConcept("concept");
        LocalDateTime localDateToday =  LocalDateTime.now();;
        transaction1.setCreatedAt(localDateToday);
        ResponseEntity<Transaction> transactionResponseEntity =
                restTemplate.postForEntity("http://localhost:" + this.port + "/transaction", transaction1,
                        Transaction.class);
        assertThat(transactionResponseEntity).isNotNull();
        assertThat(transactionResponseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
        assertThat(transactionResponseEntity.getBody().getId()).isEqualByComparingTo(3L);

    }



}
