/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author angus
 */
public class Consumer {
    private int col;
    BufferedReader br;   
    String currentLine;
    public Consumer(String fileName) throws FileNotFoundException, IOException{        
        this.br = new BufferedReader(new FileReader(fileName));                
        this.col  =  0;
        this.currentLine= br.readLine();
        
    }
    public String getChar() throws IOException
    {
        String result="";        
        if (currentLine!=null)
        {
            if (col < currentLine.length())
            {            
                result =""+currentLine.charAt(col);
                col++;
            }
            else  if (col == currentLine.length())
            {                                               
                result="/n";
                col++;
            }
            else 
            {                   
                col=0;
                currentLine= br.readLine();
                if (currentLine==null)
                    result ="/eof";
                else
                {                    
                    result =""+currentLine.charAt(col);
                    col++;
                }
                    
            }         
        }
        if (currentLine==null)
            result ="/eof";        
        return result;
    }
}
