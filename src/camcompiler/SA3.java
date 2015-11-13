package camcompiler;

public class SA3 extends SemanticAction{
    //Accion Semantica 3: CHECKEA RANGO UNSIGNED LONG -> 0<n< 2^32 -1 and removes _ul
    public SA3(LexicAnalyzer lA) {
        super(lA);
    }

    public void run (){
        CAMerror e = lA.getError();
        String s = lA.getString();
        int line = lA.getLine();
        s=s.substring(0, s.length()-3); //REMOVES _ul      
            long value = 0;
            try { //checks long range
                value = Long.valueOf(s);
            } catch (NumberFormatException numberFormatException) {
                e.addLog("Constant out of range", line);
                lA.setString(new String());
                lA.setCode(0);
                lA.setCurrentState(0);
                lA.setCurrentChar(new String());
            }
        if ((value > 0)) {
            lA.setString(Long.toString(value));
            lA.setCode(294); //UNSIGNED LONG CONSTANT TOKEN
        }               
    }
}
