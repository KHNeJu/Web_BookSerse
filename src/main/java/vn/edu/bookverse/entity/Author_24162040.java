package vn.edu.bookverse.entity;
import jakarta.persistence.*;
@Entity(name="Author") @Table(name="author") public class Author_24162040 { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="author_id") public Long id; @Column(nullable=false) public String name; @Column(length=2000) public String biography; public Author_24162040(){} public Author_24162040(String name){this.name=name;} public Long getId(){return id;} public String getName(){return name;} public String getBiography(){return biography;} }
