package com.ipdev.evote.model;

import java.time.LocalDateTime;

public record Blank (int id, BlankStatus status, String token,
                     RowBlankDto selected, LocalDateTime inserted, boolean counted) {}