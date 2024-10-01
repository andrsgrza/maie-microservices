package com.angaar.quiz_service.controllers;

import com.angaar.quiz_service.models.Quiz;
import com.angaar.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired    
    private QuizService quizService;

    @GetMapping("/getAll")
    @CrossOrigin(origins = "http://localhost:8081")
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/saveQuiz")
    @CrossOrigin(origins = "http://localhost:8081")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:8081")
    public String deleteQuiz(@PathVariable String id) {
        quizService.deleteQuiz(id);
        return id;
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:8081")
    public Quiz updateQuiz(@PathVariable String id, @RequestBody Quiz quizDetails) {
        return quizService.updateQuiz(id, quizDetails);
    }
//  @GetMapping("/{id}")
//  @CrossOrigin(origins = "http://localhost:8081")
//  public Optional<Quiz> getQuizById(@PathVariable String id) {
//      return quizService.getQuizById(id);
//  }
}