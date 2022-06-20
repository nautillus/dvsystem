package com.ipdev.evote.data.dto;

import com.ipdev.evote.data.model.Blank;

import java.util.List;

public class BlankDto extends AbstractResponseDto {
    private List<Blank> blanks;

    public List<Blank> getBlanks() {
        return blanks;
    }

    public void setBlanks(List<Blank> blanks) {
        this.blanks = blanks;
    }
}
