package com.opencbs.genesis.services;

import org.springframework.http.ResponseEntity;

/**
 * Created by Makhsut Islamov on 30.03.2017.
 */
public interface RestClientService {
    <T, R> ResponseEntity<R> post(String url, T data, Class<R> responseType);
}
