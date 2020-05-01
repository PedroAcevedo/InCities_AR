package com.example.kudan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Book implements Serializable {

    private String Name;
    private String Description;
    private HashMap<String,String> AugmentedReferences;
    private String Category;

    public Book(String name, String description, HashMap<String, String> augmentedReferences, String category) {
        Name = name;
        Description = description;
        AugmentedReferences = augmentedReferences;
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public ArrayList<String> getAugmentedReferences(){
        return new ArrayList<>(this.AugmentedReferences.keySet());
    }

    public String getMultimediaFile(String reference){
        return this.AugmentedReferences.get(reference);
    }

    public String getCategory() {
        return Category;
    }

}
