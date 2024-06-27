package edu.fbansept.demo.controller;

import edu.fbansept.demo.dao.QuestionDao;
import edu.fbansept.demo.dao.ReponsePossibleDao;
import edu.fbansept.demo.model.Question;
import edu.fbansept.demo.model.ReponsePossible;
import edu.fbansept.demo.security.IsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reponsePossible")
public class ReponsePossibleController {

    @Autowired
    ReponsePossibleDao reponsePossibleDao;

    @Autowired
    QuestionDao questionDao;

    @IsAdmin
    @PostMapping("")
    public ResponseEntity<ReponsePossible> add(@RequestBody ReponsePossible reponsePossible) {
        if (reponsePossible.getQuestion() == null || reponsePossible.getQuestion().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Question> questionOptional = questionDao.findById(reponsePossible.getQuestion().getId());

        if (questionOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        reponsePossible.setQuestion(questionOptional.get());
        reponsePossibleDao.save(reponsePossible);

        return new ResponseEntity<>(reponsePossible, HttpStatus.CREATED);
    }
}
