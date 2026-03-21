package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable{

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> schedule = timetable.get(trainingSession.getDayOfWeek());
        ArrayList<TrainingSession> trainingSessions;
        if (schedule == null){
            schedule = new TreeMap<>();
            timetable.put(trainingSession.getDayOfWeek(), schedule);
        }
        trainingSessions = schedule.get(trainingSession.getTimeOfDay());
        if (trainingSessions == null){
            trainingSessions = new ArrayList<>();
            schedule.put(trainingSession.getTimeOfDay(), trainingSessions);
        }
        trainingSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.get(dayOfWeek) != null){
            return timetable.get(dayOfWeek).get(timeOfDay);
        }
        return null;
    }



    public List<CounterOfTrainings> getCountByCoaches(){
        HashMap<Coach, Integer> trainingByCoaches = new HashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> trainingDay : timetable.entrySet()){
            for (ArrayList<TrainingSession> sessionList : trainingDay.getValue().values()){
                for (TrainingSession trainingSession : sessionList){
                    trainingByCoaches.put(trainingSession.getCoach(), trainingByCoaches.getOrDefault(trainingSession.getCoach(), 0) + 1);
                }
            }
        }
        List<CounterOfTrainings> countByCoaches = new ArrayList<>();
        for (Map.Entry<Coach, Integer> trainingByCoach : trainingByCoaches.entrySet() ){
            countByCoaches.add(new CounterOfTrainings(trainingByCoach.getKey(), trainingByCoach.getValue()));
        }

        Comparator<CounterOfTrainings> comparator = new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings o1, CounterOfTrainings o2) {
                return o2.getTrainings() - o1.getTrainings();
            }
        };
        Collections.sort(countByCoaches, comparator);
        return countByCoaches;
    }
}
