/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author Mariano
 */
public class SymbolsTable {
    
    private final Map<Token, Integer> m;
    
    public SymbolsTable(){
        System.out.println("Symbols Table Created");
        m=new Hashtable();
    }        
    public void addEntry(int code, Token t){
        m.put(t, code);
    }
    public Integer getCode(Token t){
        return m.get(t);
    }
    
    private Map.Entry findValue(Integer name){
        for (Object o: m.entrySet()) {
            Map.Entry entry;
            entry = (Map.Entry) o;
            //System.out.println("NAME: "+name);
            //System.out.println("ENTRY: " + entry.getValue());
            if((int)entry.getValue() == (int)name)
            {
                System.out.println("ENcontre");
                return entry;
            }
        }
        return null;
    }
    
    public Map.Entry request(Token t){
        Integer code;
        if (!m.containsKey(t)){
            System.out.println("MAP SIZE: "+m.size());
            this.addEntry(256+m.size()+1,t);
             code=256+m.size();
             //System.out.println("CODIGO A AGREGAR: "+code);
             //System.out.println("VOY A AGREGAR");
        }
        else
            code=getCode(t);
        return this.findValue(code);
    }
}
