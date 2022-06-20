package com.ipdev.evote.data.service;

import com.ipdev.evote.data.dto.BlankDto;
import com.ipdev.evote.data.model.Blank;
import com.ipdev.evote.data.model.Party;
import com.ipdev.evote.data.repo.BlankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class BlankService {
    @Autowired
    private BlankRepository repo;
    public BlankDto save(Blank blank){
        BlankDto dto = new BlankDto();
        Blank save = null;
        try {
            save = repo.save(blank);
        } catch (Exception e) {
            dto.addError("Failed to apply your vote in the system, Please try again later!");
            dto.seteCode("0666");
        }
        dto.setBlanks(blank == null ? Collections.emptyList() : Arrays.asList(save));
        return dto;
    }
}
