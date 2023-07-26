package kz.bitlab.techboot.springfinal.service;


import kz.bitlab.techboot.springfinal.model.Problem;
import kz.bitlab.techboot.springfinal.model.User;
import kz.bitlab.techboot.springfinal.repository.ProblemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {
    @Autowired
    private ProblemsRepository problemsRepository;

    public Problem getProblem(Long id){
       return problemsRepository.findById(id).orElse(null);
    }

    public List<Problem> getProblemsList(){
        return problemsRepository.findAll();
    }

    public void addProblem(Problem problem){

        problemsRepository.save(problem);
    }

    public void saveProblem(Problem problem){
        problemsRepository.save(problem);
    }

    public String checkAnswer(String usersAnswer, String problemAnswer){
        return null;
    }

}
