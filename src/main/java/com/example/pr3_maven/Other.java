package com.example.pr3_maven;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Other {

    public static List<List<String>> ReadModelsWithFields() {
        String packageName = "com.example.pr3_maven.Models";
        List<List<String>> modelsWithFields = new ArrayList<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace(".", "/");
            File packageDirectory = new File(classLoader.getResource(path).getFile());

            if (packageDirectory.exists() && packageDirectory.isDirectory()) {
                File[] files = packageDirectory.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".class")) {
                            String className = file.getName().replace(".class", "");
                            List<String> fieldNames = getModelFieldsByName(className);
                            List<String> modelInfo = new ArrayList<>();
                            modelInfo.add(className);
                            modelInfo.addAll(fieldNames);
                            modelsWithFields.add(modelInfo);
                        }
                    }
                }
            } else {
                System.err.println("Указанный пакет не существует или не является директорией.");
            }
        } catch (NullPointerException e) {
            System.err.println("Пакет не найден.");
        }

        return modelsWithFields;
    }


    public static List<String> getModelFieldsByName(String modelName) {
        String fullClassName = "com.example.pr3_maven.Models." + modelName;
        List<String> fieldNames = new ArrayList<>();

        try {
            Class<?> modelClass = Class.forName(fullClassName);
            Field[] fields = modelClass.getDeclaredFields();
            for (Field field : fields) {
                fieldNames.add(field.getName());
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден: " + fullClassName);
        }

        return fieldNames;
    }


}