package com.opencbs.integration.service.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientComponent {
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    @Value("${genesis.integration.user.token}")
    private String userToken;

    public RestClientComponent() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public <T, R> ResponseEntity<R> post(String url, T data, Class<R> responseType) {
        headers.add("Authorization", String.format("Bearer %s", this.userToken));
        HttpEntity<T> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<R> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.OK,
                String.format("Failed to send data (url: %s, httpStatus: %s)", url, responseEntity.getStatusCode().toString())
        );

        return responseEntity;
    }
}
