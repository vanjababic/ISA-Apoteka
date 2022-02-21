package com.example.ISAISA.DTO;

import javax.persistence.Column;

public class MedicationRegDTO {

    private String code ;

    private String name;


    private String type_med;

    private String shape_med;


    private String ingredients;

    private String producer;






    private String contraindication;


    private Integer recommended_daily_intake;

    private String alternative;

    public MedicationRegDTO() {
    }

    public MedicationRegDTO(String code, String name, String type_med, String shape_med, String ingredients, String producer,  String contraindication, Integer recommended_daily_intake, String alternative) {
        this.code = code;
        this.name = name;
        this.type_med = type_med;
        this.shape_med = shape_med;
        this.ingredients = ingredients;
        this.producer = producer;

        this.contraindication = contraindication;
        this.recommended_daily_intake = recommended_daily_intake;
        this.alternative = alternative;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_med() {
        return type_med;
    }

    public void setType_med(String type_med) {
        this.type_med = type_med;
    }

    public String getShape_med() {
        return shape_med;
    }

    public void setShape_med(String shape_med) {
        this.shape_med = shape_med;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }


    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    public Integer getRecommended_daily_intake() {
        return recommended_daily_intake;
    }

    public void setRecommended_daily_intake(Integer recommended_daily_intake) {
        this.recommended_daily_intake = recommended_daily_intake;
    }
}
