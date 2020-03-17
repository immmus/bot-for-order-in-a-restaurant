package com.immmus.telegram.bot.repository;

import com.immmus.infrastructure.api.repository.MenuPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuPositionRepository extends JpaRepository<MenuPosition, Long> { }
