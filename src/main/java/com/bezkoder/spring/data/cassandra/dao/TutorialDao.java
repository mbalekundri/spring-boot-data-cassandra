package com.bezkoder.spring.data.cassandra.dao;

import com.bezkoder.spring.data.cassandra.model.Tutorial;
import org.springframework.data.cassandra.repository.AllowFiltering;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TutorialDao {

    public Tutorial save(Tutorial tutorial);

    public Optional<Tutorial> findById(UUID id);

    @AllowFiltering
    public List<Tutorial> findByTitleContaining(String title);

    public List<Tutorial> findAll();

    public Tutorial update(UUID id, Tutorial tutorial);

    public String deleteById(UUID id);

    public String deleteAll();

    @AllowFiltering
    public List<Tutorial> findByPublished(boolean published);

}
