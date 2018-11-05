package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:/spring/spring-app.xml",
        "classpath:/spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() throws Exception {
        assertMatch(mealService.get(MEAL_ID, USER_ID), MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        mealService.get(ADMIN_MEAL_ID, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(MEAL_ID, USER_ID);
        assertMatch(mealService.getAll(USER_ID), Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2));

    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        mealService.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception{
        List<Meal> list = mealService.getBetweenDateTimes(
                LocalDateTime.of(LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0)),
                LocalDateTime.of(LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0)),
                USER_ID);
        List<Meal> expected = Arrays.asList(MEAL4, MEAL3, MEAL2, MEAL1);
        assertMatch(list, expected);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(mealService.getAll(USER_ID), MEALS);
    }

    @Test
    public void update() throws Exception {
        Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак2", 520);
        mealService.update(meal, USER_ID);
        assertMatch(mealService.get(MEAL_ID,USER_ID), meal);
    }

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2016, Month.MAY, 29, 14, 0), "Завтрак2", 520);
        mealService.create(meal, USER_ID);
        assertMatch(mealService.get(100010, USER_ID), meal);
    }
}