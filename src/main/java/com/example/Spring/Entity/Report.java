package com.example.Spring.Entity;

import java.io.Serializable;

public interface Report {
    Serializable getGroup();

    Double getSum();

    Long getCount();
}
