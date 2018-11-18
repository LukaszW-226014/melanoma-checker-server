package com.wojcik.lukasz.melanomacheckerserver.model.repository;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
