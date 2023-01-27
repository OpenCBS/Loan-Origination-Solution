package com.opencbs.genesis.dto.responses;

/**
 * Created by Makhsut Islamov on 24.10.2016.
 */
public class ApiResponse<T> {
    private T data;

    public ApiResponse(T data){
        this.data = data;
    }
    public T getData() {
        return data;
    }
}
