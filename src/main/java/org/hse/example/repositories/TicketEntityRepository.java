package org.hse.example.repositories;

import org.hse.example.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TicketEntityRepository extends CrudRepository<TicketEntity, Long> {
    Collection<TicketEntity> findAllByLength(int length);
    boolean existsByLengthAndNumber(int length, int number);
}
