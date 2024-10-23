package com.angaar.quiz_service.controllers;

import com.angaar.quiz_service.jwt.JwtUtil;
import com.angaar.quiz_service.models.Quiz;

import com.angaar.quiz_service.service.QuizService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired    
    private QuizService quizService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/getAll")
    @CrossOrigin(origins = "http://localhost:8082")
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/saveQuiz")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<?>  createQuiz(HttpServletRequest request, @RequestBody Quiz quiz) {
    	String token = jwtUtil.getTokenFromCookies(request);
    	String userId = jwtUtil.extractId(token);
    	System.out.println("Extasfasdfracted user: " + userId);
        Quiz createdQuiz = quizService.createQuiz(quiz, userId);
        return ResponseEntity.ok(createdQuiz);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:8082")
    public String deleteQuiz(@PathVariable String id) {
        quizService.deleteQuiz(id);
        return id;
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable String id, @RequestBody Quiz quizDetails) {
    	System.out.println("called UPDATE QUIZ");
    	Quiz updatedQuiz = quizService.updateQuiz(id, quizDetails);
    	System.out.println("RESPONSE RETURNED: " + ResponseEntity.ok(updatedQuiz).toString());
        return ResponseEntity.ok(updatedQuiz);
    }

}