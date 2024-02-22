package ru.melnikov.springBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.melnikov.springBoot.models.PersonUser;
import ru.melnikov.springBoot.repositories.PersonRepository;

@Service
public class RegistrationPersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public RegistrationPersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(PersonUser personUser){
        //Добавляем кодировку (теперь в бд пароль юзера будет в формате бикрипт-кодировки)
        personUser.setPassword(passwordEncoder.encode(personUser.getPassword()));
        personRepository.save(personUser);
    }
}
