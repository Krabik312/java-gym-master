
package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).firstEntry().getValue().size());
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
                .firstEntry().getValue().size(), 1);


        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY)
                .firstEntry().getValue().get(0).getTimeOfDay(), new TimeOfDay(13, 0));
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY)
                .lastEntry().getValue().get(0).getTimeOfDay(), new TimeOfDay(20, 0));


        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);


        Assertions.assertEquals(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size(), 1);
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)));
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testAddMultipleSessionsSameDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession oneTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 70);
        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession twoTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(oneTrainingSession);
        timetable.addNewTrainingSession(twoTrainingSession);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
                .get(new TimeOfDay(13, 0)).size());

        //Проверить что при добавлении 2х тренировок в одно время длина списка будет равна 2
    }


    @Test
    void testSessionsOrderedByTimeWhenAddedUnsorted() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession oneTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));

        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 70);
        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession twoTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));


        Group group3 = new Group("Акробатика для взрослых", Age.ADULT, 100);
        Coach coach3 = new Coach("Петров", "Николай", "Сергеевич");
        TrainingSession threeTrainingSession = new TrainingSession(group3, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(17, 0));

        timetable.addNewTrainingSession(oneTrainingSession);
        timetable.addNewTrainingSession(twoTrainingSession);
        timetable.addNewTrainingSession(threeTrainingSession);

        Assertions.assertEquals(new TimeOfDay(15, 0), timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).navigableKeySet().getFirst());
        Assertions.assertEquals(new TimeOfDay(20, 0), timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).navigableKeySet().getLast());
        //P.s. я так и не понял как красиво пройтись по всем 3м элементам мапы, кроме как через for чтобы выполнить этот тест,
        //но логика такая что если 15 самый первый, а 20 последний то 17 логично по середине


        //проверить что 3 добавленных тренировки в неправельно порядке автоматически отсортируются в мапе
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeWithMultipleSessions() {

        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession oneTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 70);
        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession twoTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(oneTrainingSession);
        timetable.addNewTrainingSession(twoTrainingSession);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0)).size());

        //Проверить что метод возвращения тренировок по дню и времени возвращает все тренировки если их там несколько
    }

    @Test
    void testCountByCoachesSingleSession() {

        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession oneTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(oneTrainingSession);

        Assertions.assertEquals(1, timetable.getCountByCoaches().get(0).getTrainings());

        //Должно вывести что тренер провел 1 тренировку

    }

    @Test
    void testCountByCoachesMultipleSessionsDifferentTimes() {

        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession oneTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 70);
        TrainingSession twoTrainingSession = new TrainingSession(group2, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(17, 0));

        Coach coach2 = new Coach("Максименко", "Степан", "Андреевич");
        TrainingSession threeTrainingSession = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(oneTrainingSession);
        timetable.addNewTrainingSession(twoTrainingSession);
        timetable.addNewTrainingSession(threeTrainingSession);

        Assertions.assertEquals(2, timetable.getCountByCoaches().get(0).getTrainings());
        Assertions.assertEquals(1, timetable.getCountByCoaches().get(1).getTrainings());

        // Должно вывести под первым индексом тренера с 2мя тренировками, под последним тренера с 1 тренировкой
    }

    @Test
    void testCountByCoachesMultipleSessionsSameTime() {

        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession oneTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(17, 0));

        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 70);
        TrainingSession twoTrainingSession = new TrainingSession(group2, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(17, 0));

        Coach coach2 = new Coach("Максименко", "Степан", "Андреевич");
        TrainingSession threeTrainingSession = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(oneTrainingSession);
        timetable.addNewTrainingSession(twoTrainingSession);
        timetable.addNewTrainingSession(threeTrainingSession);

        Assertions.assertEquals(2, timetable.getCountByCoaches().get(0).getTrainings());
        Assertions.assertEquals(1, timetable.getCountByCoaches().get(1).getTrainings());

        // У тренеров тренировка в одно время, результат должен быть 2, 1
    }
}
