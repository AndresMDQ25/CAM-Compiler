/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

/**
 *
 * @author Mariano
 */
public class SA4 extends SemanticAction{
    //BORRA EL ULTIMO CARACTER DEL TOKEN Y RETROCEDE EL READER
    public SA4(LexicAnalyzer lA) {
        super(lA);
    } 
    public void run(){        
        Reader r =  lA.getReader();
        String newValue = lA.getString();
        if (!("/n".equals(newValue) || " ".equals(newValue) || "    ".equals(newValue))) {
            r.goToPrevChar();
        }
        newValue = newValue.substring(0, newValue.length()-1);
        lA.setString(newValue);
        SA5 sa5 = new SA5(lA);
        sa5.run();
        if (newValue.equals("-")) {
            lA.setCode(276);
        }    
    }
}
