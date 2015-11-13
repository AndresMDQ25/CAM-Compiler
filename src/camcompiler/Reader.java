package camcompiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
    }
        
    private void readLine(){
		try{
			String newLine= this.br.readLine();			  
			this.previousLine= this.currentLine;
			this.currentLine = newLine;
		} 
		catch(IOException e) {			
		}	
	}
    
    public String getChar() throws IOException
    {
        String result="";                
        if (this.col > this.currentLine.length()){
            this.readLine();							
            this.col=0;	
        }
        if (this.currentLine == null)
               result= "$";                                     		
        else{
            if (this.col==this.currentLine.length())	          		
                    result= "/n"; 					
            else{
                char aux= (this.currentLine.charAt(col));
                result=""+aux;
                switch (aux){
                        //case '\t': result="/t"; break;
                        case ' ': result=" "; break;
                }						
            }			
            this.col++;
        }	                        
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