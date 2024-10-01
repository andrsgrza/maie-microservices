package com.angaar.quiz_service.service;

import com.angaar.quiz_service.models.Quiz;
import com.angaar.quiz_service.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(String id) {
        return quizRepository.findById(id);
    }

    public Quiz createQuiz(Quiz quiz) {
    	Quiz.Metadata meta = quiz.getMetadata();
    	meta.setCreationDate(LocalDate.now().toString());
    	quiz.setMetadata(meta);
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(String id) {
        quizRepository.deleteById(id);
    }

    public Quiz updateQuiz(String id, Quiz quizDetails) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setTitle(quizDetails.getTitle());
        quiz.setMetadata(quizDetails.getMetadata());
  
        quiz.setSections(quizDetails.getSections());
        return quizRepository.save(quiz);
    }
}