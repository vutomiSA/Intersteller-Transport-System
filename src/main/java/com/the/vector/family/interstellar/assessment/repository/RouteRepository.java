/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.the.vector.family.interstellar.assessment.repository;

import com.the.vector.family.interstellar.assessment.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vutomi Ngobeni
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Short>{
    
}
