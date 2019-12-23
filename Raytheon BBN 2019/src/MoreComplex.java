import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

//try to use java calandar or java date functions

/**
 * This version is more accurate than the quick and dirty version.
 * Due to the use of java dates and calandars, it uses epoch times to accurately compare and count dates.
 * because of calendar functions, it is also possible to add holidays that more around such as thanksgiving.
 * This epoch/calandar setup, would also make it easier to
 * This version also includes a sample method of how user input could be done outside of an input file for on the spot results.
 */
public class MoreComplex implements SchedulingInt{

    ArrayList<Event> inputs;

    @Override
    public void input(String inFile) {

        inputs = new ArrayList<Event>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(inFile));
            String row;
            while ((row = csvReader.readLine()) != null) {

                //Check for # symbol
                if (!row.contains("#")) {
                    String[] data = row.split(", ");
                    Event meeting = new Event(data[0],data[1],data[2]);

                    inputs.add(meeting);
                }
            }
            csvReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return;
    }

    @Override
    public int countOccur(int index) {

        int numOccour = 0;

        int dayToCount = inputs.get(index).weekDay;
        String strDate = inputs.get(index).start;
        String endDate = inputs.get(index).end;

        Long millis;
        Date startD = new Date();
        Date endD = new Date();

        //parses given dates into accurate Epoch time measures
        try {
            millis = new SimpleDateFormat("yyyy-MM-dd").parse(strDate).getTime();
            startD = new Date(millis);
            millis = new SimpleDateFormat("yyyy-MM-dd").parse(endDate).getTime();
            endD = new Date(millis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //checks to make sure dates are valid
        if(startD.after(endD)){
            System.out.println("invalid dates");
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(startD);

        //moves calandar until we reach the appriate weekday to count
        while(cal.get(Calendar.DAY_OF_WEEK)!=dayToCount){
            cal.add(Calendar.DATE,1);
        }


        //moves week by week until there dates are invalid, each day is on appropriate weekDay, so we can just count those
        while(cal.getTime().before(endD)){
            numOccour++;
            cal.add(Calendar.DATE,7);
        }

        return numOccour;
    }

    @Override
    public int holidayCount(int index) {

        int numHoli = 0;

        int dayToCount = inputs.get(index).weekDay;
        String strDate = inputs.get(index).start;
        String endDate = inputs.get(index).end;

        Long millis;
        Date startD = new Date();
        Date endD = new Date();

        //parses given dates into accurate Epoch time measures
        try {
            millis = new SimpleDateFormat("yyyy-MM-dd").parse(strDate).getTime();
            startD = new Date(millis);
            millis = new SimpleDateFormat("yyyy-MM-dd").parse(endDate).getTime();
            endD = new Date(millis);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //checks to make sure dates are valid
        if(startD.after(endD)){
            System.out.println("invalid dates");
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(startD);

        //moves calandar until we reach the appriate weekday to count
        while(cal.get(Calendar.DAY_OF_WEEK)!=dayToCount){
            cal.add(Calendar.DATE,1);
        }


        //moves week by week until there dates are invalid
        while(cal.getTime().before(endD)){

            //checks to see if the day is a holiday if so, add and print, number of holidays accounted for can always be added or subtracted,
            //can be optimized to other holiday only function

            if (cal.get(Calendar.DATE)==4 && cal.get(Calendar.MONTH)==6){
                numHoli++;
                System.out.println("event on 4th of July");
            }
            if (cal.get(Calendar.DATE)==25 && cal.get(Calendar.MONTH)==11){
                numHoli++;
                System.out.println("event on Christmas");
            }
            if (cal.get(Calendar.DATE)==1 && cal.get(Calendar.MONTH)==0){
                numHoli++;
                System.out.println("event on New Years");
            }
            if (cal.get(Calendar.WEEK_OF_MONTH)==4 && cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY){
                numHoli++;
                System.out.println("event on thanksgiving");
            }

            cal.add(Calendar.DATE,7);
        }

        return numHoli;
    }

    public void printEvents(){
        System.out.println("List of Soph events "+ inputs);
    }

    //promts user for an on the spot input
    public void interactableInput(){

        Scanner keyboard = new Scanner(System.in);

        System.out.println("please enter an event in the following format --> start date, end date, day of the week");
        System.out.println("yyyy-mm-dd, yyyy-mm-dd, Weekday");
        System.out.println("Example: 2018-05-02, 2018-12-31, Wednesday");

        String ev = keyboard.nextLine();
        try{
            String[] data = ev.split(", ");
            Event meeting = new Event(data[0],data[1],data[2]);

            inputs.add(meeting);
        }catch (Exception e){
            System.out.println("error with input formatting");
            e.printStackTrace();
        }


    }

}
