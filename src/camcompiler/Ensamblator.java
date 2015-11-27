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
    private Vector<String> assembler = new Vector<String>();
    private List registerOcupation = new ArrayList<Boolean>();
    private Stack stack = new Stack();
    
    public Ensamblator() {
        registerOcupation.add(false); //R1
        registerOcupation.add(false); //R2
        registerOcupation.add(false); //R3
        registerOcupation.add(false); //R4
        
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
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "MOV R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "MOV R"+var1+", _"+var2;
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "MOV _"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                            }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var2;
                                                assembler.add(toAdd);
                                                toAdd = "MOV _"+var1+", R"+currentRegister;
                                                assembler.add(toAdd);
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
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP R"+var1+", _"+var2;
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "CMP _"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "CMP R"+currentRegister+", _"+var2;
                                                registerOcupation.set(currentRegister, false);
                                                assembler.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN ==, FIJATE QUE ONDA");
                                        }
                                    }        
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JE L"+jump;
                                    assembler.add(toAdd);
                                    break;
                                }
                    case "*" :  {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "MUL R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "MUL R"+var1+", _"+var2;
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "MUL R"+currentRegister+", _"+var2;
                                                assembler.add(toAdd);
                                                registerOcupation.set(var2, false);
                                                stack.push(currentRegister);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "MUL R"+currentRegister+", _"+var2;
                                                assembler.add(toAdd);
                                                stack.push(currentRegister);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN *, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "/" : {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "DIV R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "DIV R"+var1+", _"+var2;
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "DIV R"+currentRegister+", _"+var2;
                                                assembler.add(toAdd);
                                                registerOcupation.set(var2, false);
                                                stack.push(currentRegister);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "DIV R"+currentRegister+", _"+var2;
                                                assembler.add(toAdd);
                                                stack.push(currentRegister);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN *, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "+" :  {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "ADD R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "ADD R"+var1+", _"+var2;
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "ADD R"+var2+", _"+var1;
                                            assembler.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "ADD R"+currentRegister+", _"+var2;
                                                assembler.add(toAdd);
                                                stack.push(currentRegister);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN +, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "-" :  {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "SUB R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                        else {
                                            String toAdd = "SUB R"+var1+", _"+var2;
                                            assembler.add(toAdd);
                                            stack.push(var1);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "SUB R"+var2+", _"+var1;
                                            assembler.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "SUB R"+currentRegister+", _"+var2;
                                                assembler.add(toAdd);
                                                stack.push(currentRegister);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN -, FIJATE QUE ONDA");
                                        }
                                    }                                    
                                    break;
                                }
                    case "<" :  {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP R"+var1+", _"+var2;
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "CMP _"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "CMP R"+currentRegister+", _"+var2;
                                                registerOcupation.set(currentRegister, false);
                                                assembler.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN <, FIJATE QUE ONDA");
                                        }
                                    }
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JL L"+jump;
                                    assembler.add(toAdd);
                                    break;
                                }
                    case ">" : {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP R"+var1+", _"+var2;
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "CMP _"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "CMP R"+currentRegister+", _"+var2;
                                                registerOcupation.set(currentRegister, false);
                                                assembler.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN >, FIJATE QUE ONDA");
                                        }
                                    }     
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JG L"+jump;
                                    assembler.add(toAdd);
                                    break;
                                }
                    case "<=": {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP R"+var1+", _"+var2;
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "CMP _"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "CMP R"+currentRegister+", _"+var2;
                                                registerOcupation.set(currentRegister, false);
                                                assembler.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN <=, FIJATE QUE ONDA");
                                        }
                                    }     
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JLE L"+jump;
                                    assembler.add(toAdd);
                                    break;
                                }
                    case ">=": {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP R"+var1+", _"+var2;
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "CMP _"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "CMP R"+currentRegister+", _"+var2;
                                                registerOcupation.set(currentRegister, false);
                                                assembler.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN >=, FIJATE QUE ONDA");
                                        }
                                    }           
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JGE L"+jump;
                                    assembler.add(toAdd);
                                    break;
                                }
                    case "<>": {
                                    int var1 = (Integer)stack.pop();
                                    int var2 = (Integer)stack.pop();
                                    if (var1 < 257) {
                                        if (var2 < 257) {
                                            String toAdd = "CMP R"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            String toAdd = "CMP R"+var1+", _"+var2;
                                            registerOcupation.set(var1, false);
                                            assembler.add(toAdd);
                                        }
                                    }
                                    else {
                                        if (var2 < 257) {
                                            String toAdd = "CMP _"+var1+", R"+var2;
                                            registerOcupation.set(var2, false);
                                            assembler.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV R"+currentRegister+", _"+var1;
                                                assembler.add(toAdd);
                                                toAdd = "CMP R"+currentRegister+", _"+var2;
                                                registerOcupation.set(currentRegister, false);
                                                assembler.add(toAdd);
                                            }
                                            else
                                                System.out.println("NO HAY MAS REGISTROS EN <>, FIJATE QUE ONDA");
                                        }
                                    }        
                                    i++;
                                    String jump = (String)polaca.get(i);
                                    String toAdd = "JNE L"+jump;
                                    assembler.add(toAdd);
                                    break;
                                }
                    case "PRINT":{break;}
                    case "BI":{break;}
                    case "BF":{break;}
                    default:    { // es un numero antes del branch
                                    String unomaslargo = (String)o;
                                    if (unomaslargo.startsWith("L")) {
                                        String toAdd = unomaslargo+":";
                                        assembler.add(toAdd);
                                    }                                        
                                    break;
                                }
                }
            }
            else 
                stack.add(o);
        }
        System.out.println(assembler);
        return null;
    }
    
}
