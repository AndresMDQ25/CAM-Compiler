/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byacc;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Mariano
 */
public class Scope {
    private final Scope father;
    private final String name;
    private final List scopesInside;
    
    public Scope(String name, Scope father){
        scopesInside = new ArrayList();
        this.father=father;
        this.name=name;
    }
    
    public String getName() {return this.name;}
    
    public Scope getFather(){return this.father;}
    
    public int getScopesContained(){return scopesInside.size();}
    
    public void push(Scope newScope)
    {
        scopesInside.add(newScope);
    }
    
    public String getScopeSuffix(){
        Scope a=this;
        String acc=new String();
        while(a!=null)
            acc='_'+a.getName()+acc;
        return acc;
    }
    
}
