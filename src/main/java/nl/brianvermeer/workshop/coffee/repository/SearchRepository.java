package nl.brianvermeer.workshop.coffee.repository;

import nl.brianvermeer.workshop.coffee.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

@Repository
public class SearchRepository {

    @Autowired
    EntityManager em;

    @Autowired
    DataSource dataSource;

    public List<Product> searchProduct (String input) {
        var lowerInput = input.toLowerCase(Locale.ROOT);
        String queryText = "SELECT * FROM Product " +
                           "WHERE lower(description) LIKE CONCAT('%', ?1, '%') OR " +
                           "      lower(product_name) LIKE CONCAT('%', ?2, '%')";

        var query = em.createNativeQuery(queryText, Product.class);
        query.setParameter(1, lowerInput);
        query.setParameter(2, lowerInput);
        return (List<Product>) query.getResultList();
    }

}
