package com.psalles.multiWork.bugReport.repository;

import com.psalles.multiWork.bugReport.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByReported(boolean reported);
}