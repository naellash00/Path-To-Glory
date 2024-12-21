package com.example.PathOfGlory.Repository;
import com.example.PathOfGlory.Model.BookService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookServiceRepository extends JpaRepository<BookService,Integer> {  //Renad
    BookService findBookServiceById(Integer id);
}