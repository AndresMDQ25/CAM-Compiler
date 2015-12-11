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
    private Stack<StackElement> stack = new Stack<StackElement>();
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
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();                                    
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {                                            
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());                                    
                                            String toAdd = "MOV "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            SymbolsTableEntry entry = st.getEntry(var2.getPointer());
                                            String toAdd = "MOV "+currentRegisterName1+", _"+entry.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());                                    
                                            SymbolsTableEntry entry = st.getEntry(var1.getPointer());
                                            String toAdd = "MOV _"+entry.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                            }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var2.getName();
                                                code.add(toAdd);
                                                toAdd = "MOV _"+var1.getName()+", "+currentRegister;
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
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP "+currentRegisterName1+", _"+var2.getName();
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+var2.getName();
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
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "MUL "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "MUL "+currentRegisterName1+", _"+var2.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "MUL "+registerName.get(currentRegister)+", _"+var2.getName();
                                                code.add(toAdd);
                                                registerOcupation.set(var2.getRegNumber(), false);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                stack.push(element);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "MUL "+registerName.get(currentRegister)+", _"+var2.getName();
                                                code.add(toAdd);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                stack.push(element);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN *, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "/" : {
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "DIV "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "DIV "+currentRegisterName1+", _"+var2.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "DIV "+registerName.get(currentRegister)+", _"+var2.getName();
                                                code.add(toAdd);
                                                registerOcupation.set(var2.getRegNumber(), false);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                stack.push(element);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "DIV "+registerName.get(currentRegister)+", _"+var2.getName();
                                                code.add(toAdd);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                stack.push(element);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN *, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "+" :  {
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "ADD "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "ADD "+currentRegisterName1+", _"+var2.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "ADD "+currentRegisterName2+", _"+var1.getName();
                                            code.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "ADD "+registerName.get(currentRegister)+", _"+var2.getName();
                                                code.add(toAdd);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                stack.push(element);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN +, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "-" :  {
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "SUB "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "SUB "+currentRegisterName1+", _"+var2.getName();
                                            code.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "SUB "+currentRegisterName2+", _"+var1.getName();
                                            code.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "SUB "+registerName.get(currentRegister)+", _"+var2.getName();
                                                code.add(toAdd);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                stack.push(element);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN -, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "<" :  {
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP "+currentRegisterName1+", _"+var2.getName();
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+var2.getName();
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
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP "+currentRegisterName1+", _"+var2.getName();
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+var2.getName();
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
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP "+currentRegisterName1+", _"+var2.getName();
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+var2.getName();
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
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP "+currentRegisterName1+", _"+var2.getName();
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+var2.getName();
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
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = registerName.get(var1.getRegNumber());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP "+currentRegisterName1+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP "+currentRegisterName1+", _"+var2.getName();
                                            registerOcupation.set(var1.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = registerName.get(var2.getRegNumber());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+registerName.get(currentRegister)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+registerName.get(currentRegister)+", _"+var2.getName();
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
                                        StackElement var = stack.pop();
                                        String toAdd = "invoke StdOut, "+var.getName();
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
            else {
                StackElement element = new StackElement();
                int var = (Integer)o;
                element.setType("Number");
                SymbolsTableEntry entry = st.getEntry(var);
                String type = entry.getSType();
                if (type.equals("ULONG")) 
                    element.setSize(32);
                else 
                    element.setSize(16);
                element.setName(entry.getName());
                element.setPointer(var);
                stack.add(element);
            } 
        }
        System.out.println(code);
        return null;
    }
    
}
