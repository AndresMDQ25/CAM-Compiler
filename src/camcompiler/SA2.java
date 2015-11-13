package camcompiler;

public class SA2 extends SemanticAction{
    //Accion Semantica 2: CHEQUEA RANGO DE CTES ENTERAS  -> -2^15 -1<n< 2^15 -1 Y SACA _i
    public SA2(LexicAnalyzer lA) {
        super(lA);
    }

    public void run (){
        CAMerror e = lA.getError();
        String s = lA.getString();
        int line = lA.getLine();
        char[] dst = new char[s.length()-2]; //REMOVES _i               
        s.getChars(0, s.length()-2, dst, 0);
        s= new String();
        for (char ch: dst){
            s+=ch;
        }
        int value= Integer.valueOf(s);                
        short maxShort = Short.MAX_VALUE;
        short minShort = Short.MIN_VALUE;
        if ((value >= minShort) &&(value <= maxShort))
            lA.setString(Integer.toString(value));
        else
        {
            e.addLog("Constant out of range", line);
            lA.setString(new String());
            lA.setCode(0);
            lA.setCurrentState(0);
            lA.setCurrentChar(new String());
        } 
        lA.setCode(258); //INT CONSTANT TOKEN
    }
}
