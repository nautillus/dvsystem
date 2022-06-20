package com.ipdev.evote.data.repo;

import com.ipdev.evote.data.model.Voter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoterRepository extends CrudRepository<Voter, Long> {
    List<Voter> findByFiscalId(String fiscalId);
    Voter findById(long id);
}