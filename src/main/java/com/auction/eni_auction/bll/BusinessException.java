package com.auction.eni_auction.bll;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

    private List<String> errorList;

    public List<String> getErrorList() {
        return errorList;
    }

    public BusinessException() {
        errorList = new ArrayList<String>();
    }

    public void addError(String error) {
        if(!errorList.contains(error)) {
            errorList.add(error);
        }
    }

    public boolean hasErrors() {
        return errorList.size() > 0;
    }
}