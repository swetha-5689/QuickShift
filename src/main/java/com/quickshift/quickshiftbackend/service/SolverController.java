package com.quickshift.quickshiftbackend.service;


import com.quickshift.quickshiftbackend.models.RequestModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solve")
@CrossOrigin
@Validated
public class SolverController {

    @PostMapping(path = "/")
    public RequestModel solve(@RequestBody RequestModel request) {
        return request;
    }

}
