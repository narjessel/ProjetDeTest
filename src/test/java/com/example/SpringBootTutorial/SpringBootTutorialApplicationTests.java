package com.example.SpringBootTutorial;

import com.example.SpringBootTutorial.dao.FakePersonDataAccessService;
import com.example.SpringBootTutorial.model.Person;
import com.example.SpringBootTutorial.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootTutorialApplicationTests {
    @Autowired
    private PersonService service;

    @MockBean
    private FakePersonDataAccessService repository;

    @Test
    public void addPersonTest() {
        UUID uuid = UUID.randomUUID();
        Person person = new Person(uuid,"Narjess");
        when(repository.insertPerson(person)).thenReturn(1);
        assertEquals(1,service.addPerson(person));
    }

    @Test
    public void getAllPeopleTest() {
        when(repository.selectAllPeople()).thenReturn(Stream
                .of(new Person(UUID.randomUUID(),"Narjess ElHammouti"),new Person(UUID.randomUUID(),"NJS ElHammouti")).collect(Collectors.toList()));
        assertEquals(2, service.getAllPeople().size());
    }

    @Test
    public void getPersonByIdTest() {
        UUID idperson = UUID.randomUUID();
        Person p = new Person(idperson,"Narjess ElHammouti");
        when(repository.selectPersonById(idperson)).thenReturn(Optional.of(p));
        assertEquals(false, service.getPersonById(idperson).isEmpty());
        //CHECK
        Optional<Person> checkperson = service.getPersonById(idperson);
        UUID idcheckperson =checkperson.get().getId();
        String namecheckperson =checkperson.get().getName();
        System.out.println("The ID: "+idcheckperson);
        System.out.println("The NAME: "+namecheckperson);
    }



    @Test
    public void deletePersonByIdTest(){
        UUID uuid = UUID.randomUUID();
        Person person = new Person(uuid,"Narjess");
        service.deletePerson(uuid);
        verify(repository,times(1)).deletePersonById(uuid);
    }

    @Test
    public void updatePersonByIdTest(){
        UUID uuid = UUID.randomUUID();
        Person person = new Person(uuid,"Narjess");
        Person person2 = new Person(UUID.randomUUID(),"Modified");
        service.updatePerson(uuid,person2);
        verify(repository,times(1)).updatePersonById(uuid,person2);
    }




}
