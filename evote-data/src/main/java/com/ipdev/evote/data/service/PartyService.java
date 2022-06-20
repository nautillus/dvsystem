package com.ipdev.evote.data.service;

import com.ipdev.evote.data.dto.PartyDto;
import com.ipdev.evote.data.model.Party;
import com.ipdev.evote.data.repo.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {
    @Autowired
    private PartyRepository repo;

    public PartyDto getParties(boolean sorted) {
        List<Party> parties = sorted ? repo.findAllByOrderByOrderIdAsc() : repo.findAll();
        PartyDto dto = new PartyDto();
        dto.setParties(parties);
        return dto;
    }
}
