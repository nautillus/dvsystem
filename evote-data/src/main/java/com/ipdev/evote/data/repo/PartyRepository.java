package com.ipdev.evote.data.repo;

import com.ipdev.evote.data.model.Party;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartyRepository extends CrudRepository<Party, Long> {
    List<Party> findAll();
    List<Party> findAllByOrderByOrderIdAsc();
    List<Party> findAllByOrderByOrderIdDesc();
    Party findById(long id);
}
