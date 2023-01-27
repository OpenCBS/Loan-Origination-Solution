package com.opencbs.repositories;

import com.opencbs.domain.BaseEntity;
import com.opencbs.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface Repository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
