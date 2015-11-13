package camcompiler;

import java.util.Vector;

public abstract class Logger {
    protected final Vector<String> logs;
    protected String code;
    public Logger(){
        logs=new Vector();
    }
    public Vector<String>getLogs(){
        Vector<String> aux=new Vector();
        for(int i=0; i<logs.size();i++)
            aux.add(code + " "+ logs.elementAt(i));
        return aux;
    }
    public void addLog(String log, int line){
        line++;
        logs.add(log+" at "+ line);
    }
}
