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
    
    private String getRegName(int number, int size){
        if (size == 32){
            String toReturn  ="E"+registerName.get(number);
            return toReturn;
        }
        else{
            return registerName.get(number);
        }                     
    }
    
    private void checkPositive(StackElement sE){    
        if(sE.getType().equals("Register")){
            code.add("CMP "+getRegName(sE.getRegNumber(),sE.getSize())+",0");            
        }
        else{
            code.add("CMP _"+sE.getName()+",0");            
        }        
        code.add("JL ERROR");               
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {                                            
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());                                    
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
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());                                    
                                            SymbolsTableEntry entry = st.getEntry(var1.getPointer());
                                            String toAdd = "MOV _"+entry.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                            stack.push(var1);
                                            }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var2.getName();
                                                code.add(toAdd);
                                                toAdd = "MOV _"+var1.getName()+", "+getRegName(currentRegister, size);
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+getRegName(currentRegister, size)+", _"+var2.getName();
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "MUL "+getRegName(currentRegister, size)+", _"+var2.getName();
                                                code.add(toAdd);
                                                registerOcupation.set(var2.getRegNumber(), false);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                stack.push(element);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "MUL "+getRegName(currentRegister, size)+", _"+var2.getName();
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "DIV "+getRegName(currentRegister, size)+", _"+var2.getName();
                                                code.add(toAdd);
                                                registerOcupation.set(var2.getRegNumber(), false);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                stack.push(element);
                                            }
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "DIV "+getRegName(currentRegister, size)+", _"+var2.getName();
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
                                    if (var1.getSize() == var2.getSize()) { //MISMO TAMAÑO INT-INT o ULONG-ULONG
                                        if (var1.getType().equals("Register")) {
                                        String currentRegisterName1 = getRegName(var1.getRegNumber(), var1.getSize());
                                            if (var2.getType().equals("Register")) {
                                                String currentRegisterName2 = getRegName(var2.getRegNumber(), var2.getSize());
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
                                                String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
                                                String toAdd = "ADD "+currentRegisterName2+", _"+var1.getName();
                                                code.add(toAdd);
                                                stack.push(var2);
                                            }
                                            else {
                                                int currentRegister = getFreeRegister();
                                                int size = var1.getSize(); //puede ser cualquiera de los dos, son del mismo tamaño
                                                if (currentRegister != -1) {
                                                    String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                    code.add(toAdd);
                                                    toAdd = "ADD "+getRegName(currentRegister, size)+", _"+var2.getName();
                                                    code.add(toAdd);
                                                    StackElement element = new StackElement();
                                                    element.setType("Register");
                                                    element.setRegNumber(currentRegister);
                                                    element.setSize(var1.getSize());
                                                    stack.push(element);
                                                }
                                                else
                                                    System.out.println("NO HAY MAS REGISTROS EN +, FIJATE QUE ONDA");
                                            }
                                        }
                                    }
                                    else { //DISTINTO TAMAÑO var1 y var2, HAY QUE CONVERTIR A 32
                                        StackElement var16; //seteo el elemento de 16 bits
                                        StackElement var32;
                                        if (var1.getSize() == 16) {
                                            var16 = var1;
                                            var32 = var2;
                                        }
                                        else { //var2 es de 16
                                            var16 = var2;
                                            var32 = var1;
                                        }
                                        checkPositive(var16);
                                        //ahora igual que antes:
                                        if (var16.getType().equals("Register")) {
                                            String currentRegisterName1  = getRegName(var16.getRegNumber(), var16.getSize());
                                            if (var32.getType().equals("Register")) { //los dos son registros
                                                String currentRegisterName2 = getRegName(var32.getRegNumber(), var32.getSize());
                                                int currentRegister = getFreeRegister(); //pido un reg de 32
                                                int size = 32; 
                                                String toAdd = "MOV "+getRegName(currentRegister, 32)+", 0"; //seteo el nuevo con 0
                                                code.add(toAdd);
                                                toAdd = "MOV "+getRegName(currentRegister, 16)+", "+currentRegisterName1; //seteo la parte chica con el de 16 
                                                code.add(toAdd);
                                                toAdd = "ADD "+currentRegisterName2+", "+getRegName(currentRegister, 32); //le sumo al de 32 el nuevo
                                                code.add(toAdd);
                                                registerOcupation.set(var16.getRegNumber(), false); //libero el de 16
                                                registerOcupation.set(currentRegister, false); //libero el nuevo de 32
                                                stack.push(var32); //pushea el registro de 32 con el resultado de sumar el mismo mas el de 16 pasado a 32
                                            }
                                            else {
                                                int currentRegister = getFreeRegister(); //pido un reg de 32
                                                int size = 32;
                                                String toAdd = "MOV "+getRegName(currentRegister, 32)+", 0"; //seteo el nuevo con 0
                                                code.add(toAdd);
                                                toAdd = "MOV "+getRegName(currentRegister, 16)+", "+currentRegisterName1; //seteo la parte chica con el de 16 
                                                code.add(toAdd);
                                                toAdd = "ADD "+getRegName(currentRegister, 32)+", _"+var32.getName(); //le sumo al reg nuevo la var de 32
                                                code.add(toAdd);
                                                registerOcupation.set(var16.getRegNumber(), false); //libero el de 16
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                element.setSize(32);
                                                stack.push(element); //creo un nuevo stackElement con el Reg nuevo de 32 y lo pusheo
                                            }
                                        }
                                        else {
                                            if (var32.getType().equals("Register")) { //el de 16 NO es un registro, y el de 32 si
                                                String currentRegisterName2 = getRegName(var32.getRegNumber(), var32.getSize());
                                                int currentRegister = getFreeRegister(); //pido un reg de 32 para convertir la var de 16
                                                int size = 32; 
                                                String toAdd = "MOV "+getRegName(currentRegister, 32)+", 0"; //seteo el nuevo con 0
                                                code.add(toAdd);
                                                toAdd = "MOV "+getRegName(currentRegister, 16)+", _"+var16.getName(); //seteo la parte chica con el de 16 
                                                code.add(toAdd);
                                                toAdd = "ADD "+currentRegisterName2+", "+getRegName(currentRegister, 32); //le sumo al de 32 el nuevo
                                                code.add(toAdd);
                                                registerOcupation.set(currentRegister, false); //libero el nuevo de 32
                                                stack.push(var32); //pushea el registro de 32 con el resultado de sumar el mismo mas el de 16 pasado a 32
                                            }
                                            else {//ninguno de los dos es un reg
                                                int currentRegister = getFreeRegister(); //pido un reg de 32
                                                int size = 32;
                                                String toAdd = "MOV "+getRegName(currentRegister, 32)+", 0"; //seteo el nuevo con 0
                                                code.add(toAdd);
                                                toAdd = "MOV "+getRegName(currentRegister, 16)+", _"+var16.getName(); //seteo la parte chica con el de 16 
                                                code.add(toAdd);
                                                toAdd = "ADD "+getRegName(currentRegister, 32)+", _"+var32.getName(); //le sumo al reg nuevo la var de 32
                                                code.add(toAdd);
                                                StackElement element = new StackElement();
                                                element.setType("Register");
                                                element.setRegNumber(currentRegister);
                                                element.setSize(32);
                                                stack.push(element); //creo un nuevo stackElement con el Reg nuevo de 32 y lo pusheo
                                            }
                                        }
                                    }
                                                                        
                                    break;
                                }
                    case "-" :  {
                                    StackElement var2 = stack.pop();
                                    StackElement var1 = stack.pop();
                                    if (var1.getType().equals("Register")) {
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
                                            String toAdd = "SUB "+currentRegisterName2+", _"+var1.getName();
                                            code.add(toAdd);
                                            stack.push(var2);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "SUB "+getRegName(currentRegister, size)+", _"+var2.getName();
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+getRegName(currentRegister, size)+", _"+var2.getName();
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+getRegName(currentRegister, size)+", _"+var2.getName();
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+getRegName(currentRegister, size)+", _"+var2.getName();
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+getRegName(currentRegister, size)+", _"+var2.getName();
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
                                        String currentRegisterName1  = getRegName(var1.getRegNumber(), var1.getSize());
                                        if (var2.getType().equals("Register")) {
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
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
                                            String currentRegisterName2  = getRegName(var2.getRegNumber(), var2.getSize());
                                            String toAdd = "CMP _"+var1.getName()+", "+currentRegisterName2;
                                            registerOcupation.set(var2.getRegNumber(), false);
                                            code.add(toAdd);
                                        }
                                        else {
                                            int currentRegister = getFreeRegister(); int size = var1.getSize();
                                            if (currentRegister != -1) {
                                                String toAdd = "MOV "+getRegName(currentRegister, size)+", _"+var1.getName();
                                                code.add(toAdd);
                                                toAdd = "CMP "+getRegName(currentRegister, size)+", _"+var2.getName();
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
        for (String a : data) {
            System.out.println(a);
        }
        for (String a : code) {
            System.out.println(a);
        }
        return null;
    }
    
}
