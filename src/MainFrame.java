
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DSHH
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form wenben
     */
    public MainFrame() {
        initComponents();
    }

   FileDialog open = new FileDialog(this,"打开",FileDialog.LOAD);
   FileDialog save = new FileDialog(this,"另存为",FileDialog.SAVE);
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        MainTextPane = new javax.swing.JTextPane();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuMenu = new javax.swing.JMenu();
        CreateButton = new javax.swing.JMenuItem();
        OpenButton = new javax.swing.JMenuItem();
        SaveasButton = new javax.swing.JMenuItem();
        CopyButton = new javax.swing.JMenuItem();
        PasteButton = new javax.swing.JMenuItem();
        SearchButton = new javax.swing.JMenuItem();
        QuitButton = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        AutoMenu = new javax.swing.JMenu();
        AutoOnButton = new javax.swing.JMenuItem();
        AutoOffButton = new javax.swing.JMenuItem();
        TimeMenu = new javax.swing.JMenu();
        TimeButton = new javax.swing.JMenuItem();
        HelpMenu = new javax.swing.JMenu();
        HelpButton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(MainTextPane);

        MenuBar.add(jMenu1);

        MenuMenu.setText("菜单");

        CreateButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        CreateButton.setText("新建");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButtonActionPerformed(evt);
            }
        });
        MenuMenu.add(CreateButton);

        OpenButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        OpenButton.setText("打开");
        OpenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenButtonActionPerformed(evt);
            }
        });
        MenuMenu.add(OpenButton);

        SaveasButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveasButton.setText("另存为");
        SaveasButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveasButtonActionPerformed(evt);
            }
        });
        MenuMenu.add(SaveasButton);

        CopyButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        CopyButton.setText("复制");
        CopyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopyButtonActionPerformed(evt);
            }
        });
        MenuMenu.add(CopyButton);

        PasteButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        PasteButton.setText("粘贴");
        PasteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasteButtonActionPerformed(evt);
            }
        });
        MenuMenu.add(PasteButton);

        SearchButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        SearchButton.setText("搜索");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });
        MenuMenu.add(SearchButton);

        QuitButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        QuitButton.setText("退出");
        QuitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitButtonActionPerformed(evt);
            }
        });
        MenuMenu.add(QuitButton);

        MenuBar.add(MenuMenu);
        MenuBar.add(jMenu2);

        AutoMenu.setText("自动换行");

        AutoOnButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        AutoOnButton.setText("启用");
        AutoOnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AutoOnButtonActionPerformed(evt);
            }
        });
        AutoMenu.add(AutoOnButton);

        AutoOffButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        AutoOffButton.setText("禁用");
        AutoOffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AutoOffButtonActionPerformed(evt);
            }
        });
        AutoMenu.add(AutoOffButton);

        MenuBar.add(AutoMenu);

        TimeMenu.setText("时间与日期");

        TimeButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        TimeButton.setText("时间与日期");
        TimeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimeButtonActionPerformed(evt);
            }
        });
        TimeMenu.add(TimeButton);

        MenuBar.add(TimeMenu);

        HelpMenu.setText("帮助");

        HelpButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        HelpButton.setText("使用小助手");
        HelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpButtonActionPerformed(evt);
            }
        });
        HelpMenu.add(HelpButton);

        MenuBar.add(HelpMenu);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
      
    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
       MainTextPane.setText("");
    }//GEN-LAST:event_CreateButtonActionPerformed

    private void OpenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenButtonActionPerformed
        open.setVisible(true);              
	    		
	        String filePath = open.getDirectory();      
                String fileName = open.getFile();                                                        
                             
                
                File value = new File(filePath + fileName);    
                try{
                	FileReader br = new FileReader(value);
                        BufferedReader input = new BufferedReader(br);
                	
                	String str = null;
                	
                	while((str = input.readLine()) != null){
                               
                		MainTextPane.setText(MainTextPane.getText() + str);
                                
                	}
                	
                	input.close();                 
                }catch(IOException e){
                	e.printStackTrace(); 
                }
    }//GEN-LAST:event_OpenButtonActionPerformed

    private void SaveasButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveasButtonActionPerformed
        save.setVisible(true);        
                    
                    String dirPath = save.getDirectory();  
                    String fileName = save.getFile();                                          
                    
                    File value = new File(dirPath + fileName);  
	    		
	    		
	    		try{
                                FileWriter br = new FileWriter(value);
	    			BufferedWriter output = new BufferedWriter(br);
	    			String text =MainTextPane.getText();             
	    			output.write(text);                            
	    			output.close();                                
	    		}catch(IOException e){
	    			e.printStackTrace(); 
	    		}
    }//GEN-LAST:event_SaveasButtonActionPerformed

    private void QuitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_QuitButtonActionPerformed

    private void HelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpButtonActionPerformed
        JOptionPane.showMessageDialog(this,"①“清除”按键可清空文本输入框\r\n②“打开”按钮可读取一个文本文件并输出到文本框中（以追加的形式）\r\n③“另存为”按钮可将内容保存到指定的位置\r\n④“退出”按钮可退出此简单文本编辑器\r\n⑤“复制”按钮可以+"
                + "复制选中的文本内容\r\n⑥“粘贴”按钮可以将剪切板中的内容粘贴到文本框中\r\n⑦“自动换行”启用后可实现自动换行功能，禁用则关闭此功能+"
                + "“时间与日期”按钮可以在文本框中显示此时的日期与时间","帮助", JOptionPane.INFORMATION_MESSAGE);      
    }//GEN-LAST:event_HelpButtonActionPerformed

    private void CopyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopyButtonActionPerformed
        String str=MainTextPane.getSelectedText();
        StringSelection strs = new StringSelection(str);      
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strs, null); 
    }//GEN-LAST:event_CopyButtonActionPerformed

    private void PasteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasteButtonActionPerformed
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable clipTf =sysClip.getContents(null);

        if(clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)){

        try{
            
            String ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
            MainTextPane.setText(MainTextPane.getText() + ret);
           }
            catch(Exception e){
                   e.printStackTrace();
           }

          }

    }//GEN-LAST:event_PasteButtonActionPerformed

    private void AutoOnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AutoOnButtonActionPerformed
        //MainTextPane.setLineWrap(true);
    }//GEN-LAST:event_AutoOnButtonActionPerformed

    private void AutoOffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AutoOffButtonActionPerformed
        //MainTextPane.setLineWrap(false);
    }//GEN-LAST:event_AutoOffButtonActionPerformed

    private void TimeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimeButtonActionPerformed
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
      String str = form.format(cal.getTime());
      MainTextPane.setText(str);
     
    }//GEN-LAST:event_TimeButtonActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        new SearchFrame().setVisible(true);
    }//GEN-LAST:event_SearchButtonActionPerformed

  
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);              
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AutoMenu;
    private javax.swing.JMenuItem AutoOffButton;
    private javax.swing.JMenuItem AutoOnButton;
    private javax.swing.JMenuItem CopyButton;
    private javax.swing.JMenuItem CreateButton;
    private javax.swing.JMenuItem HelpButton;
    private javax.swing.JMenu HelpMenu;
    public static javax.swing.JTextPane MainTextPane;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu MenuMenu;
    private javax.swing.JMenuItem OpenButton;
    private javax.swing.JMenuItem PasteButton;
    private javax.swing.JMenuItem QuitButton;
    private javax.swing.JMenuItem SaveasButton;
    private javax.swing.JMenuItem SearchButton;
    private javax.swing.JMenuItem TimeButton;
    private javax.swing.JMenu TimeMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    public javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private Object getsource(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
