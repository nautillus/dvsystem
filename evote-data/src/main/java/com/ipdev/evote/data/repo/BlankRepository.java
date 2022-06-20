package com.ipdev.evote.data.repo;

import com.ipdev.evote.data.model.Blank;
import com.ipdev.evote.data.model.Party;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlankRepository extends CrudRepository<Blank, Long> {
    List<Blank> findAll();
    List<Blank> findByVoted(Party party);
    Blank findById(long id);
}
