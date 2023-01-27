package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.services.RestClientService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Makhsut Islamov on 30.03.2017.
 */
@Service
public class RestClientServiceImpl extends BaseService implements RestClientService {
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public RestClientServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    @Override
    public <T, R> ResponseEntity<R> post(String url, T data, Class<R> responseType) {
        HttpEntity<T> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<R> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.OK,
                String.format("Failed to send data (url: %s, httpStatus: %s)", url, responseEntity.getStatusCode().toString())
        );

        return responseEntity;
    }
}
