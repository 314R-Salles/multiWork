package com.psalles.multiWork.bugReport.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "image_table")
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "b64string", columnDefinition = "LONGTEXT")
    private String b64string;
    @Column(name = "dateTime")
    private LocalDateTime dateTime;
    @Column(name = "reported")
    private boolean reported;

    public Image() {
        super();
    }

    public Image(String name, String b64string, LocalDateTime dateTime, boolean reported) {
        this.name = name;
        this.b64string = b64string;
        this.dateTime = dateTime;
        this.reported = reported;
    }


}