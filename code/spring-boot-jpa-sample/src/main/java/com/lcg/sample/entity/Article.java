package com.lcg.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Article implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title_")
    private String title;
    @Column(name = "content_")
    private String content;

    @CreatedDate
    @Column(name = "create_time")
    public Date createTime;

    @CreatedBy
    @Column(name = "create_by")
    public Long createBy;

    private transient boolean isNew;//默认为false

    @Override
    public boolean isNew() {
        return isNew;//新增时，设置该属性为true，更新时可以不设值，默认为false
    }
}
