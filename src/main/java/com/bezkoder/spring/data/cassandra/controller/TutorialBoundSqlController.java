package com.bezkoder.spring.data.cassandra.controller;

import com.bezkoder.spring.data.cassandra.dao.TutorialDao;
import com.bezkoder.spring.data.cassandra.model.Tutorial;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/bound-sql/tutorials")
public class TutorialBoundSqlController {

    @Autowired
    TutorialDao tutorialDao;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();

            if (title == null)
                tutorialDao.findAll().forEach(tutorials::add);
            else
                tutorialDao.findByTitleContaining(title).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") UUID id) {
        Optional<Tutorial> tutorialData = tutorialDao.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial _tutorial = tutorialDao.save(new Tutorial(Uuids.timeBased(), tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") UUID id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialDao.findById(id);
        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialDao.update(id, _tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public String deleteTutorial(@PathVariable("id") UUID id) {
        try {
            String returnMsg = tutorialDao.deleteById(id);
            return returnMsg;
//            return new ResponseEntity<String>(returnMsg, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

    @DeleteMapping(value="/deleteAll", produces = "application/json")
    public String deleteAllTutorials() {
        try {
            String returnMsg = tutorialDao.deleteAll();
            return returnMsg;
//            return new ResponseEntity<String>(returnMsg, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
    }

    @GetMapping(value = "/published", produces = "application/json")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        try {
            List<Tutorial> tutorials = tutorialDao.findByPublished(true);
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Tutorial>>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findByTitle/{title}")
    public ResponseEntity<List<Tutorial>> findByTitle(@PathVariable("title") String title) {
        try {
            List<Tutorial> tutorials = tutorialDao.findByTitleContaining(title);
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
