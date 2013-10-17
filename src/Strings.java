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

}

// to represent an empty list of Strings
class MtLoS implements ILoS {
    MtLoS() {
        //empty
    }
  

//returns true because empty list is alphabetical
    public boolean isSorted(StringsCompare compare) {
        return true;
    }
  
    
// insert a string to the list in alphabetic order 
    public ILoS insert(String that, StringsCompare compare) {
        return new OneLoS(that, this);
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
}





// to represent a non-empty list
abstract class ConsLoS implements ILoS {
    String first;
    ConsLoS(String first) {
        this.first = first; 
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
  

//returns if list is sorted as required 
    public abstract boolean isSorted(StringsCompare compare);
    


//insert a string to the list in required order     
    public abstract ConsLoS insert(String that, StringsCompare compare);

//merge two ordered list into given order    
    public abstract ILoS merge(ILoS that, StringsCompare compare); 

//sort the list as required 
    public abstract ILoS sort(StringsCompare compare);
}

//to represent a list with only 1 string 
class OneLoS extends ConsLoS {
    MtLoS rest;
   
    OneLoS(String first,MtLoS rest) {
        super(first);
        this.rest = rest;
    }
    
//returns true because there is only one string
    public boolean isSorted(StringsCompare compare) {
        return true;
    }

//insert a string to the list in required order   
    public ConsLoS insert(String that, StringsCompare compare) {
        if (compare.comesBefore(that, this.first)) {
            return new MultiLoS(that, this); 
        }
        else {
            return new MultiLoS(this.first, new OneLoS(that, new MtLoS()));       	
        }
    }
//merge two ordered list into given order    
    public ILoS merge(ILoS that, StringsCompare compare) {
        return that.insert(this.first, compare);
    }
//sort the list as required 
    public ILoS sort(StringsCompare compare) {
        return this;
    }
}

//to represent a list with more than one strings 
class MultiLoS extends ConsLoS {
    ConsLoS rest;
 
    MultiLoS(String first,ConsLoS rest) {
        super(first);
        this.rest = rest;
    }

// check if the list is sorted as required 
    public boolean isSorted(StringsCompare compare) {
        return compare.comesBefore(this.first, this.rest.first) &&
                this.rest.isSorted(compare);
    }
//insert a string to the list in required order   
    public ConsLoS insert(String that, StringsCompare compare) {
        if (compare.comesBefore(that, this.first)) {
            return new MultiLoS(that, this); 
        }
        else {
            return new MultiLoS(this.first, this.rest.insert(that, compare));    	
        }
    }
  
//merge two ordered list into given order    
    public ILoS merge(ILoS that, StringsCompare compare) {
        return this.rest.merge(that.insert(this.first, compare), compare);
    }
    
//sort the list as required 
    public ILoS sort(StringsCompare compare) {
        return this.rest.sort(compare).insert(this.first, compare);
    }
}



// to represent examples for lists of strings
class ExamplesStrings {
  
    ILoS mary = new MultiLoS("Mary ",
                    new MultiLoS("had ",
                            new MultiLoS("a ",
                                    new MultiLoS("little ",
                                        new OneLoS("lamb.", new MtLoS())))));
    ILoS alphabet = new MultiLoS("aaa",
            new MultiLoS("bb", 
                    new OneLoS("dd", new MtLoS())));
    
    ILoS alphabet1 = new MultiLoS("aaa",
            new MultiLoS("bb", 
                    new OneLoS("c", new MtLoS())));
    ILoS alphabetCom = new MultiLoS("aaa",
            new MultiLoS("aaa",
                    new MultiLoS("bb", 
                            new MultiLoS("bb", 
                                    new MultiLoS("c", 
                                           new OneLoS("dd",
                                                   new MtLoS()
                        ))))));
    ILoS lengthCom = new MultiLoS("aaa",
            new MultiLoS("aaa",
                    new MultiLoS("bb", 
                            new MultiLoS("bb", 
                                    new MultiLoS("dd", 
                                           new OneLoS("c",
                                                   new MtLoS()
                        ))))));
    boolean testIsSorted(Tester t) {
        return
                t.checkExpect(this.mary.isSorted(new StringLexComp()), false) &&
                t.checkExpect(this.alphabet.isSorted(new StringLexComp()), true) &&
                t.checkExpect(this.alphabet1.isSorted(new StringLengthComp()), true) &&
                t.checkExpect(this.alphabetCom.isSorted(new StringLengthComp()), false);
                
    }
    boolean testInsert(Tester t) {
        return
                t.checkExpect(this.alphabet.insert("c", new StringLexComp()), new MultiLoS("aaa",
                        new MultiLoS("bb", new MultiLoS("c", 
                                new OneLoS("dd", new MtLoS()))))) &&
                t.checkExpect(this.alphabet.insert("c", new StringLengthComp()), new MultiLoS("aaa",
                        new MultiLoS("bb", new MultiLoS("dd", 
                                new OneLoS("c", new MtLoS())))));
                                
    }
    boolean testMerge(Tester t) {
        return
                t.checkExpect(this.alphabet.merge(this.alphabet1,new StringLexComp() ),
                        this.alphabetCom ) &&
                t.checkExpect(this.alphabet.merge(this.alphabet1,new StringLengthComp() ),
                        this.lengthCom );        
    }
    boolean testSort(Tester t) {
    	return
    			t.checkExpect(this.mary.sort(new StringLexComp()), 
    					new MultiLoS("Mary ",
    		                    new MultiLoS("a ",
    		                            new MultiLoS("had ",
    		                                    new MultiLoS("lamb.",
    		                                        new OneLoS("little ", new MtLoS())))))) &&
    	t.checkExpect(this.mary.sort(new StringLengthComp()), 
				new MultiLoS("little ",
	                    new MultiLoS("lamb.",
	                            new MultiLoS("Mary ",
	                                    new MultiLoS("had ",
	                                        new OneLoS("a ", new MtLoS()))))));
    		                                        
    }
}
 