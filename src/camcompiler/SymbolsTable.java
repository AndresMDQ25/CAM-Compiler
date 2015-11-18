package camcompiler;

import byacc.Scope;
import java.util.Vector;

public class SymbolsTable {
    
    private final Vector<SymbolsTableEntry> m;
    
    public SymbolsTable(){
        m=new Vector();
    }        
    private void addEntry(Token t){
        if ((t.getCode()==258) || (t.getCode()==257) || (t.getCode()==294) || (t.getCode()==277))
            m.add(new SymbolsTableEntry(256+m.size()+1, t.getCode(), t.getValue(),1,t));
    }
    
    public SymbolsTableEntry getEntry(int code){
        for (int i=0;i<m.size();i++)
            if(m.elementAt(i).getCode()==code)
                return m.elementAt(i);
        return null;
    }
        
    public int request(Token t){
        if (!((t.getCode()==258) || (t.getCode()==257) || (t.getCode()==294) || (t.getCode()==277)))
            return -1;
        this.addEntry(t);
        return m.elementAt(m.size()-1).getCode();
    }
    public String toString() {
        String s = new String();
        s = "CODE   L_TYPE      S_TYPE     CANT      LEXEME\n";
        for (int i = 0; i < m.size(); i++) {
            s+=m.elementAt(i).toString();
            s+='\n';
        }
        return s;
    }
    public boolean inScope(int pointer, SymbolsTableEntry entry){
        Vector<SymbolsTableEntry> matches = new Vector<>();
        for (int i=0;i<m.size();i++)
            if((m.elementAt(i).getToken().getValue().equals(entry.getSimpleName())) &&(m.elementAt(i).getCode()!= pointer))
                matches.add(m.elementAt(i));
        for (int i=0;i<matches.size();i++)
            if (validScope(matches.elementAt(i).getScope(),entry.getScope()))
                return true;
        return false;
    }
    public boolean validScope(String declaredScope, String currentScope){                
        return currentScope.substring(0,declaredScope.length()).equals(declaredScope);               
    }         
}