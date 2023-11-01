package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int DAYS_IN_APRIL = 30;
        List<String> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\IntelliJ IDEA projects\\SimpleProject\\src\\main\\java\\org\\example\\order.csv"))) {
            String order;
            while ((order = br.readLine()) != null) {
                addApril2022Orders(orders, order);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Integer> countsOfOrdersInHour = new ArrayList<>(72 * 10);
        List<Double> averageCountsOfOrders = new ArrayList<>();
        List<Double> dispersionsOfOrders = new ArrayList<>();

        int countOfOrders = 1;
        String key = orders.get(0).substring(0, 13);
        for (int i = 1; i < orders.size(); i++) {
            if (!orders.get(i).substring(0, 13).equals(key)) {
                countsOfOrdersInHour.add(countOfOrders);
                key = orders.get(i).substring(0, 13);

                countOfOrders = 1;
            } else {
                countOfOrders++;
            }
        }
        countsOfOrdersInHour.add(countOfOrders);

        double sum = 0;
        for (int i = 1; i <= countsOfOrdersInHour.size(); i++) {
            sum += countsOfOrdersInHour.get(i - 1);
            if (i % 72 == 0) {
                averageCountsOfOrders.add(sum / 72);
                sum = 0;
            }
        }

        int averageCountsOfOrdersIndex = 0;
        sum = 0;
        for (int i = 1; i <= countsOfOrdersInHour.size(); i++) {
            sum += Math.pow(countsOfOrdersInHour.get(i - 1) -
                    averageCountsOfOrders.get(averageCountsOfOrdersIndex), 2);

            if (i % 72 == 0) {
                sum /= 72;
                dispersionsOfOrders.add(sum);
                averageCountsOfOrdersIndex++;
                sum = 0;
            }
        }

        countOfOrders = 0;
        key = orders.get(0).substring(0, 10);
        List<Integer> countsOfOrdersInDay = new ArrayList<>(DAYS_IN_APRIL);
        for (int i = 0; i < orders.size(); i++) {
            if (!orders.get(i).substring(0, 10).equals(key)) {
                countsOfOrdersInDay.add(countOfOrders);
                key = orders.get(i).substring(0, 10);

                countOfOrders = 1;
            } else {
                countOfOrders++;
            }
        }
        countsOfOrdersInDay.add(countOfOrders);

        double averageCountOfOrdersInDay = 0.0;
        double dispersionOfOrdersInDay = 0.0;

        for (Integer countOfOrderInDay :
                countsOfOrdersInDay) {
            averageCountOfOrdersInDay += countOfOrderInDay;
        }

        averageCountOfOrdersInDay /= 30;

        for (Integer countOfOrderInDay :
                countsOfOrdersInDay) {
            dispersionOfOrdersInDay += Math.pow(countOfOrderInDay -
                    averageCountOfOrdersInDay, 2);
        }
        dispersionOfOrdersInDay /= DAYS_IN_APRIL;
        System.out.println(dispersionOfOrdersInDay);
    }

    private static void addToAverageCountsAndDispersionsOfOrders(
            int numberOfHour,
            List<Double> averageCountsOfOrders,
            List<Double> dispersionsOfOrders,
            int countOfOrders) {
        if (numberOfHour % 72 == 0) {
            averageCountsOfOrders.add(((double) countOfOrders) / 72);
            dispersionsOfOrders.add(((double) countOfOrders) / 72);
        }
    }

    private static void addApril2022Orders(List<String> records, String line) {
        if (line.contains("2022-04")) {
            records.add(line);
        }
    }

}
