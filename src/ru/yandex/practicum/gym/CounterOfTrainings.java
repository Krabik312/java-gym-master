package ru.yandex.practicum.gym;


public class CounterOfTrainings {

    private Coach coach;
    private int trainings;

    CounterOfTrainings(Coach coach, int trainings){
        this.coach = coach;
        this.trainings = trainings;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainings() {
        return trainings;
    }
}
