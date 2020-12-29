package scheduler.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.objects.Appointment;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author Brayden McArthur
 * @version 1.0
 * A class to handle date and time gets, comparisons, and gets.
 */
public class DateAndTime {

    //An alerts object to generate alert boxes.
    Alerts alerts = new Alerts();

    //A list to hold appointments so that different attributes can be extracted.
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public DateAndTime(){}

    //Formatters for datetime and time
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
    final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm:ss");

    //The current date and time
    final LocalDateTime current = LocalDateTime.now();

    /**
     * Gets the current date and time and formats it for the database
     * @return the current local date/time as a string
     */
    public String getCurrentDateAndTime(){
        return dateTimeFormatter.format(current);
    }

    /**
     * Converts a given date to UTC.
     * @param date A string representing a local date of pattern "yyyy-MM-dd kk:mm:ss" that will be converted to UTC.
     * @return Returns the corresponding UTC as a string.
     */
    public String getUtcFromLocal(String date){

        //Parse date string to LocalDateTime
        LocalDateTime providedDate = LocalDateTime.parse(date, dateTimeFormatter);
        //Convert LocalDateTime to ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.of(providedDate, ZoneId.systemDefault());
        //Convert to UTC
        ZonedDateTime utcDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);

        return dateTimeFormatter.format(utcDateTime);

    }

    /**
     * Converts a given date to Local time.
     * @param date A String representing a UTC date of pattern "yyyy-MM-dd kk:mm:ss that will be converted to Local.
     * @return Returns the corresponding Local date/time as a String.
     */
    public String getLocalFromUtc(String date){

        //Parse date string to LocalDateTime
        LocalDateTime providedDate = LocalDateTime.parse(date, dateTimeFormatter);
        //Convert LocalDateTime to ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.of(providedDate, ZoneId.of("UTC"));
        //Convert UTC time to system default time using an offset
        ZonedDateTime toSysDefault = zonedDateTime.withZoneSameInstant(ZoneOffset.systemDefault());
        //Convert back to a LocalDateTime
        LocalDateTime result = toSysDefault.toLocalDateTime();

        return dateTimeFormatter.format(result);

    }

    /**
     * Used for validating user input in the start/end fields. If it can successfully perform the action, the provided
     * date is valid. Else, it is an invalid date.
     * @param date provided by the user to be checked.
     * @return the date formatted to the dateTimerFormatter.
     */
    public String formatDateAndTime(String date){

        return dateTimeFormatter.parse(date).toString();

    }

    /**
     * Checks if a there is a meeting within 15 minutes of the user's login time so that an alert can be displayed.
     * @throws Exception Throws Exception if there is a problem in the Fetcher class.
     * @return Returns true if an appointment is scheduled within 15 minutes of login or false if there are no appointments
     * scheduled within 15 minutes of login.
     */
    public boolean checkMeetingWithinMinutes() throws Exception {

        Fetcher fetcher = new Fetcher();
        fetcher.fetchAppointments(appointments);

        //is false if there are no appointments within 15 minutes of signing in
        boolean upcoming = false;

        for(int i = 0; i < appointments.size(); i++){

            //Add 15 minutes to the current time and store in a variable.
            LocalDateTime localPlusFifteen = current.plusMinutes(15);

            //Converts string to LocalDateTime and formats it to the provided formatter.
            LocalDateTime date = LocalDateTime.parse(appointments.get(i).getStart(), dateTimeFormatter);

            //If the provided date is greater than or equal to the current date AND is less than or equal to the current
            //date plus 15 minutes, a meeting is about to start and an alert box is triggered.
            if((date.isEqual(current) || date.isAfter(current)) && (date.isEqual(localPlusFifteen) || (date.isBefore(localPlusFifteen)))){

                upcoming = true;
                alerts.alertBoxInformation("An appointment is about to start!\nID: " + appointments.get(i).getAppointmentId()
                        +"\nDate/Time: " + appointments.get(i).getStart());

            }
        }

        //If no meetings are about to start an alert box indicates no upcoming appointments and return false.
        if(!upcoming){
            alerts.alertBoxInformation("There are no upcoming appointments");
            return false;
        }

        return true;
    }

    /**
     * Checks if the appointment that the user is adding/modifying is overlapping with any existing appointments in the
     * database.
     * @param appointmentId A valid Appointment_ID from the database.
     * @param start A start date associated with an Appointment object provided as a string.
     * @param end An end date associated with an Appointment object provided as a string.
     * @return Returns true if the provided start and end times are overlapping with another Appointment.
     * @throws Exception Throws Exception if there is a problem in the Fetcher class.
     */
    public boolean isOverlapping(int appointmentId, String start, String end) throws Exception {

        //A Fetcher object for getting the appointments from the database. I discovered an error where the Fetcher and
        //DateAndTime class call each other back and forth in a never-ending loop if I declare them outside the methods
        //ie as global variables.
        Fetcher fetcher = new Fetcher();
        //A list of appointments that have been fetched from the database. Used for extracting start/end times
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        fetcher.fetchAppointments(appointments);

        //Convert the provided start/end strings to LocalDateTime variables
        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);

        //Loop through the entire appointments list and set each start/end time to a LocalDateTime variable to be
        //compared.
        for(int i = 0; i < appointments.size(); i++){

            //If the appointment ID matches an existing ID in the database, continue to the next iteration.
            if(appointments.get(i).getAppointmentId() == appointmentId){
                continue;
            }

            //Create LocalDateTimes from start/end strings from the current appointment.
            LocalDateTime appointmentStart = LocalDateTime.parse(appointments.get(i).getStart(), dateTimeFormatter);
            LocalDateTime appointmentEnd = LocalDateTime.parse(appointments.get(i).getEnd(), dateTimeFormatter);

            //If the start time is greater than or equal to an existing appointment start time AND the end time is less
            //than that appointment's end time, the appointments overlap and return true.
            if(startTime.equals(appointmentStart) || (startTime.isAfter(appointmentStart) && startTime.isBefore(appointmentEnd)) ) {
                return true;
            }

            //If the end time is equal to an existing appointment end OR the end time is after an existing appointment
            //start time AND before the existing appointment end time, they overlap and return true.
            if(endTime.equals(appointmentEnd) || (endTime.isAfter(appointmentStart) && endTime.isBefore(appointmentEnd))){
                return true;
            }
        }

        //If none of the conditions apply, the appointments do not overlap and return false.
        return false;


    }

    /**
     * Checks if a provided start date/time occurs before a provided end date/time.
     * @param start A start date/time provided as a string
     * @param end An end date/time provided as a string
     * @return Returns a boolean value. True if the start time occurs before the end time or false if not.
     */
    public boolean isBefore(String start, String end) {

        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);

        return endTime.isBefore(startTime);
    }

    /**
     * Checks if the provided date's time is outside of the EST open/close hours.
     * @param date A UTC date/time provided as a string, which will be converted to EST for direct comparison.
     * @return Returns a boolean value. True if the provided date falls within the EST schedule or false if not.
     */
    public boolean compareEasternTime(String date){

        //Need to set the providedDate and utcOpen to standardize
        LocalTime providedDateToUtc = LocalDateTime.parse(getUtcFromLocal(date), dateTimeFormatter).toLocalTime();

        //Switches the utc version to eastern time for a direct comparison
        LocalTime providedTime = LocalTime.parse(providedDateToUtc.format(timeFormatter)).minusHours(5);

        LocalTime easternToUtcOpen = LocalTime.of(8, 0, 0, 0);
        LocalTime easternToUtcClose = LocalTime.of(22, 0, 0, 0);

        if((providedTime.isAfter(easternToUtcOpen) || (providedTime.equals(easternToUtcOpen))) && (providedTime.isBefore(easternToUtcClose) ||
                providedTime.equals(easternToUtcClose))){
            return true;
        }else{
            return false;
        }

    }

    /**
     * Checks if a provided date/time occurs within the next week.
     * @param date A date/time provided as a string.
     * @return Returns false if the provided date occurs before or after the next week or true if not.
     */
    public boolean compareWeek(String date){

        LocalDateTime providedDate = LocalDateTime.parse(date, dateTimeFormatter);
        LocalDateTime currentPlusOneWeek = current.plusWeeks(1);

        if(providedDate.isBefore(current)){
            return false;
        }if(providedDate.isAfter(currentPlusOneWeek)){
            return false;
        }

        return true;

    }

    /**
     * Checks if a provided date/time occurs within the next month.
     * @param date A date/time provided as a string.
     * @return Returns false if the provided date occurs before or after the next month or true if not.
     */
    public boolean compareMonth(String date){

        LocalDateTime providedDate = LocalDateTime.parse(date, dateTimeFormatter);
        LocalDateTime currentPlusOneMonth = current.plusMonths(1);

        if(providedDate.isBefore(current)){
            return false;
        }if(providedDate.isAfter(currentPlusOneMonth)){
            return false;
        }

        return true;

    }


    /**
     * Calculates the hours between a provided start and end date/time.
     * @param start A start date/time provided as a string.
     * @param end An end date/time provided as a string.
     * @return Returns a double value representing the hours between the provided dates.
     */
    public double hoursScheduled(String start, String end){

        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);

        Duration duration = Duration.between(startTime, endTime);
        double toDouble = duration.toSeconds();

        return toDouble/3600;
    }



}
