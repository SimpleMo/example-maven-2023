package org.hse.example.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TICKET_ENTITY")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "TICKET_LENGTH")
    int length;

    @Column(name = "TICKET_NUMBER")
    int number;
}
