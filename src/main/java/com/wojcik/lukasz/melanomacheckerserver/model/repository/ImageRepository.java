package com.wojcik.lukasz.melanomacheckerserver.model.repository;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Mole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Mole, Long> {

}
