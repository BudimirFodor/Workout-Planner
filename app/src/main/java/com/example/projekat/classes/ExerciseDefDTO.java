package com.example.projekat.classes;

public class ExerciseDefDTO extends ExerciseDef {

    private boolean active;

    public ExerciseDefDTO(String name, int valueType, boolean active) {
        super(name, valueType);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
