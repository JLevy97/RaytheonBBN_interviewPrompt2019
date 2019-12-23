/**
 * holds important information that could be used in an event.
 */
public class Event {

    //basic 3 elements of an event
    String start;
    String end;
    String day;

    //additional variables.
    int weekDay;
    int startYear;
    int endYear;
    int startMonth;
    int endMonth;
    int startDay;
    int endDay;

    //default constructor to make everything null/empty
    public Event(){
        start = null;
        end = null;
        day = null;
    }

    /**
     * Passes in the basic 3 elements and does the processing automatically.
     *
     * @param s
     * @param e
     * @param d
     */
    public Event(String s, String e, String d){
        start = s;
        end = e;
        day = d;
        process();
    }

    //given the basic elements, will initialize the additional variables that may be useful for functionalities
    void process(){

        String[] splits = start.split("-");
        startYear = Integer.parseInt(splits[0]);
        startMonth = Integer.parseInt(splits[1]);
        startDay = Integer.parseInt(splits[2]);

        splits = end.split("-");
        endYear = Integer.parseInt(splits[0]);
        endMonth = Integer.parseInt(splits[1]);
        endDay = Integer.parseInt(splits[2]);

        //sets the weekDay equal to a number
        if (day.toLowerCase().equals("sunday")){
            weekDay = 1;
        }else if (day.toLowerCase().equals("monday")){
            weekDay = 2;
        }else if (day.toLowerCase().equals("tuesday")){
            weekDay = 3;
        }else if (day.toLowerCase().equals("wednesday")){
            weekDay = 4;
        }else if (day.toLowerCase().equals("thursday")){
            weekDay = 5;
        }else if (day.toLowerCase().equals("friday")){
            weekDay = 6;
        }else if (day.toLowerCase().equals("saturday")){
            weekDay = 7;
        }else{
            weekDay = -1;
        }

    }

    @Override
    //allows events to be printed prettily
    public String toString() {
        return "Event{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", day='" + day + '\'' +
                '}';
    }

}
