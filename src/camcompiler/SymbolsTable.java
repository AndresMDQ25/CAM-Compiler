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
        
    public boolean inScope(int pointer) {
        SymbolsTableEntry entry = this.getEntry(pointer);
        Token t = entry.getToken();
        Vector<SymbolsTableEntry> matches = new Vector<SymbolsTableEntry>();
        for (int i=0;i<m.size();i++)
            if((m.elementAt(i).getLexema().equals(entry.getLexema())) && ((m.elementAt(i).getCode())!= pointer)) {
                    if ((m.elementAt(i).getMyScope().equals("")) || (m.elementAt(i).getMyScope().equals(entry.getScope()))) 
                        matches.add(m.elementAt(i));
                }
        for (int i=matches.size()-1;i>=0;i--) {
            if (validScope(matches.elementAt(i).getScope(),entry.getScope())) {
                t.setPointer(matches.elementAt(i).getCode());
                matches.elementAt(i).increaseCant();
                removeEntry(pointer);
                return true;
            }
        }
        return false;
    }
    public boolean validScope(String declaredScope, String currentScope){                
        return currentScope.substring(0,declaredScope.length()).equals(declaredScope);               
    }
    
    public void removeEntry(int pointer) {
        for (int i = 0; i < m.size(); i++) {
            if (m.elementAt(i).getCode() == pointer)
                m.remove(i);
        }
        
        //acomodar los indices                                                                                                  o no
    }
    
}