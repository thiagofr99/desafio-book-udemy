package com.devthiagofurtado.controller;

import com.devthiagofurtado.data.model.Person;
import com.devthiagofurtado.data.vo.PersonVO;
import com.devthiagofurtado.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("api/person/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO buscarPorId(@PathVariable(value = "id") Long id) {
        PersonVO personVO = personService.findById(id);
        personVO.add(linkTo(methodOn(PersonController.class).buscarPorId(id)).withSelfRel());
        return personVO;
    }

    @GetMapping(value = {}, produces = {"application/json", "application/xml", "application/x-yaml"})
    public List<PersonVO> buscarTodos() {
        List<PersonVO> personVOS = personService.findAll();
        personVOS.forEach(p->{
            p.add(linkTo(methodOn(PersonController.class).buscarPorId(p.getKey())).withSelfRel());
        });
        return personVOS;
    }

    @PostMapping(value = "/salvar", produces = {"application/json", "application/xml", "application/x-yaml"}
            , consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO salvar(@RequestBody PersonVO person) {
        PersonVO personVO =personService.create(person);
        personVO.add(linkTo(methodOn(PersonController.class).buscarPorId(personVO.getKey())).withSelfRel());
        return personVO;
    }

    @PutMapping( value = "/atualizar", produces = {"application/json", "application/xml", "application/x-yaml"}
            , consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO atualizar(@RequestBody PersonVO person) {
        PersonVO personVO = personService.update(person);
        personVO.add(linkTo(methodOn(PersonController.class).buscarPorId(personVO.getKey())).withSelfRel());
        return personVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

}
