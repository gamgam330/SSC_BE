package com.sithub.sithub.Repository;

import com.sithub.sithub.domain.Manage;
import com.sithub.sithub.responseDTO.ManageListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManageRepository extends JpaRepository<Manage, Long> {

}
