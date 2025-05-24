package com.bezkoder.spring.data.cassandra.dao;

import com.bezkoder.spring.data.cassandra.model.Tutorial;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TutorialDaoImpl implements TutorialDao {

    private final CqlSession tutorialCqlSession;

    private PreparedStatement createTutorialStatement;

    private PreparedStatement getTutorialByIdStatement;

    private PreparedStatement updateTutorialStatement;

    private PreparedStatement deleteTutorialStatement;

    private PreparedStatement deleteAllTutorialStatement;

    public TutorialDaoImpl(CqlSession tutorialCqlSession) {
        this.tutorialCqlSession = tutorialCqlSession;
        initPreparedStatements();
    }

    // Create
    @Transactional
    public Tutorial save(Tutorial tutorial) {
        if (tutorial.getId() == null) {
            tutorial.setId(UUID.randomUUID());
        }
        BoundStatement boundStatement = createTutorialStatement.bind(
                tutorial.getId(),
                tutorial.getTitle(),
                tutorial.getDescription(),
                tutorial.isPublished()
        );
        // Execute the prepared statement
       tutorialCqlSession.execute(boundStatement);
       return this.findById(tutorial.getId()).orElse(null);
    }

    public Optional<Tutorial> findById(UUID id) {
        BoundStatement bound = getTutorialByIdStatement.bind(id);
        ResultSet resultSet = tutorialCqlSession.execute(bound);
        List<Row> rows = resultSet.all();
        if (rows != null && !rows.isEmpty()) {
            return Optional.of(new Tutorial(
                    rows.get(0).getUuid("id"),
                    rows.get(0).getString("title"),
                    rows.get(0).getString("description"),
                    rows.get(0).getBoolean("published"))
            );
        }
        return null;
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title) {
        String query = "SELECT * FROM tutorial WHERE title LIKE ? ALLOW FILTERING ";
        BoundStatement boundStatement = tutorialCqlSession.prepare(query).bind("%" + title + "%");
        ResultSet resultSet = tutorialCqlSession.execute(boundStatement);
        List<Tutorial> tutorials = new ArrayList<>();
        for (Row row : resultSet) {
            tutorials.add(new Tutorial(
                    row.getUuid("id"),
                    row.getString("title"),
                    row.getString("description"),
                    row.getBoolean("published")
            ));
        }
        return tutorials;
    }


    public List<Tutorial> findAll() {
        ResultSet resultSet = tutorialCqlSession.execute(getGetAllTutorialsCQL());
        List<Tutorial> tutorials = new ArrayList<>();
        for (Row row : resultSet) {
            tutorials.add(new Tutorial(
                    row.getUuid("id"),
                    row.getString("title"),
                    row.getString("description"),
                    row.getBoolean("published")
            ));
        }
        return tutorials;
    }

    @Transactional
    public Tutorial update(UUID id, Tutorial tutorial) {
        BoundStatement bound = updateTutorialStatement.bind(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), id);
        tutorialCqlSession.execute(bound);
        return this.findById(id).orElse(null);
    }



    @Transactional
    public String deleteById(UUID id) {
        String query = "DELETE FROM tutorial WHERE id = ?";
        BoundStatement boundStatement = deleteTutorialStatement.bind(id);
        tutorialCqlSession.execute(boundStatement);
        return "Tutorial deleted successfully";
    }

    @Transactional
    public String deleteAll() {
        String query = "TRUNCATE tutorial";
        BoundStatement boundStatement = deleteAllTutorialStatement.bind();
        ResultSet rs = tutorialCqlSession.execute(boundStatement);
        return "All tutorials deleted successfully";
    }

    public List<Tutorial> findByPublished(boolean published) {
        String query = "SELECT * FROM tutorial WHERE published = ? ALLOW FILTERING";
        BoundStatement boundStatement = tutorialCqlSession.prepare(query).bind(published);
        ResultSet resultSet = tutorialCqlSession.execute(boundStatement);
        List<Tutorial> tutorials = new ArrayList<>();
        for (Row row : resultSet) {
            tutorials.add(new Tutorial(
                    row.getUuid("id"),
                    row.getString("title"),
                    row.getString("description"),
                    row.getBoolean("published")
            ));
        }
        return tutorials;
    }

    private void initPreparedStatements() {
        createTutorialStatement = tutorialCqlSession.prepare(getCreateTutorialCQL());
        getTutorialByIdStatement = tutorialCqlSession.prepare(getGetTutorialByIdCQL());
        updateTutorialStatement = tutorialCqlSession.prepare(getUpdateTutorialCQL());
        deleteTutorialStatement = tutorialCqlSession.prepare(getDeleteTutorialCQL());
        deleteAllTutorialStatement = tutorialCqlSession.prepare(getDeleteAllTutorialCQL());
    }

    private String getCreateTutorialCQL() {
        return "INSERT INTO tutorial (id, title, description, published) VALUES (?, ?, ?, ?)";
    }

    private String getGetTutorialByIdCQL() {
        return "SELECT * FROM tutorial WHERE id = ?";
    }

    private String getGetAllTutorialsCQL() {
        return "SELECT * FROM tutorial";
    }

    private String getUpdateTutorialCQL() {
        return "UPDATE tutorial SET title = ?, description = ?, published = ? WHERE id = ?";
    }

    private String getDeleteTutorialCQL() {
        return "DELETE FROM tutorial WHERE id = ?";
    }

    private String getDeleteAllTutorialCQL() {
        return "TRUNCATE tutorial ";
    }
}
