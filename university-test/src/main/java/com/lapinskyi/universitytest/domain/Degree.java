package com.lapinskyi.universitytest.domain;

public enum Degree {
    ASSISTANT("Assistant"),
    ASSOCIATE_PROFESSOR("Associate Proffesor"),
    PROFESSOR("Professor");
    
    private String degreeName;
    
    private Degree(String degreeName) {
        this.degreeName = degreeName;
    }
    
    @Override
    public String toString() {
        return degreeName;
    }
}
