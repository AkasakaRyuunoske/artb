package com.example.demo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "anaata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anaata {
    @PrimaryKey
    String anaata;

    String message;
    String name;
    int probability;
}
