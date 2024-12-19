package com.example.PathOfGlory.Repository;
import com.example.PathOfGlory.Model.BookOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOfferingRepository extends JpaRepository<BookOffering,Integer> {  //Renad
    BookOffering findBookOfferingById(Integer id);
}