import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This quick and dirty version uses elementary loops and algorithms to calculate approximate numbers.
 * most of these calculations are done via an approximation and not by strickly comparing dates.
 * likewise, this verison only supports holidays that are on fixed days and do not move around year by year.
 * this version also does not support leap years
 */
public class QnD implements SchedulingInt{

    ArrayList<Event> inputs;

    @Override
    //reads in the CSV file as lines of text. puts contents into an arraylist that can be used for further functions
    public void input(String inFile) {

        inputs = new ArrayList<Event>();

        //reads in the file
        //assumes correct format
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(inFile));
            String row;
            while ((row = csvReader.readLine()) != null) {

                //Check for # symbol as to avoid comment lines
                if (!row.contains("#")) {
                    String[] data = row.split(", ");
                    Event meeting = new Event();

                    //breaks up the string into formats and enters them into data structure class
                    meeting.start = data[0];
                    meeting.end = data[1];
                    meeting.day = data[2];
                    meeting.process();

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
    //rough estimate that guesses the number of times that a weekday occours during a time frame based on the number of days in that segment
    // assumes that the weekday does not change and that it happens once a week and does not stop for holidays but may not happen in a gap of <7 days.
    // leap year edge case is not covered
    //index is the element in the arrayList you want to count occurrences for
    public int countOccur(int index) {

        int numDays = 0;

        int sM = inputs.get(index).startMonth;
        int sd = inputs.get(index).startDay;
        int sy = inputs.get(index).startYear;
        int eM = inputs.get(index).endMonth;
        int ed = inputs.get(index).endDay;
        int ey = inputs.get(index).endYear;

        //set of incorrect dates return -1
        if (ey < sy) {
            return -1;
        } else if (ey == sy && sM > eM){
            return -1;
        } else if (ey == sy && sM == eM && sd>ed){
            return -1;
        }

        //goes through all months between given dates and adds the number of days between them
        for (int i = sM+1;i<eM;i++){
            if (i == 1 || i ==3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12){
                numDays += 31;
            } else if (i == 4 || i == 6 || i == 9 || i == 11){
                numDays += 30;
            }else{
                numDays += 28;
            }
        }

        //add number of days left in starting month if they are in differnt months
        if (sM!=eM || sy!=ey) {
            if (sM == 1 || sM == 3 || sM == 5 || sM == 7 || sM == 8 || sM == 10 || sM == 12) {
                numDays += (31 - sd);
            } else if (sM == 4 || sM == 6 || sM == 9 || sM == 11) {
                numDays += (30 - sd);
            } else {
                numDays += (28 - sd);
            }
            //adds the number of days in the last month
            numDays+=ed;
        }else{
            numDays = ed-sd;
        }


        return numDays/7;
    }

    @Override
    //walks through starting at startDate until end date week by week checking to see if it lands on any holidays
    //ASSUMES you start on the correct day of the week
    public int holidayCount(int index) {

        int hc = 0;

        int sM = inputs.get(index).startMonth;
        int sd = inputs.get(index).startDay;
        int sy = inputs.get(index).startYear;
        int eM = inputs.get(index).endMonth;
        int ed = inputs.get(index).endDay;
        int ey = inputs.get(index).endYear;

        //set of incorrect dates return -1
        if (ey < sy) {
            return -1;
        } else if (ey == sy && sM > eM){
            return -1;
        } else if (ey == sy && sM == eM && sd>ed){
            return -1;
        }


        int currentDay = sd;
        int currentMonth = sM;
        int currentYear = sy;

        while(currentYear<=ey){ //goes through every year

            while(currentMonth<=12){ //goes through all months

                if (currentMonth>eM && currentYear==ey){ //went too far in a year
                    break;
                }

                if (currentMonth == 1 || currentMonth ==3 || currentMonth == 5 || currentMonth == 7 || currentMonth == 8 || currentMonth == 10 || currentMonth == 12){

                    while(currentDay<=31){

                        if (currentDay>ed && currentMonth>=eM && currentYear>=ey){  //checks for valid days
                            break;
                        }

                        if(currentDay == 4 && currentMonth == 7){ //4th of july
                            System.out.println("July 4th");
                            hc++;
                        }
                        if(currentDay == 25 && currentMonth == 12){ //christmas
                            System.out.println("Christmas");
                            hc++;
                        }
                        if(currentDay == 1 && currentMonth == 1){ //new years
                            System.out.println("New Years");
                            hc++;
                        }
                        if(currentDay == 27 && currentMonth == 5){ //memorial day
                            System.out.println("Memorial day");
                            hc++;
                        }

                        currentDay+=7;
                    }

                    currentDay = currentDay-31;

                } else if (currentMonth == 4 || currentMonth == 6 || currentMonth == 9 || currentMonth == 11){

                    while(currentDay<=30){

                        if (currentDay>ed && currentMonth>=eM && currentYear>=ey){  //checks for valid days
                            break;
                        }

                        if(currentDay == 4 && currentMonth == 7){ //4th of july
                            hc++;
                        }
                        if(currentDay == 25 && currentMonth == 12){ //christmas
                            hc++;
                        }
                        if(currentDay == 1 && currentMonth == 1){ //new years
                            hc++;
                        }
                        if(currentDay == 27 && currentMonth == 5){ //memorial day
                            hc++;
                        }

                        currentDay+=7;
                    }

                    currentDay = currentDay-30;
                }else{
                    while(currentDay<=28){

                        if (currentDay>ed && currentMonth>=eM && currentYear>=ey){  //checks for valid days
                            break;
                        }

                        if(currentDay == 4 && currentMonth == 7){ //4th of july
                            hc++;
                        }
                        if(currentDay == 25 && currentMonth == 12){ //christmas
                            hc++;
                        }
                        if(currentDay == 1 && currentMonth == 1){ //new years
                            hc++;
                        }
                        if(currentDay == 27 && currentMonth == 5){ //memorial day
                            hc++;
                        }

                        currentDay+=7;
                    }

                    currentDay = currentDay-28;
                }


                currentMonth++;
            }

            currentYear++;
            currentMonth=1;
        }

        return hc;
    }


    public void printEvents(){
        System.out.println("start of QnD events");
        for (int i=0;i<inputs.size();i++){
            System.out.println(inputs.get(i));
        }
        System.out.println("end of Qnd Events");
    }
}
