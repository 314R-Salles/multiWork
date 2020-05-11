package com.psalles.multiWork.Poc.Validation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)

public class ValidatedBean {

    @NotNull
    private Integer notNull;

    @Min(value = 10, message = "should be higher than 10")
    private int min;

    @Max(value = 100, message = "should be smaller than 100")
    private int max;

    @NotBlank(message = "Shouldn't be blank")
    private String notBlank;

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 5, max = 50, message = "Length should be between 5 and 50")
    private String size;

    @AssertTrue(message = "should be true")
    private boolean assertTrue;


}
