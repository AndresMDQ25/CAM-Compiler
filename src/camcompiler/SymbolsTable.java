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