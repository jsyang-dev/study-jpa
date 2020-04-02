package me.study.jpa.chap08;

import javax.persistence.DiscriminatorValue;

//@Entity
@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
