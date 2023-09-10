package com.example.pr3_maven;

import java.util.List;

public class ModelInfoDTO {
    private String modelName;
    private List<String> fieldNames;
    public ModelInfoDTO(String modelName, List<String> fieldNames) {
        this.modelName = modelName;
        this.fieldNames = fieldNames;
    }

    public String getModelName() {
        return modelName;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }}
