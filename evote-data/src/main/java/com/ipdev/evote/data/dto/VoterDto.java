package com.ipdev.evote.data.dto;

public class VoterDto extends AbstractResponseDto{
    private boolean voted;
    public boolean isVoted() {
        return voted;
    }
    public void setVoted(boolean voted) {
        this.voted = voted;
    }
}
