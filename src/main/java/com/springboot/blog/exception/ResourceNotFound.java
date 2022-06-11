package com.springboot.blog.exception;

public class ResourceNotFound extends RuntimeException{

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFound(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s not found with %s : %s",resourceName, fieldName, fieldValue));
        this.fieldName= fieldName;
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
