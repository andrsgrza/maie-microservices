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
    public ResponseEntity<?> getAllQuizzes(HttpServletRequest request) {
        String token = jwtUtil.getTokenFromCookies(request);
        String userId = jwtUtil.extractId(token);
        System.out.println("Token: " + token + "\n UserId: " + userId);

        // Retrieve quizzes with entitlement information
        List<Object> quizzes = quizService.getAllQuizzesForUser(userId);
        if(quizzes == null) {
        	return ResponseEntity.notFound().build();
        }
        System.out.println("Quizzes is not null" + quizzes.size());
        return ResponseEntity.ok(quizzes);
    }


    @PostMapping("/saveQuiz")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<?> createQuiz(HttpServletRequest request, @RequestBody Quiz quiz) {
        String token = jwtUtil.getTokenFromCookies(request);
        String userId = jwtUtil.extractId(token);
        Quiz createdQuiz = quizService.createQuiz(quiz, userId);
        return ResponseEntity.ok(createdQuiz);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<?> deleteQuiz(HttpServletRequest request, @PathVariable String id) {
        String token = jwtUtil.getTokenFromCookies(request);
        String userId = jwtUtil.extractId(token);
        quizService.deleteQuiz(id, userId);
        return ResponseEntity.ok("Quiz deleted");
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<Quiz> updateQuiz(HttpServletRequest request, @PathVariable String id, @RequestBody Quiz quizDetails) {
        String token = jwtUtil.getTokenFromCookies(request);
        String userId = jwtUtil.extractId(token);
        Quiz updatedQuiz = quizService.updateQuiz(id, quizDetails, userId);
        return ResponseEntity.ok(updatedQuiz);
    }


}