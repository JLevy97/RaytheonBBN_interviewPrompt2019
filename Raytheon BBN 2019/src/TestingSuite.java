import java.sql.SQLOutput;

/**
 * Tests the two versions
 * .
 */
public class TestingSuite {

    public static void main(String[] args){

        System.out.println("~~~~~~~~~~Given tests for the Quick and Dirty version~~~~~~~~~~");
        System.out.println();
        QnDTesting();
        System.out.println("\n~~~~~~~~~~Given tests for the more sophisticated and accurate version~~~~~~~~~~");
        System.out.println();
        CompTesting();
        System.out.println("\n~~~~~~~~~~Demonstrating how the sophisticated version is more accurate~~~~~~~~~~");
        System.out.println();
        compare();
    }

    //uses the quick and dirty verisons
    static void QnDTesting(){

        QnD workExample = new QnD();

        workExample.input("input.csv");
        workExample.printEvents();

        System.out.println();
        for (int i=0;i<workExample.inputs.size();i++){
            System.out.println(workExample.inputs.get(i)+" would have about "+workExample.countOccur(i)+" weeks that could contain the requested weekday");
        }

        System.out.println();
        for (int i=0;i<workExample.inputs.size();i++){
            System.out.println(workExample.inputs.get(i)+" would have about "+workExample.holidayCount(i)+" weeks that could contain the requested weekday");
        }


    }

    //uses the more accurate versions
    static void CompTesting(){

        MoreComplex workExample = new MoreComplex();

        workExample.input("input.csv");
        workExample.printEvents();

        System.out.println();
        //how many days would day meet if on Wednesday from example and how many holidays.
        System.out.println(workExample.inputs.get(0)+" would have about "+workExample.countOccur(0)+" weeks that could contain the requested weekday");
        System.out.println(workExample.inputs.get(0)+" would have about "+workExample.holidayCount(0)+" holiday(s) that landing on the requested weekday");

        System.out.println();
        //what if we moved it to move it to thursday, how many holidays.
        System.out.println(workExample.inputs.get(1)+" would have about "+workExample.countOccur(1)+" weeks that could contain the requested weekday");
        System.out.println(workExample.inputs.get(1)+" would have about "+workExample.holidayCount(1)+" holiday(s) that landing on the requested weekday");


    }

    //compares how the two can produce different results and one is more accurate than the other.
    static void compare(){

        QnD qd = new QnD();
        qd.input("minitests.csv");

        MoreComplex mc = new MoreComplex();
        mc.input("minitests.csv");

        System.out.println("\nevents in both versions\n");
        qd.printEvents();
        mc.printEvents();

        System.out.println("\nresults for Quick and Dirty");
        for (int i=0;i<qd.inputs.size();i++){
            System.out.println(qd.inputs.get(i)+" would have about "+qd.countOccur(i)+" weeks that could contain the requested weekday");
        }

        System.out.println("\nresults for more sophisticated");
        for (int i=0;i<mc.inputs.size();i++){
            System.out.println(mc.inputs.get(i)+" would have about "+mc.countOccur(i)+" weeks that could contain the requested weekday");
        }


        System.out.println("\nshows off ability to add on the spot events in better implementation");
        mc.interactableInput();
        mc.printEvents();
    }
}
