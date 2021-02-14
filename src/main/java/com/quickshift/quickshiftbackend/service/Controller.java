package com.quickshift.quickshiftbackend.service;

import com.quickshift.quickshiftbackend.models.Nurse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nurses")
@CrossOrigin
@Validated
public class Controller {

    private final NurseService nurseService = new NurseService();

    @GetMapping("/")
    public ResponseEntity<List<Nurse>> getNurses() {
        return new ResponseEntity<>(nurseService.getNurseList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nurse> getNursesById(@PathVariable String id) {
        return new ResponseEntity<Nurse>(nurseService.getNurseById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Nurse> createNurse(@RequestBody Map<String, String> json) {
        return new ResponseEntity<Nurse>(nurseService.addNurse(json.get("name")), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<HttpStatus> deleteNurse(@RequestBody Map<String, String> json){
        if(nurseService.removeNurse(json.get("id"))) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
