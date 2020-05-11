package com.psalles.multiWork.Poc.Validation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AnnotatedField {

    private String fieldName;
    private String annotation;


    public AnnotatedField(Field field, Annotation annotation) {
        this.fieldName = field.getName();
        this.annotation = annotation.toString();
    }

}
