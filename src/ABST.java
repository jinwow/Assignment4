import tester.*;

/** represents a book **/
class Book {
    String title;
    String author;
    int price;
    Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}

/** represents a list of books **/
interface ILoBook {
    /** inserts book into list according to given filter **/
    ILoBook insert(IBookComparator order, Book b);
    /** returns whether the list is empty or not **/
    boolean isEmpty();
}

/**represents an empty list of books **/
class MtLoBook implements ILoBook {
    MtLoBook() {
        //empty constructor
    }
    /** inserts book into list according to given filter **/
    public ILoBook insert(IBookComparator order, Book b) {
        return new ConsLoBook(b, this);
    }
    /** returns whether the list is empty or not **/
    public boolean isEmpty() {
        return true;
    }
}

/**represents populated list of books **/
class ConsLoBook implements ILoBook {
    Book first;
    ILoBook  rest;
    ConsLoBook(Book first, ILoBook rest) {
        this.first = first;
        this.rest = rest;
    }
    /** inserts book into list according to given filter **/
    public ILoBook insert(IBookComparator order, Book b) {
        if (order.compare(this.first, b) > 0) {
            return new ConsLoBook(this.first, this.rest.insert(order, b));
        }
        else {
            return new ConsLoBook(b, this);
        }
    }
    /** returns whether the list is empty or not **/
    public boolean isEmpty() {
        return false;
    }
}
/** interface for filter classes 
 * to compare books
 **/
interface IBookComparator {
    int compare(Book b1, Book b2);
}

/** filter class for comparing books by title **/
class BooksByTitle implements IBookComparator {
    /**
     * returns 
     * <0 if b1<b2
     * 0 if b1=b2
     * >0 if b1>b2
     */
    public int compare(Book b1, Book b2) {
        return b1.title.compareTo(b2.title);
    }
}

/** filter class for comparing books by Author **/
class BooksByAuthor implements IBookComparator {
    /**
     * returns 
     * <0 if b1<b2
     * 0 if b1=b2
     * >0 if b1>b2
     */
    public int compare(Book b1, Book b2) {
        return b1.author.compareTo(b2.author);
    }
}

/** filter class for comparing books by price **/
class BooksByPrice implements IBookComparator {
    /**
     * returns 
     * <0 if b1<b2
     * 0 if b1=b2
     * >0 if b1>b2
     */
    public int compare(Book b1, Book b2) {
        return b1.price - b2.price;
    }
}

/** abstract class of nodes and leafs
 * for binary search tree **/
abstract class ABST {
    IBookComparator order;
    ABST(IBookComparator order) {
        this.order = order;
    }
    /** creates new tree with b in correct place
     * decided by filter
     */
    abstract ABST insert(Book b);
    /** produces first book in binary tree **/
    abstract Book getFirst();
    /** produces ABST search tree with first book removed **/
    abstract ABST getRest();
    /** returns whether node is empty **/
    boolean isEmpty() {
        return false;
    }
    /**for getRest()
     *  finds highest value in branch and adds ABST to right pointer **/
    abstract ABST appendToEnd(ABST b);
    /**for getRest()
     *  finds lowest value in branch and adds ABST to left pointer **/
    abstract ABST appendToStart(ABST b);
    /** determines whether given tree is identical to this tree **/
    abstract boolean sameTree(ABST that);
    /**helper method
     *  returns data held by Node **/
    abstract Book getData();
    /**helper method
     *  returns left pointer of Node **/
    abstract ABST getLeft();
    /**helper method
     *  returns right pointer of Node **/
    abstract ABST getRight();
    /** returns whether this tree contains the same books as the given tree **/
    abstract boolean sameData(ABST that);
    /** returns whether this tree has Book b **/
    abstract boolean inTree(Book b);
    /** combines given sorted list and binary tree into sorted list **/
    abstract ILoBook buildList(ILoBook that);
    /** returns whether given list is the same as this binary tree **/
    abstract boolean sameAsList(ILoBook that);
}

/** represents empty node in binary search tree
 * used for ending a branch **/
class Leaf extends ABST {
    Leaf(IBookComparator order) {
        super(order);
    }
    /** returns Node with b and pointers to 2 leaf **/
    ABST insert(Book b) {
        return new Node(this.order, b, this, this);
    }
    /** produces first book in binary tree **/
    Book getFirst() {
        throw new RuntimeException("No first in an empty tree");
    }
    /** produces ABST search tree with first book removed **/
    ABST getRest() {
        throw new RuntimeException("No rest of an empty tree");
    }
    /** returns whether node is empty **/
    boolean isEmpty() {
        return true;
    }
    /**for getRest()
     *  finds highest value in branch and adds ABST to right pointer **/
    ABST appendToEnd(ABST b) {
        throw new RuntimeException("no tree to append to");
    }
    /**for getRest()
     *  finds lowest value in branch and adds ABST to left pointer **/
    ABST appendToStart(ABST b) {
        throw new RuntimeException("no tree to append to");
    }
    /** determines whether given tree is identical to this tree **/
    boolean sameTree(ABST that) {
        return that.isEmpty();
    }
    /** returns whether this tree contains the same books as the given tree **/
    boolean sameData(ABST that) {
        return that.isEmpty();
    }
    /**helper method
     *  returns data held by Node **/
    Book getData() {
        throw new RuntimeException("no data in empty tree");
    }
    /**helper method
     *  returns left pointer of Node **/
    ABST getLeft() {
        throw new RuntimeException("no pointer in empty tree");
    }
    /**helper method
     *  returns right pointer of Node **/
    ABST getRight() {
        throw new RuntimeException("no pointer in empty tree");
    }
    /** returns whether this tree has Book b **/
    boolean inTree(Book b) {
        return false;
    }
    /** combines given sorted list and binary tree into sorted list **/
    ILoBook buildList(ILoBook that) {
        return that;
    }
    /** returns whether given list is the same as this binary tree **/
    boolean sameAsList(ILoBook that) {
        return that.isEmpty();
    }
}

