package me.study.jpa.chap08;

import javax.persistence.DiscriminatorValue;

//@Entity
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;

    private String actor;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
