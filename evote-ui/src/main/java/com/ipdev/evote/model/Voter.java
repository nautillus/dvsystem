package com.ipdev.evote.model;
public record Voter (int id, String fiscalID, String serialID, boolean voted) {}