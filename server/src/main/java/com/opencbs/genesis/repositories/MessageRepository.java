package com.opencbs.genesis.repositories;


import com.opencbs.genesis.domain.EmailMessage;

public interface MessageRepository extends Repository<EmailMessage> {
    void deleteByEventIdAndIsSentFalse(Long id);
}
