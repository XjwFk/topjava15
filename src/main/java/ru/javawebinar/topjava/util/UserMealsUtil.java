package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //List<UserMealWithExceed> list = getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        //System.out.print("dd");
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = new HashMap<>();

        for (int i = 0; i < mealList.size(); i++) {
            LocalDate localDate = mealList.get(i).getDateTime().toLocalDate();
            Integer calories = mealList.get(i).getCalories();
            //map.merge(localDate, calories, (integer, integer2) -> integer2 = integer + calories);
            map.merge(localDate, calories, (integer, integer2) -> integer + integer2);
        }
        List<UserMealWithExceed> list = new ArrayList<>();
        for (int i = 0; i < mealList.size(); i++) {
            if (TimeUtil.isBetween(mealList.get(i).getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExceed(mealList.get(i).getDateTime(), mealList.get(i).getDescription(), mealList.get(i).getCalories(), caloriesPerDay < map.get(mealList.get(i).getDateTime().toLocalDate())));
            }
        }
        return list;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = mealList.stream()
                //.collect(Collectors.toMap(o -> o.getDateTime().toLocalDate(), o -> o.getCalories(), (o, o2) -> o + o2));
                .collect(Collectors.toMap(o -> o.getDateTime().toLocalDate(), UserMeal::getCalories, (o, o2) -> o + o2));

        return mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), caloriesPerDay < map.get(userMeal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }
}
