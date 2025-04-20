package com.epam.training.gen.ai.dto;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ModelList {

    private List<Model> data;
}
