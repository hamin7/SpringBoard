package com.example.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity
//@Table("")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;
    @Column
    private String writer;
    @Column
    private String subject;
    @Column
    private String content;
    @Column
    private String filename;
    @Column
    private Integer readcnt;
    @Column
    private Date regdate;
}
