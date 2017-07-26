package assignment1;  
 import java.util.Scanner;  
  import java.util.Stack;  
 public class Assignment1 {  
   private SuccLinkedList []succLinkedList; //Successor Array  
   private int [] pred; //pred Array  
   private SuccLinkedList bag; // Bag  
   private Stack outputStack; // OutputArray Stack  
   private SuccLinkedList outputStack2;  
   private int [] outputArray; // Output Array  
   private int numberStatic; // n  
   private static int numberSorts; // number of sorts  
   public Assignment1(){  
   }  
   public static void main(String[] args) {  
     int number; // n  
     Scanner in = new Scanner(System.in);  
     Scanner in2 = new Scanner(System.in);  
     number = in.nextInt(); // n. O(1)  
     Assignment1 topSorts = new Assignment1(); // create an instance of TopSort class  
     numberSorts = 0; // initialize count of number of TopSorts to 0  
     topSorts.numberStatic = number;  
     topSorts.pred = new int [number+1]; // create new int pred array, size n  
     topSorts.succLinkedList = new SuccLinkedList [number+1]; // create new object array of SuccArray, size n  
     topSorts.outputStack2 = new SuccLinkedList();  
     topSorts.outputStack = new Stack(); // create stack array  
     topSorts.outputArray = new int [number]; // create output array  
     int i = 1;  
     while (i <= number) //initialize pred array and succ array. O(n)  
     {  
       topSorts.pred[i]=0;  
       topSorts.succLinkedList[i]= new SuccLinkedList();  
       i++;  
     }  
     String condition = “default”;  
     while (!condition.equals(“0 0”)) //input pair constraints. O(n)  
     {  
      condition = in2.nextLine();  
      String[] parts = condition.split(” “);  
      int predessor = Integer.parseInt(parts [0]); //store predessor  
      int successor = Integer.parseInt(parts [1]); //store successor  
      if (predessor!=0 && successor !=0){  
      topSorts.pred[successor]= topSorts.pred[successor]+1; //increment pred by 1. O(1)  
      topSorts.succLinkedList[predessor].addListNodeSuccessor(successor);} //add successor. O(1)  
     }  
     topSorts.bag = new SuccLinkedList(); // initialize bag  
     int c = 1;  
     while (c<=number) // place all numbers with zero predecessor into bag. O(n)  
     {  
       if (topSorts.pred[c]==0)  
       {  
         topSorts.bag.addBag(c);  
       }  
       c++;  
     }  
     topSorts.topsort(); //recursive topsort  
     System.out.println(“number of Sorts:” + numberSorts);  
   }  
   private void topsort()  
   {  
     if (!bag.isEmpty()) //check if bag is empty. O(1).  
     {  
       int takenOutBagCount = 0;  
       while(!(bag.size()==takenOutBagCount)) // there is an object not yet taken out.  
       {  
        SuccLinkedList.ListNode nodeTakenOut = bag.returnFirstListNode();  
        bag.pop(); //take it out of bag. O(1)  
        takenOutBagCount++;  
        outputStack2.addListNode(nodeTakenOut); // add to output array. O(1)  
        int takenOutOfBagNumber = nodeTakenOut.data;  
        succLinkedList[takenOutOfBagNumber].pointer = succLinkedList[takenOutOfBagNumber].firstNode; // initialize LinkedList pointer to first node in LinkedList  
        while(succLinkedList[takenOutOfBagNumber].pointer!=null) // reduce pred count for each successor for entire succ array. O(m) to finish while loop  
        {  
         int successorNumber = succLinkedList[takenOutOfBagNumber].pointer.data; //traversal though a successor LinkedList  
         pred[successorNumber]= pred[successorNumber]-1; //reduce pred count by 1. O(1)  
         if (pred[successorNumber] == 0)  
         {  
           if (bag.isEmpty())  
           {  
             bag.firstNode = succLinkedList[takenOutOfBagNumber].pointer; bag.size=bag.size+1;  
             bag.lastNode = succLinkedList[takenOutOfBagNumber].pointer;  
           }  
           else  
           {  
           bag.lastNode.nextBag = succLinkedList[takenOutOfBagNumber].pointer;//add to bag if pred count = 0. O(1)  
          bag.size=bag.size+1;  
           bag.lastNode = succLinkedList[takenOutOfBagNumber].pointer;  
           }  
         }  
         succLinkedList[takenOutOfBagNumber].pointer = succLinkedList[takenOutOfBagNumber].pointer.next; //traversal though a successor LinkedList  
        }  
        topsort();  
        SuccLinkedList.ListNode popListNode = outputStack2.returnListNodeLast();  
        outputStack2.popLastNode();  
        bag.addListNode2(popListNode); // add to bag. O(1)  
        int popNumber = popListNode.data;  
        succLinkedList[popNumber].pointer = succLinkedList[popNumber].firstNode; // initialize LinkedList pointer to first node in LinkedList  
        while(succLinkedList[popNumber].pointer!=null) // add pred count for each successor for entire succ array. O(m) to finish while loop  
        {  
         int successorNumber = succLinkedList[popNumber].pointer.data; //traversal though a successor LinkedList  
         pred[successorNumber]= pred[successorNumber]+1; //add pred count by 1. O(1)  
         if (pred[successorNumber] != 0)  
         {  
           if (succLinkedList[popNumber].pointer.previousBag!=null && succLinkedList[popNumber].pointer.nextBag!=null)  
           {succLinkedList[popNumber].pointer.previousBag.nextBag = succLinkedList[popNumber].pointer.nextBag; bag.size=bag.size-1;} //remove to bag if pred count no longer is 0. O(1)  
           else if(succLinkedList[popNumber].pointer.previousBag==null&&succLinkedList[popNumber].pointer.nextBag!=null)  
           {  
             bag.firstNode = succLinkedList[popNumber].pointer.nextBag;  
             succLinkedList[popNumber].pointer.nextBag = null; bag.size=bag.size-1;  
           }  
           else if(succLinkedList[popNumber].pointer.previousBag!=null&&succLinkedList[popNumber].pointer.nextBag==null)  
           {  
             bag.lastNode = succLinkedList[popNumber].pointer.previousBag;  
            succLinkedList[popNumber].pointer.previousBag.nextBag = null; bag.size=bag.size-1;  
           }  
         }  
         succLinkedList[popNumber].pointer = succLinkedList[popNumber].pointer.next; //traversal though a successor LinkedList  
        }  
       }  
     }  
     else  
     {  
       outputStack2.print();  
        numberSorts++; //adds 1 to number of Sorts  
        if (numberSorts>=50) //limit total number of sorts to 50  
        {  
          System.out.println(“Hit 50 sort limit. Program will end”);  
          System.exit(0);  
        }  
     }  
   }  
 }  
