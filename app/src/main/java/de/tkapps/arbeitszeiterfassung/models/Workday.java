package de.tkapps.arbeitszeiterfassung.models;

public class Workday {

    private String dateTimeStart;
    private String dateTimeEnd;
    private String workTimeBrutto;
    private String workTimeNetto;
    private String pauseTime;
    private String hash;


    public Workday() {
    }

    public Workday(String dateTimeStart, String dateTimeEnd, String workTimeBrutto, String workTimeNetto, String pauseTime, String hash) {
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.workTimeBrutto = workTimeBrutto;
        this.workTimeNetto = workTimeNetto;
        this.pauseTime = pauseTime;
        this.hash = hash;
    }

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public String getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(String dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getWorkTimeBrutto() {
        return workTimeBrutto;
    }

    public void setWorkTimeBrutto(String workTimeBrutto) {
        this.workTimeBrutto = workTimeBrutto;
    }

    public String getWorkTimeNetto() {
        return workTimeNetto;
    }

    public void setWorkTimeNetto(String workTimeNetto) {
        this.workTimeNetto = workTimeNetto;
    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Workday{" +
                "dateTimeStart='" + dateTimeStart + '\'' +
                ", dateTimeEnd='" + dateTimeEnd + '\'' +
                ", workTimeBrutto='" + workTimeBrutto + '\'' +
                ", workTimeNetto='" + workTimeNetto + '\'' +
                ", pauseTime='" + pauseTime + '\'' +
                '}';
    }
}