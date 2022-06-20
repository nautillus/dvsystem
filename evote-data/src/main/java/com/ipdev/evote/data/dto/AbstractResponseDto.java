package com.ipdev.evote.data.dto;

import java.util.ArrayList;
import java.util.List;

public class AbstractResponseDto {
    private List<String> errors = new ArrayList<>();
    private String eCode;

    public List<String> getErrors() {
        return errors;
    }

    public void seteCode(String eCode) {
        this.eCode = eCode;
    }

    public String geteCode() {
        return eCode;
    }

    public void addError(String err) {
        errors.add(err);
    }

    public void clearError(){
        errors.clear();
    }
}