/** represents node containing information
 *  in binary search tree **/
class Node extends ABST {
    Book data;
    ABST left;
    ABST right;
    Node(IBookComparator order, Book data, ABST left, ABST right) {
        super(order);
        this.data = data;
        this.left = left;
        this.right = right;
    }
    
    /**helper method
     *  returns data held by Node **/
    public Book getData() {
        return this.data;
    }
    /**helper method
     *  returns left pointer of Node **/
    public ABST getLeft() {
        return this.left;
    }
    /**helper method
     *  returns right pointer of Node **/
    public ABST getRight() {
        return this.right;
    }
    
    /** inserts b into correct order using filter 
     * when values are identical they are added to the right
     **/
    public ABST insert(Book b) {
        if (this.order.compare(this.data, b) == 0) {
            return this.right = new Node(this.order, b, new Leaf(this.order), this.right);
        }
        else {
            if (this.order.compare(this.data, b) > 0) {
                return this.right.insert(b);
            }
            else {
                return this.left.insert(b);
            }
        }
    }
    
    /** produces first book in binary tree **/
    public Book getFirst() {
        return this.data;
    }
    
    /** produces ABST search tree with first book removed **/
    public ABST getRest() {
        if (!this.left.isEmpty()) {
            this.left.appendToEnd(this.right);
            return this.left;
        }
        else {
            this.right.appendToStart(this.left);
            return this.right;
        }
    }
    /**Helper for getRest()
     *  finds highest value in branch and adds ABST to right pointer **/
    public ABST appendToEnd(ABST b) {
        if (this.right.isEmpty()) {
            this.right = b;
            return this;
        }
        else {
            return this.right.appendToEnd(b);
        }
    }
    /**Helper for getRest()
     * finds lowest value in branch and adds ABST to left pointer **/
    public ABST appendToStart(ABST b) {
        if (this.left.isEmpty()) {
            this.left = b;
            return this;
        }
        else {
            return this.left.appendToStart(b);
        }
    }
    
    /** determines whether given tree is identical to this tree **/
    public boolean sameTree(ABST that) {
        if (that.isEmpty()) {
            return false;
        }
        else {
            if (this.data == that.getData()) {
                return true &&
                        this.left.sameTree(getLeft()) && 
                        this.right.sameTree(getRight());
            }
            else {
                return false;
            }
        }
    }
    /** returns whether given tree has the same data as this tree **/
    public boolean sameData(ABST that) {
        if (this.sameTree(that)){
            return true;
        }
        else {
            if (that.inTree(this.data)) {
                return true && this.getRest().sameData(that);
            }
            else {
                return false;
            }
        }
    }
    /** returns whether this tree has Book b **/
    boolean inTree(Book b) {
        if (this.data.equals(b)) {
            return true;
        }
        else {
            return this.left.inTree(b) || this.right.inTree(b);
        }
    }
    
    /** combines given sorted list and binary tree into sorted list **/
    ILoBook buildList(ILoBook that) {
        return this.getRest().buildList(that.insert(this.order, this.data));
    }
    /** returns whether given list is the same as this binary tree **/
    boolean sameAsList(ILoBook that) {
        return that.equals(this.buildList(new MtLoBook()));
    }
    
}

class ExamplesBinaryTree {
    Book tale = new Book("the tale of two cities", "Charles Dickons", 10);
    Book lord = new Book("LOTR", "J.R.R. Tolkien", 15);
    Book jeff = new Book("My Life", "Jeff Robbins", 11);
    Book tao = new Book("Tao te Ching", "Lao tzu", 8);
    
    IBookComparator authorComp = new BooksByAuthor();
    IBookComparator titleComp = new BooksByTitle();
    IBookComparator priceComp = new BooksByPrice();
    
    ABST leafPrice = new Leaf(this.priceComp);
    ABST leafTitle = new Leaf(this.titleComp);
    ABST leafAuthor = new Leaf(this.authorComp);
    
    ABST priceTree = new Node(this.priceComp, this.tale, 
            new Node(this.priceComp, this.tao, this.leafPrice, this.leafPrice),
            new Node(this.priceComp, this.lord,
                    new Node(this.priceComp, this.jeff, this.leafPrice, this.leafPrice),
                    this.leafPrice));
    ABST titleTree = new Node(this.titleComp, this.jeff,
            new Node(this.titleComp, this.lord, this.leafTitle, this.leafTitle),
            new Node(this.titleComp, this.tale,
                    new Node(this.titleComp, this.tao, this.leafTitle, this.leafTitle), 
                    this.leafTitle));
    }


