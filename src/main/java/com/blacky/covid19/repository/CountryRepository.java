package com.blacky.covid19.repository;

import com.blacky.covid19.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, String> {

    @Override
    List<Country> findAll();

}
