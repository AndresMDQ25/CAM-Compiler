/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.cam;

import byacc.*;
import camcompiler.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class MainView extends javax.swing.JFrame {
    //Consumer c;
    LexicAnalyzer lexicAnalyzer;
    SymbolsTable st;
    JFileChooser fileChooser;
    Ensamblator ens;
    
    /**
     * Creates new form MainView
     */    
    public MainView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        mainEditor = new javax.swing.JTextPane();
        counterPane = new javax.swing.JTextPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        textBoxPolish = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textBoxSintactic = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        textBoxLexic = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        textBoxSymbolsTable = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        textBoxError = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        textBoxWarning = new javax.swing.JTextPane();
        jMenuBar2 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        run = new javax.swing.JMenu();
        aboutMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CAM Compiler");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(200, 230));

        jSplitPane1.setMinimumSize(new java.awt.Dimension(500, 220));
        jSplitPane1.setName(""); // NOI18N

        mainEditor.setEditable(false);
        jSplitPane1.setRightComponent(mainEditor);

        counterPane.setEditable(false);
        counterPane.setForeground(new java.awt.Color(102, 102, 102));
        counterPane.setDisabledTextColor(new java.awt.Color(102, 102, 0));
        counterPane.setMinimumSize(new java.awt.Dimension(30, 20));
        counterPane.setName(""); // NOI18N
        jSplitPane1.setLeftComponent(counterPane);

        jScrollPane3.setViewportView(jSplitPane1);

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        textBoxPolish.setEditable(false);
        jScrollPane7.setViewportView(textBoxPolish);

        jTabbedPane1.addTab("Output Polish", jScrollPane7);

        textBoxSintactic.setEditable(false);
        jScrollPane1.setViewportView(textBoxSintactic);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1037, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 241, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Output Sintáctico", jPanel2);

        textBoxLexic.setEditable(false);
        jScrollPane6.setViewportView(textBoxLexic);

        jTabbedPane1.addTab("Output Lexico", jScrollPane6);

        textBoxSymbolsTable.setEditable(false);
        jScrollPane5.setViewportView(textBoxSymbolsTable);

        jTabbedPane1.addTab("Symbols Table", jScrollPane5);

        textBoxError.setEditable(false);
        jScrollPane4.setViewportView(textBoxError);

        jTabbedPane1.addTab("Errores", jScrollPane4);

        textBoxWarning.setEditable(false);
        jScrollPane2.setViewportView(textBoxWarning);

        jTabbedPane1.addTab("Warnings", jScrollPane2);

        FileMenu.setText("File");
        FileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileMenuActionPerformed(evt);
            }
        });

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(openMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(exitMenuItem);

        jMenuBar2.add(FileMenu);

        run.setText("Run");
        run.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                runMouseClicked(evt);
            }
        });
        run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runActionPerformed(evt);
            }
        });
        jMenuBar2.add(run);

        aboutMenu.setText("About");
        jMenuBar2.add(aboutMenu);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open file");
	fileChooser.setApproveButtonText("Open file");
        
        // Solo permitimos abrir archivos txt
        TextFilter filter = new TextFilter();
        fileChooser.setFileFilter(filter);        
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile;
                FileReader in;
                in = new FileReader(fileChooser.getSelectedFile().getAbsolutePath());
                st = new SymbolsTable();
             
                BufferedReader br = new BufferedReader(in);
                String code = new String();
                String count = new String();
                int counter = 1;
                String line = br.readLine();
                while (line != null) {
                    count+=(counter+"\n");
                    code+=(line+"\n");
                    counter++;
                    line = br.readLine();
                    
                }
                            
                mainEditor.setText(code);
                counterPane.setText(count);
                
            } catch (IOException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
                
		}
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0); 
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void FileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileMenuActionPerformed
        
    }//GEN-LAST:event_FileMenuActionPerformed

    private void runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runActionPerformed
        
    }//GEN-LAST:event_runActionPerformed

    private void runMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_runMouseClicked
        try { 
            st = new SymbolsTable();
            CAMerror errors = new CAMerror();
            SyntacticLogger synLog = new SyntacticLogger();
            LexicLogger tokens = new LexicLogger();            
            lexicAnalyzer = new LexicAnalyzer(fileChooser.getSelectedFile().getAbsolutePath(),st, errors,tokens);
            Parser p = new Parser(lexicAnalyzer,errors,synLog);            
            p.run();
            ens = new Ensamblator(st);
                       
            
            CAMerror l=lexicAnalyzer.getError();
            Vector<String> e=errors.getLogs();
            String aux=new String();
            for(int i=0; i<e.size();i++)
                aux+=(e.elementAt(i)+"\n");
            textBoxError.setText(aux);
            
            SyntacticLogger sl=p.getSynLog();
            Vector<String> v=sl.getLogs();
            aux=new String();
            for(int i=0; i<v.size();i++)
                aux+=(v.elementAt(i)+"\n");
            textBoxSintactic.setText(aux);
        
            
            
            Warning w=lexicAnalyzer.getWarning();
            e=w.getLogs();
            aux=new String();
            for(int i=0; i<e.size();i++)
                aux+=(e.elementAt(i)+"\n");
            textBoxWarning.setText(aux);
            
            e=tokens.getLogs();
            aux=new String();
            for(int i=0; i<e.size();i++)
                aux+=(e.elementAt(i)+"\n");
            textBoxLexic.setText(aux);
            
            aux = new String();
            aux = st.toString();
            textBoxSymbolsTable.setText(aux);
            
            List polaca = p.getPolich();
            ens.start(polaca);
            System.out.println(polaca);
            for (int i = 0; i < polaca.size()-1; i++) {
                Object o = polaca.get(i);
                if (o instanceof Integer) {
                    SymbolsTableEntry entry = st.getEntry((int)o);
                    polaca.set(i, entry.getLexema()+entry.getScope());
                }             
            }
            if (!(polaca.get(polaca.size()-1) instanceof String)) {
                SymbolsTableEntry entry = st.getEntry((int)polaca.get(polaca.size()-1));
                polaca.set(polaca.size()-1, entry.getLexema()+entry.getScope());
            }
            aux = new String();
            for (int i = 0; i < polaca.size(); i++) {
                aux += i + "    " + polaca.get(i) +"\n";
            }
            textBoxPolish.setText(aux);
            
        } catch (IOException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_runMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JTextPane counterPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane mainEditor;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenu run;
    private javax.swing.JTextPane textBoxError;
    private javax.swing.JTextPane textBoxLexic;
    private javax.swing.JTextPane textBoxPolish;
    private javax.swing.JTextPane textBoxSintactic;
    private javax.swing.JTextPane textBoxSymbolsTable;
    private javax.swing.JTextPane textBoxWarning;
    // End of variables declaration//GEN-END:variables
}
