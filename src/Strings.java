// CS 2510 Fall 2013
// Assignment 4
/*
 * assignment 4
 * Robbins Jeffrey
 * Robbinsj
 * Guo jinhua
 * guoj
 */
import tester.*;

// to compare two string
interface StringsCompare {
	public boolean comesBefore(String s1, String s2);
}

//compare two string lexicographically

class StringLexComp implements StringsCompare {
    public boolean comesBefore(String s1, String s2) {
        if ( s1.compareTo(s2) < 0 )
        { return true; }
        else 
        { return false; }
    }
}

//compare if the longer string comes first

class StringLengthComp implements StringsCompare {
    public boolean comesBefore(String s1, String s2) {
        if ( s1.length() < s2.length() ) { 
            return true; 
        }
        else {
            return false;
        }
    }
}

// to represent a list of Strings
interface ILoS {

    //determines whether given list is sorted
    // by a given request
    boolean isSorted(StringsCompare compare);


    //Helper for merge:
    //insert a string to the list in required order 
    ILoS insert(String that, StringsCompare compare);

    //merge given sorted list into this sorted list 
    //in required order
    ILoS merge(ILoS that, StringsCompare compare);

    //sort the list as required 
    ILoS sort(StringsCompare compare);

    //returns if list is empty
    boolean isEmpty();
    
    //returns contents of list
    String contents();
    
    //combines lists
    ILoS combine(ILoS that);

}

// to represent an empty list of Strings
class MtLoS implements ILoS {
    MtLoS() {
        //empty
    }
    //returns empty string
    public String contents() {
        return "";
    }
  
    //returns contents of ILoS
    public boolean isEmpty() {
        return true;
    }
//returns true because empty list is alphabetical
    public boolean isSorted(StringsCompare compare) {
        return true;
    }
  
    
// insert a string to the list in alphabetic order 
    public ILoS insert(String that, StringsCompare compare) {
        return new ConsLoS(that, this);
    }   
    //merge given sorted list into this sorted list 
    //in required order
    public ILoS merge(ILoS that, StringsCompare compare) {
        return that;
    }

  //sort the list as required 
    public ILoS sort(StringsCompare compare) {
        return this;
    }
  //combines lists
    public ILoS combine(ILoS that) {
        return that;
    }
}





// to represent a non-empty list
class ConsLoS implements ILoS {
    String first;
    ILoS rest;
    ConsLoS(String first, ILoS rest) {
        this.first = first; 
        this.rest = rest;
    }
  //returns empty string
    public String contents() {
        return this.first;
    }

 /*
  TEMPLATE
  FIELDS:
  ... this.first ...         -- String
  ... this.rest ...          -- ILoS
  
  METHODS
  ... this.combine() ...      -- String
  
  METHODS FOR FIELDS
  ... this.first.concat(String) ...       -- String
  ... this.first.compareTo(String) ...    -- int
  ... this.rest.combine() ...             -- String
  
  */
  //returns contents of ILoS
    public boolean isEmpty() {
        return false;
    }
//returns if list is sorted as required 
    public boolean isSorted(StringsCompare compare) {
        if (this.rest.isEmpty()) {
            return true;
        }
        else {
            return compare.comesBefore(this.first, this.rest.contents()) &&
                    this.rest.isSorted(compare);
        }
    }
    


  //insert a string to the list in required order   
    public ILoS insert(String that, StringsCompare compare) {
        if (compare.comesBefore(that, this.first)) {
            return new ConsLoS(that, this); 
        }
        else {
            return new ConsLoS(this.first, this.rest.insert(that, compare));       
        }
    }

  //merge two ordered list into given order    
    public ILoS merge(ILoS that, StringsCompare compare) {
        return this.combine(that).sort(compare);
    }

  //sort the list as required 
    public ILoS sort(StringsCompare compare) {
        return this.rest.sort(compare).insert(this.first, compare);
    }
  //combines lists
    public ILoS combine(ILoS that) {
        return this.rest.combine(new ConsLoS(this.first, that));
    }
}


// to represent examples for lists of strings
class ExamplesStrings {
  
    ILoS mary = new ConsLoS("Mary ",
                    new ConsLoS("had ",
                            new ConsLoS("a ",
                                    new ConsLoS("little ",
                                        new ConsLoS("lamb. ",
                                                new MtLoS())))));
    ILoS alphabet = new ConsLoS("aaa",
            new ConsLoS("bb", 
                    new ConsLoS("dddd", new MtLoS())));
    
    ILoS alphabet1 = new ConsLoS("aaa",
            new ConsLoS("bb", 
                    new ConsLoS("c", new MtLoS())));
    ILoS alphabetCom = new ConsLoS("aaa",
            new ConsLoS("aaa",
                    new ConsLoS("bb", 
                            new ConsLoS("bb", 
                                    new ConsLoS("c", 
                                           new ConsLoS("dddd",
                                                   new MtLoS()
                        ))))));
    ILoS lengthCom = new ConsLoS("c",
            new ConsLoS("bb",
                    new ConsLoS("bb", 
                            new ConsLoS("aaa", 
                                    new ConsLoS("aaa", 
                                           new ConsLoS("dddd",
                                                   new MtLoS()
                        ))))));
    boolean testIsSorted(Tester t) {
        return
                t.checkExpect(this.mary.isSorted(
                        new StringLexComp()), false) &&
                t.checkExpect(this.alphabet.isSorted(
                        new StringLexComp()), true) &&
                t.checkExpect(this.alphabet1.isSorted(
                        new StringLengthComp()),false) &&
                t.checkExpect(this.alphabetCom.isSorted(
                        new StringLengthComp()), false);
                
    }
    boolean testInsert(Tester t) {
        return
                t.checkExpect(this.alphabet.insert("c", new StringLexComp()),
                        new ConsLoS("aaa",
                        new ConsLoS("bb", new ConsLoS("c", 
                                new ConsLoS("dddd", new MtLoS())))));
                                
    }
    boolean testMerge(Tester t) {
        return
                t.checkExpect(this.alphabet.merge(this.alphabet1,
                        new StringLexComp() ),
                        this.alphabetCom ) &&
                t.checkExpect(this.alphabet.merge(this.alphabet1,
                        new StringLengthComp() ),
                        this.lengthCom );        
    }
    boolean testSort(Tester t) {
        return
                t.checkExpect(this.mary.sort(new StringLexComp()), 
                        new ConsLoS("Mary ",
                                new ConsLoS("a ",
                                        new ConsLoS("had ",
                                                new ConsLoS("lamb. ",
                                                        new ConsLoS("little ",
                                                                new MtLoS())))))) &&
                t.checkExpect(this.mary.sort(new StringLengthComp()), 
                        new ConsLoS("a ",
                        new ConsLoS("had ",
                                new ConsLoS("Mary ",
                                        new ConsLoS("lamb. ",
                                            new ConsLoS("little ",
                                                    new MtLoS()))))));
    }
}
 