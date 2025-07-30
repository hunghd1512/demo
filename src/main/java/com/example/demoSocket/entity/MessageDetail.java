package com.example.demoSocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "message-detail")
public class MessageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String to;
    private String from;
    private String content;
    private Long  idMessInfo;
    private String date;

    public MessageDetail(String to, String from, String date) {
        this.to = to;
        this.from = from;
        this.date = date;
    }
}
