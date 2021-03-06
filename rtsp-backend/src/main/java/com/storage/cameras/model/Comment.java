package com.storage.cameras.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    public static final int MAX_COMMENT_LENGTH = 255;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "comment", length = MAX_COMMENT_LENGTH, nullable = false)
    private String comment;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate = new Date();

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "camera_id")
    private Camera camera;
}
