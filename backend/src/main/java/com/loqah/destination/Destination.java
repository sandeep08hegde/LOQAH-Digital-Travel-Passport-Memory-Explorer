package com.loqah.destination;

import jakarta.persistence.*;
import java.util.UUID;

@Entity @Table(name="destinations")
public class Destination {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @Column(nullable=false,length=140) private String name;
    @Column(nullable=false,length=80) private String state;
    @Column(nullable=false,length=40) private String category;
    @Column(length=1000) private String description;
    @Column(length=500) private String imageUrl;
    private Double latitude;
    private Double longitude;
    private boolean active=true;
    protected Destination() {}
    public Destination(String name,String state,String category,String description,String imageUrl,Double latitude,Double longitude) {
        this.name=name;this.state=state;this.category=category;this.description=description;this.imageUrl=imageUrl;this.latitude=latitude;this.longitude=longitude;
    }
    public UUID getId(){return id;} public String getName(){return name;} public String getState(){return state;}
    public String getCategory(){return category;} public String getDescription(){return description;}
    public String getImageUrl(){return imageUrl;} public Double getLatitude(){return latitude;} public Double getLongitude(){return longitude;}
}