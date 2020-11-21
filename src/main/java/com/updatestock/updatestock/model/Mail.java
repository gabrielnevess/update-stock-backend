package com.updatestock.updatestock.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> model;
}