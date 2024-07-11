package ru.spbstu.sce.db.entity.Coin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SymbolsResponse {
    private List<String> symbols;
}