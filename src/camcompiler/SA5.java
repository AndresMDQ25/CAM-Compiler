package camcompiler;

public class SA5 extends SemanticAction{
    //Accion Semantica 5: VERIFICA PALABRAS RESERVADAS
    public SA5(LexicAnalyzer lA) {
        super(lA);
    } 

    public void run(){
        CAMerror e = lA.getError();
        SA4 sa4 = new SA4(lA);
        sa4.run();
        int line = lA.getLine();
        String word=lA.getString();
        switch(word) {
            case "IF" : { lA.setCode(261); break; }
            case "THEN" : { lA.setCode(262); break; }
            case "ELSE" : { lA.setCode(263); break; }
            case "ENDIF" : { lA.setCode(264); break; } 
            case "PRINT" : { lA.setCode(265); break; } 
            case "INT" :  { lA.setCode(266); break; }
            case "BEGIN" :  { lA.setCode(267); break; }
            case "END" :  { lA.setCode(268); break; }
            case "UNSIGNED" :  { lA.setCode(269); break; }
            case "LONG" :  { lA.setCode(270); break; }
            case "MY" :  { lA.setCode(271); break; }
            case "LOOP" :  { lA.setCode(272);  break; }
            case "FROM" :  { lA.setCode(273); break; }
            case "TO" :  { lA.setCode(274);  break; }
            case "BY" :  { lA.setCode(275);  break; }
            default : {
                e.addLog("not recognized reserved word", line);
                lA.setString(new String());
                lA.setCode(0);
                lA.setCurrentState(0);
                lA.setCurrentChar(new String());
                break;
            }
        }

    }
}
