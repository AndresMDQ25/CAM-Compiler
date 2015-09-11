/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.cam;

import java.io.File;

/**
 *
 * @author Andres
 */
 class TextFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
        }
    public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Text documents (*.txt)";
        }
    } 
