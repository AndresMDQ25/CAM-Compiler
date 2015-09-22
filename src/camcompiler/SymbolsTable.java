/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import java.util.Vector;

/**
 *
 * @author Mariano
 */
public class SymbolsTable {
    
    private final Vector<SymbolsTableEntry> m;
    
    public SymbolsTable(){
        m=new Vector();
        m.add(new SymbolsTableEntry(256+m.size()+1, 261, "IF",0,new Token(261,"IF")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 262, "THEN",0,new Token(262,"THEN")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 263, "ELSE",0,new Token(263,"ELSE")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 264, "ENDIF",0,new Token(264,"ENDIF")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 265, "PRINT",0,new Token(265,"PRINT")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 266, "INT",0,new Token(266,"INT")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 267, "BEGIN",0,new Token(267,"BEGIN")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 268, "END",0,new Token(268,"END")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 269, "UNSIGNED",0,new Token(269,"UNSIGNED")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 270, "LONG",0,new Token(270,"LONG")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 271, "MY",0,new Token(271,"MY")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 275, "BY",0,new Token(275,"BY")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 274, "TO",0,new Token(274,"TO")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 273, "FROM",0,new Token(273,"FROM")));
        m.add(new SymbolsTableEntry(256+m.size()+1, 272, "LOOP",0,new Token(272,"LOOP")));
    }        
    private void addEntry(Token t){
        m.add(new SymbolsTableEntry(256+m.size()+1, t.getCode(), t.getValue(),1,t));
    }
    
    public SymbolsTableEntry getEntry(int code){
        for (int i=0;i<m.size();i++)
            if(m.elementAt(i).getCode()==code)
                return m.elementAt(i);
        return null;
    }
        
    public int request(Token t){
        for(int i=0;i<m.size();i++){
           if(m.elementAt(i).getToken().equals(t))
               return m.elementAt(i).getCode();
        }
        this.addEntry(t);
        return m.elementAt(m.size()-1).getCode();
    }
}