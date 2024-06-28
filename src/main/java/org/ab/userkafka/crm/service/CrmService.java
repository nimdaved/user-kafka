package org.ab.userkafka.crm.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ab.userkafka.common.domain.ChangeOriginator;
import org.ab.userkafka.crm.publisher.CrmEventPublisher;
import org.ab.userkafka.crm.domain.CrmUser;
import org.ab.userkafka.crm.repository.CrmUserRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrmService {
    private final CrmUserRepository crmUserRepository;
    private final CrmEventPublisher crmEventPublisher;

    public CrmService(CrmUserRepository crmUserRepository, CrmEventPublisher crmEventPublisher) {
        this.crmUserRepository = crmUserRepository;
        this.crmEventPublisher = crmEventPublisher;
    }


    @Transactional
    public CrmUser changeUser(CrmUser user, ChangeOriginator source) {

boolean created = user.isNewUser() || !crmUserRepository
        .findById(user.getId()).isPresent();
            CrmUser savedUser = crmUserRepository.save(user);
            if (created) {
                crmEventPublisher.userCreated(savedUser, source);
            } else {
                crmEventPublisher.userUpdated(savedUser, source);
            }
            return savedUser;




    }

    public CrmUser findById(Long id) {
        return crmUserRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Could not find user with id: " + id));
    }
}
