package com.devthiagofurtado.service;

import com.devthiagofurtado.converter.DozerConverter;
import com.devthiagofurtado.data.model.Person;
import com.devthiagofurtado.data.vo.PersonVO;
import com.devthiagofurtado.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonVO create(PersonVO person) {

        return DozerConverter.parseObject(personRepository.save(DozerConverter.parseObject(person,Person.class)), PersonVO.class);
    }

    public PersonVO update(PersonVO person) {
        Person personAntigo = personRepository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("Person não localizado."));
        personAntigo.setAddress(person.getAddress());
        personAntigo.setFirstName(person.getFirstName());
        personAntigo.setLastName(person.getLastName());
        personAntigo.setGender(person.getGender());

        return DozerConverter.parseObject(personRepository.save(personAntigo), PersonVO.class);
    }

    public void delete(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person não localizado."));
        personRepository.delete(person);
    }

    public PersonVO findById(Long id) {
        return DozerConverter.parseObject(personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person não localizado.")), PersonVO.class);
    }

    public List<PersonVO> findAll() {
        return DozerConverter.parseListObjects(personRepository.findAll(), PersonVO.class);
    }
}
