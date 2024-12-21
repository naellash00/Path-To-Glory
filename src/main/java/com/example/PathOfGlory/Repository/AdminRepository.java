package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer>{
    Admin findAdminById(Integer id);
}