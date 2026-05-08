package com.nitin.expense.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class SummaryDTO {
    private Double totalAmount;
    private Map<String, Double> categoryWiseTotal;
    private Long totalExpenses;
    private Double averagePer30Day;
    private Map<String, Double> monthlyAvg;
}