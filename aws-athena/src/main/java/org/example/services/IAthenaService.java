package org.example.services;

import org.example.models.Transactions;

import java.util.List;

public interface IAthenaService {
    List<Transactions> getDataFromAthena(String myQuery);
}
