package com.deepaksharma.digital_library.repository;

import com.deepaksharma.digital_library.enums.BookType;
import com.deepaksharma.digital_library.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomBookRepositoryImpl implements CustomBookRepository{

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Book> findBookByFilters(String bookTitle, BookType bookType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        if (bookTitle != null && !bookTitle.isEmpty()){
            Predicate titlePredicate = criteriaBuilder.like(bookRoot.get("bookTitle"), "%" + bookTitle + "%");
            predicates.add(titlePredicate);
        }
        if (bookType != null){
            Predicate typePredicate = criteriaBuilder.equal(bookRoot.get("bookType"), bookType);
            predicates.add(typePredicate);
        }
        Predicate finalPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        criteriaQuery.select(bookRoot).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
