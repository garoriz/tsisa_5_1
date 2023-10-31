package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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

        double sum = 0;
        for (int i = 1; i <= countsOfOrdersInHour.size(); i++) {
            sum += countsOfOrdersInHour.get(i - 1);
            if (i % 72 == 0) {
                averageCountsOfOrders.add(sum / 72);
                sum = 0;
            }
        }
        averageCountsOfOrders.add(sum / 72);

        int averageCountsOfOrdersIndex = 0;
        sum = 0;
        for (int i = 1; i <= countsOfOrdersInHour.size(); i++) {
            sum += Math.sqrt(Math.abs(countsOfOrdersInHour.get(i - 1) -
                    averageCountsOfOrders.get(averageCountsOfOrdersIndex)));

            if (i % 72 == 0) {
                sum /= 72;
                dispersionsOfOrders.add(sum);
                averageCountsOfOrdersIndex++;
                sum = 0;
            }
        }
        sum /= 72;
        dispersionsOfOrders.add(sum);

        for (Double d:
             dispersionsOfOrders) {
            System.out.println(d);
        }
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
