package org.ab.userkafka.crm.repository;

import org.ab.userkafka.crm.domain.CrmUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrmUserRepository extends JpaRepository<CrmUser, Long> {

}
