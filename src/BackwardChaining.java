import java.util.*;
import java.io.*;


class BC {
    // create variables
    public static int counter = 0;
    public static String tell;
    public static String ask;
    public static ArrayList < String > agenda;  // Goal
    public static ArrayList < String > facts; // Base of Facts
    public static ArrayList < String > clauses; // Rules
    public static ArrayList < String > entailed;
    
    public BC(String a, String t) {
        // initialize variables
        agenda = new ArrayList < String > ();
        clauses = new ArrayList < String > ();
        entailed = new ArrayList < String > ();
        facts = new ArrayList < String > ();
        tell = t;
        ask = a;
        init(tell);
    }


    // method which sets up initial values for backward chaining
    public static void init(String tell) {
        agenda.add(ask);
        // split kb into sentences
        String[] sentences = tell.split(";");
        for (int i = 0; i < sentences.length; i++) {
            if (!sentences[i].contains("=>"))
                // add the facts
                facts.add(sentences[i]);
            else {
                // add the premises	
                clauses.add(sentences[i]);
            }
        }
    }

    // method which calls the main bcentails() method and returns output back to iengine
    public String execute() {
        String output = "";

        if (bcentails()) {
            // the method returned true so it entails
            output = "YES: ";
            // loop through all entailed symbols in reverse
            for (int i = entailed.size() - 1; i >= 0; i--) {
                if (i == 0)
                    output += entailed.get(i);
                else
                    // no comma at the end
                    output += entailed.get(i) + ", ";

            }
            output += "\n Counter: " + counter;
        }
        // no 
        else {
           
            output = "NO";
            output += "\n Counter: " + counter;
        }
        return output;
    }

    // method which runs the bc algorithm
    public boolean bcentails() {
        // while the list of symbols are not empty
        while (!agenda.isEmpty()) {
            // get current symbol
            String q = agenda.remove(agenda.size() - 1);
            // add the entailed array
            entailed.add(q);
      
          
            // if this element is a fact then we dont need to go further
            if (!facts.contains(q)) {
                counter++;
                // .. but it isnt so..
                // create array to hold new symbols to be processed 
                ArrayList < String > p = new ArrayList < String > ();
                for (int i = 0; i < clauses.size(); i++) {
                   // counter++;
                    // for each clause..
                    if (conclusionContains(clauses.get(i), q)) {
                        // that contains the symbol as its conclusion
                          //  counter++;
                        ArrayList < String > temp = getPremises(clauses.get(i));
                        for (int j = 0; j < temp.size(); j++) {
                            // add the symbols to a temp array
                            p.add(temp.get(j));
                        
                        }
                    }
                }
                // no symbols were generated and since it isnt a fact either 
                // then this sybmol and eventually ASK  cannot be implied by TELL
                if (p.size() == 0) {
                     counter=0;
                    return false;
                } else {
                    // there are symbols so check for previously processed ones and add to agenda
                    for (int i = 0; i < p.size(); i++) {
                        if (!entailed.contains(p.get(i)))
                            agenda.add(p.get(i));
                        
                              
                       
                    }


                }
            }
           
            

        } //while end
        return true;
    }

    // methid that returns the conjuncts contained in a clause
    public static ArrayList < String > getPremises(String clause) {
        // get the premise
        String premise = clause.split("=>")[0];
        ArrayList < String > temp = new ArrayList < String > ();
        String[] conjuncts = premise.split("&");
        // for each conjunct
        for (int i = 0; i < conjuncts.length; i++) {
            if (!agenda.contains(conjuncts[i]))
                temp.add(conjuncts[i]);
        }
        return temp;
    }


    // method which checks if c appears in the conclusion of a given clause	
    // input : clause, c
    // output : true if c is in the conclusion of clause
    public static boolean conclusionContains(String clause, String c) {
        String conclusion = clause.split("=>")[1];
        if (conclusion.equals(c))
            return true;
        else
            return false;

    }

}

class BackwardChaining {
    public static void main(String[] args) {
        BC bc = new BC(
            "x",
            "m=>x;f&r=>m;t=>f;t;r");
        System.out.print(bc.execute());
    }
}
