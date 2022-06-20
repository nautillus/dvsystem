package com.ipdev.evote.data.service;

import com.ipdev.evote.data.dto.BlankDto;
import com.ipdev.evote.data.dto.VoterDto;
import com.ipdev.evote.data.model.Blank;
import com.ipdev.evote.data.model.Voter;
import com.ipdev.evote.data.repo.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VoterService {
    @Autowired
    private VoterRepository repo;

    public VoterDto check(String fiscalID) {
        List<Voter> list = repo.findByFiscalId(fiscalID);
        Optional<Voter> first = list.stream().findFirst();
        VoterDto dto = new VoterDto();
        if(!first.isPresent()) {
            dto.setVoted(true);
            dto.addError("Provided fiscal ID not found!");
            dto.seteCode("05");
        } else {
            Voter voter = first.get();
            if(voter.isVoted()) {
                dto.setVoted(true);
                dto.addError("Provided fiscal ID already voted!");
                dto.seteCode("06");
            }
        }
        return dto;
    }
    public VoterDto save(String fiscalId) {
        VoterDto dto = new VoterDto();
        List<Voter> byFiscalId = repo.findByFiscalId(fiscalId);
        Optional<Voter> first = byFiscalId.stream().findFirst();
        Voter save = null;
        if(first.isPresent()) {
            try {
                Voter voter = first.get();
                voter.setVoted(true);
                save = repo.save(voter);
            } catch (Exception e) {
                dto.addError("Failed to apply your vote in the system, Please try again later!");
                dto.seteCode("0666");
            }
            dto.setVoted(save == null ? false : save.isVoted());
        }
        return dto;
    }
}
