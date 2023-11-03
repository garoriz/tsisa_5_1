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
                addOrdersByMonth(orders, order, "2022-04");
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

        List<String> ordersIn3Months = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\IntelliJ IDEA projects\\SimpleProject\\src\\main\\java\\org\\example\\order.csv"))) {
            String order;
            while ((order = br.readLine()) != null) {
                addOrdersByMonth(ordersIn3Months, order, "2022-03");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ordersIn3Months.addAll(orders);

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\IntelliJ IDEA projects\\SimpleProject\\src\\main\\java\\org\\example\\order.csv"))) {
            String order;
            while ((order = br.readLine()) != null) {
                addOrdersByMonth(ordersIn3Months, order, "2022-05");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        key = ordersIn3Months.get(0).substring(0, 10);
        int index = 1;
        while (!key.equals("2022-03-07")) {
            key = ordersIn3Months.get(index).substring(0, 10);
            index++;
        }

        List<Integer> countsOfOrdersInMondays = new ArrayList<>();
        countOfOrders = 1;
        int indexOfWeek = 1;

        for (int i = index + 1; i < ordersIn3Months.size(); i++) {
            if (!ordersIn3Months.get(i).substring(0, 10).equals(key)) {
                addOrdersIfMonday(countsOfOrdersInMondays, indexOfWeek, countOfOrders);
                key = ordersIn3Months.get(i).substring(0, 10);

                countOfOrders = 1;
                indexOfWeek = getIndexOfWeek(indexOfWeek);
            } else {
                countOfOrders++;
            }
        }
        addOrdersIfMonday(countsOfOrdersInMondays, indexOfWeek, countOfOrders);

        double averageCountOfOrdersInMondays = 0.0;
        double dispersionOfOrdersInMondays = 0.0;

        for (Integer countOfOrderInMondays :
                countsOfOrdersInMondays) {
            averageCountOfOrdersInMondays += countOfOrderInMondays;
        }

        averageCountOfOrdersInMondays /= countsOfOrdersInMondays.size();

        for (Integer countOfOrderInMondays :
                countsOfOrdersInMondays) {
            dispersionOfOrdersInMondays += Math.pow(countOfOrderInMondays -
                    averageCountOfOrdersInMondays, 2);
        }
        dispersionOfOrdersInMondays /= countsOfOrdersInMondays.size();

        key = ordersIn3Months.get(0).substring(0, 10);
        index = 1;
        while (!key.equals("2022-03-06")) {
            key = ordersIn3Months.get(index).substring(0, 10);
            index++;
        }

        List<Integer> countsOfOrdersInSundays = new ArrayList<>();
        countOfOrders = 1;
        indexOfWeek = 7;

        for (int i = index + 1; i < ordersIn3Months.size(); i++) {
            if (!ordersIn3Months.get(i).substring(0, 10).equals(key)) {
                addOrdersIfSunday(countsOfOrdersInSundays, indexOfWeek, countOfOrders);
                key = ordersIn3Months.get(i).substring(0, 10);

                countOfOrders = 1;
                indexOfWeek = getIndexOfWeek(indexOfWeek);
            } else {
                countOfOrders++;
            }
        }
        addOrdersIfSunday(countsOfOrdersInSundays, indexOfWeek, countOfOrders);

        double averageCountOfOrdersInSundays = 0.0;
        double dispersionOfOrdersInSundays = 0.0;

        for (Integer countOfOrderInSundays :
                countsOfOrdersInSundays) {
            averageCountOfOrdersInSundays += countOfOrderInSundays;
        }

        averageCountOfOrdersInSundays /= countsOfOrdersInSundays.size();

        for (Integer countOfOrderInSundays :
                countsOfOrdersInSundays) {
            dispersionOfOrdersInSundays += Math.pow(countOfOrderInSundays -
                    averageCountOfOrdersInSundays, 2);
        }
        dispersionOfOrdersInSundays /= countsOfOrdersInSundays.size();

        List<Integer> countsOfOrdersInWeeks = new ArrayList<>();
        countOfOrders = 1;
        indexOfWeek = 2;
        key = ordersIn3Months.get(0).substring(0, 10);

        for (int i = 1; i < ordersIn3Months.size(); i++) {
            if (!ordersIn3Months.get(i).substring(0, 10).equals(key)) {
                key = ordersIn3Months.get(i).substring(0, 10);
                countOfOrders = addOrdersByWeekAndGetCountOfOrders(countsOfOrdersInWeeks, countOfOrders, indexOfWeek);
                indexOfWeek = getIndexOfWeek(indexOfWeek);
            } else {
                countOfOrders++;
            }
        }
        countsOfOrdersInWeeks.add(countOfOrders);

        double averageCountOfOrdersInWeeks = 0.0;
        double dispersionOfOrdersInWeeks = 0.0;

        for (Integer countOfOrderInWeeks :
                countsOfOrdersInWeeks) {
            averageCountOfOrdersInWeeks += countOfOrderInWeeks;
        }

        averageCountOfOrdersInWeeks /= countsOfOrdersInWeeks.size();

        for (Integer countOfOrderInWeeks :
                countsOfOrdersInWeeks) {
            dispersionOfOrdersInWeeks += Math.pow(countOfOrderInWeeks -
                    averageCountOfOrdersInWeeks, 2);
        }
        dispersionOfOrdersInWeeks /= countsOfOrdersInWeeks.size();
    }

    private static int addOrdersByWeekAndGetCountOfOrders(List<Integer> countsOfOrdersInWeeks, int countOfOrders, int indexOfWeek) {
        if (indexOfWeek == 7) {
            countsOfOrdersInWeeks.add(countOfOrders);
            return 1;
        }
        return countOfOrders;
    }

    private static void addOrdersIfSunday(List<Integer> countsOfOrdersInSundays, int indexOfWeek, int countOfOrders) {
        if (indexOfWeek == 7) {
            countsOfOrdersInSundays.add(countOfOrders);
        }
    }

    private static void addOrdersIfMonday(List<Integer> countsOfOrdersInMondays, int indexOfWeek, int countOfOrders) {
        if (indexOfWeek == 1) {
            countsOfOrdersInMondays.add(countOfOrders);
        }
    }

    private static int getIndexOfWeek(int indexOfWeek) {
        if (indexOfWeek == 7) {
            return 1;
        }
        indexOfWeek++;
        return indexOfWeek;
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

    private static void addOrdersByMonth(List<String> records, String line, String month) {
        if (line.contains(month)) {
            records.add(line);
        }
    }

}
