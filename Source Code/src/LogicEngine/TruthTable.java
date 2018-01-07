package LogicEngine;

import java.io.IOException;

public class TruthTable {
    
    public static void main(String [] args) throws IOException
    {
        StringManipulator strMan=new StringManipulator();
        strMan.stringModifier();
        strMan.manipulate();
        strMan.printString();
    }
    
}
