package ru.regiuss.practice.dictinoary.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private T data;
    private boolean success;

    public ResponseDTO(T data) {
        this.data = data;
        this.success = true;
    }
}
