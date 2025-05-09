package com.epam.training.gen.ai.dto;

import java.util.List;
import java.util.Map;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScoredPointDto {

    private float score;
    private String uuid;
    private List<Float> embeddingPoints;
    private Map<String, Object> payload;
}