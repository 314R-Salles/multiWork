package com.psalles.multiWork.Poc.Validation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(description = "POC for bean validation")
@RestController
@RequestMapping("/pocs")
public class ValidationController {

    @ApiOperation("Use annotation validation")
    @PostMapping(path = "/validation", consumes = "application/json", produces = "application/json")
    public ValidatedBean testBeanWithValidAnnotation(@Valid @RequestBody ValidatedBean bean) {
        return bean;
    }

    @ApiOperation("Use annotation validation")
    @GetMapping(path = "/validation")
    public List<AnnotatedField> printAnnotation() {
        List<AnnotatedField> res = new ArrayList<>();
        for (Field f : ValidatedBean.class.getDeclaredFields()) {
            Annotation[] annotations = f.getDeclaredAnnotations();
            res.addAll(Arrays.stream(annotations).map(annotation -> new AnnotatedField(f, annotation)).collect(toList()));
        }
        return res;
    }

}
