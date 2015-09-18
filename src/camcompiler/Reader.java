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
public class Reader {
    
    private int col;
    private int line;
    private BufferedReader br;   
    private String currentLine;
    private String previousLine;
    
    public Reader(String fileName) throws FileNotFoundException, IOException{        
        this.br = new BufferedReader(new FileReader(fileName));                
        this.col  =  0;
        this.currentLine= br.readLine();
        this.previousLine ="";
        this.line=1;        
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
                previousLine = currentLine;            
                line++;
       
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
            
    public void goToPrevChar(){
        if (col-1 < 0)
        {
                col = 0;
                line --;                
                currentLine = previousLine;
        }
        else 
        {
            col--;        
        }
    }     
}
