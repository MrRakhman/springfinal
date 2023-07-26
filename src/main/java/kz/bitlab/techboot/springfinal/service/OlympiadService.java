package kz.bitlab.techboot.springfinal.service;


import kz.bitlab.techboot.springfinal.model.Olympiads;
import kz.bitlab.techboot.springfinal.model.User;
import kz.bitlab.techboot.springfinal.repository.OlympiadsRepository;
import kz.bitlab.techboot.springfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OlympiadService {
    @Autowired
    private OlympiadsRepository olympiadsRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Olympiads> getOlympiadsList(){
        return olympiadsRepository.findAll();
    }

    public Olympiads getOlympiad(Long id){
        return olympiadsRepository.findById(id).orElse(null);
    }

    public void addOlympiad(Olympiads olympiad){
        olympiadsRepository.save(olympiad);
    }
}
