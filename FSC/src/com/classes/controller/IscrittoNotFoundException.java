package com.classes.controller;

public class IscrittoNotFoundException extends RuntimeException{

    public IscrittoNotFoundException(){
        super();
    }

    public IscrittoNotFoundException(String error){
        super(error);
    }

}
