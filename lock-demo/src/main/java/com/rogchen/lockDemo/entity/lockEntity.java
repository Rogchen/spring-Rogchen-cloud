package com.rogchen.lockDemo.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 2018/9/30 09:29
 **/

@Data
@Table(name = "lock_entity")
public class lockEntity {
    @Id
    private Integer id;
}
