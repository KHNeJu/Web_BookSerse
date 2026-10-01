package vn.edu.bookverse.entity;

import jakarta.persistence.*;
import java.math.*;
import java.text.*;
import java.time.*;
import java.util.*;

@Entity(name="Book")
@Table(name="books")
public class Book_24162040 {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="bookid") public Long id;
    @Column(nullable=false) public String title;
    @Column(nullable=false, unique=true) public String isbn;
    @ManyToMany(fetch=FetchType.EAGER) @JoinTable(name="book_author", joinColumns=@JoinColumn(name="bookid"), inverseJoinColumns=@JoinColumn(name="author_id")) public Set<Author_24162040> authors = new LinkedHashSet<>();
    @Column(nullable=false) public String publisher;
    public LocalDate publisherDate;
    public int quantity;
    @Column(precision=12, scale=0) public BigDecimal price = BigDecimal.ZERO;
    public String coverUrl;
    @Column(length=4000) public String description;
    @Transient public long reviewCount;
    public Long getId(){ return id; } public String getTitle(){ return title; } public String getIsbn(){ return isbn; }
    public Set<Author_24162040> getAuthors(){ return authors; } public Author_24162040 getAuthor(){ return authors.stream().findFirst().orElse(null); }
    public String getPublisher(){ return publisher; } public LocalDate getPublisherDate(){ return publisherDate; } public int getQuantity(){ return quantity; }
    public BigDecimal getPrice(){ return price == null ? BigDecimal.ZERO : price; }
    public String getFormattedPrice(){ return NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(getPrice()); }
    public String getCoverUrl(){ return coverUrl; }
    public String getDescription(){ return description; } public long getReviewCount(){ return reviewCount; }
}
