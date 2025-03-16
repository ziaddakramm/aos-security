package com.aos.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum Role {
    USER("USER"), MANAGER("MANAGER");
    private final String value;
}
