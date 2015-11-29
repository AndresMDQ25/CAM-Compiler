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
    private Vector<String> data = new Vector<String>();
    private List registerOcupation = new ArrayList<Boolean>();
    private List <String> registerName = new ArrayList<String>();
    private Stack stack = new Stack();
    private SymbolsTable st;
    
    public Ensamblator(SymbolsTable st) {
        registerOcupation.add(false); //R1
        registerName.add("AX");
        registerOcupation.add(false); //R2
        registerName.add("BX");
        registerOcupation.add(false); //R3
        registerName.add("CX");
        registerOcupation.add(false); //R4
        registerName.add("DX");
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
        
        Vector<String> names = st.getNames();
        for (int e = 0; e < names.size(); e++) {
            data.add(names.elementAt(e));
        }
        System.out.println(data);
        for (int i = 0; i < polaca.size(); i++) {
            Object o = polaca.get(i);
            
            if (o instanceof String) {
                System.out.println("String: "+(String)o+" R0: "+registerOcupation.get(0)+" R1: "+registerOcupation.get(1)+" R2: "+registerOcupation.get(2)+" R3: "+registerOcupation.get(3));
                switch((String)o) {
                    case "=" :  {
                                    int var2 = (Integer)stack.pop();
                                    int var1 = (Integer)stack.pop();                                    
                                    if (var1 < 257) {
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {                                            
                                            String currentRegisterName2  = registerName.get(var2);                                    
                                            String toAdd = "MOV "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "MOV "+currentRegisterName1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);                                    
                                            SymbolsTableEntry entry = st.getEntry(var1);
                                            String toAdd = "MOV _"+entry.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                            }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", _"+entry.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            SymbolsTableEntry entry = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "MUL "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "MUL "+currentRegisterName1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "MUL "+registerName.get(currentRegister)+", _"+entry2.getName();
                                                code.add(toAdd);
                                                registerOcupation.set(var2, false);
                                                stack.push(currentRegister);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "MUL "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "DIV "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "DIV "+currentRegisterName1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "DIV "+registerName.get(currentRegister)+", _"+entry2.getName();
                                                code.add(toAdd);
                                                registerOcupation.set(var2, false);
                                                stack.push(currentRegister);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "DIV "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "ADD "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2);
                                            String toAdd = "ADD "+currentRegisterName1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            SymbolsTableEntry entry = st.getEntry(var1);
                                            String toAdd = "ADD "+currentRegisterName2+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1;
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "ADD "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "SUB "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "SUB "+currentRegisterName1+", _"+entry2.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "SUB "+currentRegisterName2+", _"+entry1.getName();
                                            code.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "SUB "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                                        String currentRegisterName1  = registerName.get(var1);
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            SymbolsTableEntry entry2 = st.getEntry(var2);
                                            String toAdd = "CMP "+currentRegisterName1+", _"+entry2.getName();
                                            registerOcupation.set(var1, false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String currentRegisterName2  = registerName.get(var2);
                                            SymbolsTableEntry entry1 = st.getEntry(var1);
                                            String toAdd = "CMP _"+entry1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2, false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                SymbolsTableEntry entry1 = st.getEntry(var1);
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+entry1.getName();
                                                code.add(toAdd);
                                                SymbolsTableEntry entry2 = st.getEntry(var2);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+entry2.getName();
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
                    case "PRINT":   {
                                        int var = (Integer)stack.pop();
                                        SymbolsTableEntry entry1 = st.getEntry(var);
                                        String toAdd = "invoke StdOut, "+entry1.getName();
                                        code.add(toAdd);
                                        break;
                                    }
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
