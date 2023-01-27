package com.opencbs.repositories;

import com.opencbs.domain.Message;

import java.util.List;

public interface MessageRepository extends Repository<Message> {
    List<Message> findByIsSentFalse();
}
