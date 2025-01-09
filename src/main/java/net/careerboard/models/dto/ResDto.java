package net.careerboard.models.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResDto<T> implements Serializable {
    private Boolean success;

    private ResDTOMessage message;

    private T payload;

    public ResDto<Object> toResponse() {
        return new ResDto<>(this.success, this.message, this.payload);
    }

    @SuppressWarnings("unchecked")
    public <R> ResDto<R> castData() {
        return new ResDto<>(this.success, this.message, (R) this.payload);
    }
}
