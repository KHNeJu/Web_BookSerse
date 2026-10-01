package vn.edu.bookverse.config;

import jakarta.persistence.*;
import java.util.*;

public final class Jpa_24162040 {
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("bookverse", Map.of(
            "jakarta.persistence.jdbc.url", "jdbc:h2:file:./data/bookverse;MODE=MySQL;AUTO_SERVER=TRUE"
    ));
    private Jpa_24162040() {}
    public static EntityManager em() { return EMF.createEntityManager(); }
}
