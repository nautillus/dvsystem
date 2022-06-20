package com.ipdev.evote.data.dto;

import com.ipdev.evote.data.model.Party;

import java.util.List;

public class PartyDto extends AbstractResponseDto{
    private List<Party> parties;
    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }
}
