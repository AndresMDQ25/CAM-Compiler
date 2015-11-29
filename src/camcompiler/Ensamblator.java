/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author Andres
 */
public class Ensamblator {
    private Vector<String> code = new Vector<String>();
    private List registerOcupation = new ArrayList<Boolean>();
    private Stack stack = new Stack();
    private SymbolsTable st = new SymbolsTable();
    
    public Ensamblator(SymbolsTable st) {
        registerOcupation.add(false); //R1
        registerOcupation.add(false); //R2
        registerOcupation.add(false); //R3
        registerOcupation.add(false); //R4
        this.st  = st;
        
    }
    
    private int getFreeRegister() {
        for (int i = 0; i < registerOcupation.size(); i++) {
            if (!((Boolean)registerOcupation.get(i))) {
                registerOcupation.set(i, true);
                return i;        
            }
        }
        return -1;
    }
    
    public List start(List polaca) {
        for (int i = 0; i < polaca.size(); i++) {
            Object o = polaca.get(i);
            
            if (o instanceof String) {
                System.out.println("String: "+(String)o+" R0: "+registerOcupation.get(0)+" R1: "+registerOcupation.get(1)+" R2: "+registerOcupation.get(2)+" R3: "+registerOcupation.get(3));
                switch((String)o) {
                    case "=" :  {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "MOV R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "MOV R"+var1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry = st.getEntry(var1);
                                            String toAdd = "MOV _"+entry.getName()+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                            }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+entry2.getName();
                                                code.add(toAdd);
                                                toAdd = "MOV _"+entry1.getName()+", R"+currentRegister;
                                                code.add(toAdd);
                                                registerOcupation.set(currentRegister, false);
                                                stack.push(var1);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN =, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "==": {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "CMP R"+var1+", _"+entry.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry.getName()+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP R"+currentRegister+", _"+entry2.getName();
                                                registerOcupation.set(currentRegister, false);
                                                code.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN ==, FIJATE QUE ONDA");
                                        }
                                    }        
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JE L"+jump;
                                    code.add(toAdd);
                                    break;
                                }
                    case "*" :  {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "MUL R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "MUL R"+var1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "MUL R"+currentRegister+", _"+entry2.getName();
                                                code.add(toAdd);
                                                registerOcupation.set(var2, false);
                                                stack.push(currentRegister);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "MUL R"+currentRegister+", _"+entry2.getName();
                                                code.add(toAdd);
                                                stack.push(currentRegister);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN *, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "/" : {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "DIV R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "DIV R"+var1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "DIV R"+currentRegister+", _"+entry2.getName();
                                                code.add(toAdd);
                                                registerOcupation.set(var2, false);
                                                stack.push(currentRegister);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "DIV R"+currentRegister+", _"+entry2.getName();
                                                code.add(toAdd);
                                                stack.push(currentRegister);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN *, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "+" :  {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "ADD R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "ADD R"+var1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry = st.getEntry(var1);
                                            String toAdd = "ADD R"+var2+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "ADD R"+currentRegister+", _"+entry2.getName();
                                                code.add(toAdd);
                                                stack.push(currentRegister);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN +, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "-" :  {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "SUB R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "SUB R"+var1+", _"+entry2.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "SUB R"+var2+", _"+entry1.getName();
                                            code.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "SUB R"+currentRegister+", _"+entry2.getName();
                                                code.add(toAdd);
                                                stack.push(currentRegister);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN -, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "<" :  {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP R"+var1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP R"+currentRegister+", _"+entry2.getName();
                                                registerOcupation.set(currentRegister, false);
                                                code.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN <, FIJATE QUE ONDA");
                                        }
                                    }
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JL L"+jump;
                                    code.add(toAdd);
                                    break;
                                }
                    case ">" : {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP R"+var1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP R"+currentRegister+", _"+entry2.getName();
                                                registerOcupation.set(currentRegister, false);
                                                code.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN >, FIJATE QUE ONDA");
                                        }
                                    }     
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JG L"+jump;
                                    code.add(toAdd);
                                    break;
                                }
                    case "<=": {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP R"+var1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP R"+currentRegister+", _"+entry2.getName();
                                                registerOcupation.set(currentRegister, false);
                                                code.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN <=, FIJATE QUE ONDA");
                                        }
                                    }     
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JLE L"+jump;
                                    code.add(toAdd);
                                    break;
                                }
                    case ">=": {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP R"+var1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP R"+currentRegister+", _"+entry2.getName();
                                                registerOcupation.set(currentRegister, false);
                                                code.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN >=, FIJATE QUE ONDA");
                                        }
                                    }           
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JGE L"+jump;
                                    code.add(toAdd);
                                    break;
                                }
                    case "<>": {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP R"+var1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV R"+currentRegister+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP R"+currentRegister+", _"+entry2.getName();
                                                registerOcupation.set(currentRegister, false);
                                                code.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN <>, FIJATE QUE ONDA");
                                        }
                                    }        
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JNE L"+jump;
                                    code.add(toAdd);
                                    break;
                                }
                    case "BI" : {break;}
                    case "BF" : {break;}
                    case "PRINT":{break;}
                    default:    { // es un numero antes del branch
                                    String unomaslargo = (String)o;
                                    if (unomaslargo.startsWith("L")) {
                                        String toAdd = unomaslargo+":";
                                        code.add(toAdd);
                                    }
                                    else {
                                        i++;
                                        String jump = (String)polaca.get(i);
                                        if (jump.equals("BI")) {
                                            String toAdd = "JMP L"+unomaslargo;
                                            code.add(toAdd);
                                        }
                                    }
                                    break;
                                }
                }
            }
            else 
                stack.add(o);
        }
        System.out.println(code);
        return null;
    }
    
}
