package proj2;

public class MyStack <S> { // Stack class ADT
        
    private S stackArray[];                         // Array holding stack
    private static final int DEFAULT_SIZE = 52;     // 52 because that is the max size of a deck of cards
    private int maxSize;                            // Maximum size of stack
    private int top;                                // First free position at top
    
    // Constructors
    @SuppressWarnings("unchecked") // Generic array allocation
    MyStack(int size) 
    {
        maxSize = size;
        top = 0;
        stackArray = (S[])new Object[size]; // Create stackArray
    }

    MyStack() { this(DEFAULT_SIZE); }
    
    public void clear() { top = 0; }    // Reinitialize stack

    
    // Push "it" onto stack
    public boolean push(S value) {
    if (top >= maxSize) return false;
    stackArray[top++] = value;
    return true;
    }
      
    // Remove and return top element
    public S pop() {
    if (top == 0) return null;
    return stackArray[--top];
    }
    
    // Return top element
    public S top() {          
        if (top == 0) return null;
        return stackArray[top-1];
    }


    // Return the number of elements in the stack
    public int length() { return top; }
        
    // Tell if the stack is empty or not
    public boolean isEmpty() { return top == 0; } 
}

