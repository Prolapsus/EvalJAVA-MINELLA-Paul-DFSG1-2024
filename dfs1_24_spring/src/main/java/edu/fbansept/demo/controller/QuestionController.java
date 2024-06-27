package edu.fbansept.demo.controller;

import edu.fbansept.demo.dao.QuestionDao;
import edu.fbansept.demo.dao.QuizzDao;
import edu.fbansept.demo.model.Question;
import edu.fbansept.demo.model.Quizz;
import edu.fbansept.demo.security.AppUserDetails;
import edu.fbansept.demo.security.IsAdmin;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    QuizzDao quizzDao;

    @IsAdmin
    @PostMapping("")
    public ResponseEntity<Question> add(
            @RequestBody @Valid Question question,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Quizz> quizzOptional = quizzDao.findById(question.getQuizz().getId());

        if (quizzOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        question.setQuizz(quizzOptional.get());
        questionDao.save(question);

        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }
}
