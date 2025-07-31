package com.FadiMagdi.URL_Shortner.Repositories;

import com.FadiMagdi.URL_Shortner.Domain.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<URL,Integer> {



    Optional<URL> findByShortCode(String shortCode);
}
