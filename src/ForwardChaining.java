import java.util.*;
import java.io.*;



class FC {
	// create variables
	public static int counter = 0;
    public static String tell; // Rules + Base of Facts
    public static String ask; // Goal
    public static ArrayList < String > agenda; // Base of Facts
    public static ArrayList < String > clauses; // Rules
    public static ArrayList < Integer > count; //Number if primeses in a rule
    public static ArrayList < String > entailed;

    public FC(String a, String t) {
        // initialize variables
        agenda = new ArrayList < String > ();
        clauses = new ArrayList < String > ();
        entailed = new ArrayList < String > ();
        count = new ArrayList < Integer > ();
        tell = t;
        ask = a;
        init(tell);
    }

    // method which calls the main fcentails() method and returns output back to iengine
    public String execute() {
        String output = "";
        if (fcentails()) {
            // the method returned true so it entails
            output = "YES: ";
            // for each entailed symbol
            for (int i = 0; i < entailed.size(); i++) {
                output += entailed.get(i) + ", ";
            }
			output += ask;
			output += "\n Counter: " + counter;
        } else {
			output = "NO";
			output += "\n Counter: " + counter;
        }
        return output;
    }

    // FC algorithm
    public boolean fcentails() {
        // loop through while there are unprocessed facts
        while (!agenda.isEmpty()) {
            // take the first item and process it
            String p = agenda.remove(0);
            // add to entailed
            entailed.add(p);
            // for each of the clauses....
            for (int i = 0; i < clauses.size(); i++) {
				// .... that contain p in its premise
				//counter++;
                if (premiseContains(clauses.get(i), p)) {
					// counter++;
                    Integer j = count.get(i);
                    // reduce count : unknown elements in each premise
                    count.set(i, --j);
                    // all the elements in the premise are now known
                    if (count.get(i) == 0) {
                        counter++;
                        // the conclusion has been proven so put into agenda
                        String head = clauses.get(i).split("=>")[1];
                        // have we just proven the 'ask'?
                        if (head.equals(ask))
                            return true;
						agenda.add(head);
						
                    }
                }
			}

        }
        // if we arrive here then ask cannot be entailed
        return false;
    }




    // method which sets up initial values for forward chaining
    // takes in string representing KB and seperates symbols and clauses, calculates count etc..
    public static void init(String tell) {
        String[] sentences = tell.split(";");
        for (int i = 0; i < sentences.length; i++) {

            if (!sentences[i].contains("=>"))
                // add facts to be processed
                agenda.add(sentences[i]);
            else {
                // add sentences
                clauses.add(sentences[i]);
                count.add(sentences[i].split("&").length);
            }
        }
    }


    // method which checks if p appears in the premise of a given clause	
    // input : clause, p
    // output : true if p is in the premise of clause
    public static boolean premiseContains(String clause, String p) {
		// counter++;
		String premise = clause.split("=>")[0];
        String[] conjuncts = premise.split("&");
        // check if p is in the premise
        if (conjuncts.length == 1)
            return premise.equals(p);
        else
            return Arrays.asList(conjuncts).contains(p);
    }
}


class ForwardChaining {
    public static void main(String[] args) {
//        FC fc = new FC(
//            "e",
//            "a&b=>c;c&d=>f;f&b=>e;f&a=>g;g&f=>b;a;c;d");
//     
//       
//        System.out.println(fc.execute());
//      
        GUI fw = new GUI();
        fw.setVisible(true);

       
    }
}