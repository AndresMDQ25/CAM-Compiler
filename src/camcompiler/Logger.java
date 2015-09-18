/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import java.util.Vector;
import javafx.util.Pair;

/**
 *
 * @author Mariano
 */
public abstract class Logger {
    protected final Vector<Pair<String,Integer>> logs;
    protected String code;
    public Logger(){
        logs=new Vector();
    }
    public Vector<Pair<String, Pair<String,Integer>>>getLogs(){
        Vector<Pair<String, Pair<String,Integer>>> aux=new Vector();
        for(int i=0; i<logs.size();i++)
            aux.add(new Pair(code, logs.elementAt(i)));
        return aux;
    }
    public void addLog(String log, int line){
        logs.add(new Pair(log, line));
    }
}
