package com.example.demo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnaataRepository extends CassandraRepository<Anaata, String> {

}