package com.loqah.visit;
import com.loqah.destination.Destination;
import com.loqah.user.User;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="visits",uniqueConstraints=@UniqueConstraint(columnNames={"user_id","destination_id"}))
public class Visit {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(optional=false) private User user;
 @ManyToOne(optional=false) private Destination destination;
 @Column(nullable=false) private Instant visitedAt;
 @Column(nullable=false,length=20) private String status="PENDING";
 @Column(length=500) private String note;
 protected Visit(){}
 public Visit(User user,Destination destination,String note){this.user=user;this.destination=destination;this.note=note;this.visitedAt=Instant.now();}
 public UUID getId(){return id;} public Destination getDestination(){return destination;} public Instant getVisitedAt(){return visitedAt;} public String getStatus(){return status;}
 public void award(){this.status="AWARDED";}
}