package com.imedical.mobiledoctor.entity;


public class LabelValue<T> {
    public String label;
    public String value;
    public Integer color;
    public String textStyle;
    private T data;

    public LabelValue(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public LabelValue(String label, String value, T additionalData) {
        this.label = label;
        this.value = value;
        this.data = additionalData;
    }

    public T getAdditionalData() {
        return data;
    }

    public LabelValue(String label, String value, Integer color) {
        this.label = label;
        this.value = value;
        this.color = color;
    }

    public LabelValue(String label, String value, Integer color, String textStyle) {
        this.label = label;
        this.value = value;
        this.color = color;
        this.textStyle = textStyle;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return label;
    }
}
