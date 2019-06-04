
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


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
        MainTextPane.getDocument().addDocumentListener(new SyntaxHighlighter(MainTextPane));

        //充值
        //SearchButton.setVisible(false);
    }

   FileDialog open = new FileDialog(this,"打开",FileDialog.LOAD);
   FileDialog save = new FileDialog(this,"另存为",FileDialog.SAVE);
   
   /**

 * 当文本输入区的有字符插入或者删除时, 进行高亮.

 * 

 * 要进行语法高亮, 文本输入组件的document要是styled document才行. 所以不要用JTextArea. 可以使用JTextPane.

 * 

 * @author Biao

 * 

 */

class SyntaxHighlighter implements DocumentListener {

	private Set<String> keywords;

	private Style keywordStyle;

	private Style normalStyle;



	public SyntaxHighlighter(JTextPane editor) {

		// 准备着色使用的样式

		keywordStyle = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);

		normalStyle = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);

		StyleConstants.setForeground(keywordStyle, Color.RED);

		StyleConstants.setForeground(normalStyle, Color.BLACK);



		// 准备关键字
                
		keywords = new HashSet();
                String[] kw = {"select", "from",
"where", "like", "and", "or", "order", "group", "sum", "avg",
"not", "in", "create", "grand", "null", "count", "max", "min",
"start", "with", "connect", "update", "delete", "set", "values",
"view", "table", "as", "distinct", "into", "drop", "is", "on",
"exists", "by", "tree", "table", "cust", "union", "dual",
"trigger", "function", "procedure", "begin", "end", "for", "loop",
"while", "insert", "count", "if", "else", "then", "commit",
"rollback", "return", "declare", "when", "elsif", "open", "fetch",
"close", "exit", "exception", "execute"};
            int n = 0;  //保存元素个数的变量
            for(int i = 0; i < kw.length; i++)
            {
                if(null != kw[i]) n++;
            }
             System.out.println(n);

		for (int i = 0;i<n;i++)
                {
                    keywords.add(kw[i]);
                }
                
                keywords.add("public");

		keywords.add("protected");

		keywords.add("private");

		keywords.add("int");

		keywords.add("float");

		keywords.add("double");

	}



	public void colouring(StyledDocument doc, int pos, int len) throws BadLocationException {

		// 取得插入或者删除后影响到的单词.

		// 例如"public"在b后插入一个空格, 就变成了:"pub lic", 这时就有两个单词要处理:"pub"和"lic"

		// 这时要取得的范围是pub中p前面的位置和lic中c后面的位置

		int start = indexOfWordStart(doc, pos);

		int end = indexOfWordEnd(doc, pos + len);



		char ch;

		while (start < end) {

			ch = getCharAt(doc, start);

			if (Character.isLetter(ch) || ch == '_') {

				// 如果是以字母或者下划线开头, 说明是单词

				// pos为处理后的最后一个下标

				start = colouringWord(doc, start);

			} else {

				SwingUtilities.invokeLater(new ColouringTask(doc, start, 1, normalStyle));

				++start;

			}

		}

	}



	/**

	 * 对单词进行着色, 并返回单词结束的下标.

	 * 

	 * @param doc

	 * @param pos

	 * @return

	 * @throws BadLocationException

	 */

	public int colouringWord(StyledDocument doc, int pos) throws BadLocationException {

		int wordEnd = indexOfWordEnd(doc, pos);

		String word = doc.getText(pos, wordEnd - pos);



		if (keywords.contains(word)) {

			// 如果是关键字, 就进行关键字的着色, 否则使用普通的着色.

			// 这里有一点要注意, 在insertUpdate和removeUpdate的方法调用的过程中, 不能修改doc的属性.

			// 但我们又要达到能够修改doc的属性, 所以把此任务放到这个方法的外面去执行.

			// 实现这一目的, 可以使用新线程, 但放到swing的事件队列里去处理更轻便一点.

			SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, keywordStyle));

		} else {

			SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, normalStyle));

		}



		return wordEnd;

	}



	/**

	 * 取得在文档中下标在pos处的字符.

	 * 

	 * 如果pos为doc.getLength(), 返回的是一个文档的结束符, 不会抛出异常. 如果pos<0, 则会抛出异常.

	 * 所以pos的有效值是[0, doc.getLength()]

	 * 

	 * @param doc

	 * @param pos

	 * @return

	 * @throws BadLocationException

	 */

	public char getCharAt(Document doc, int pos) throws BadLocationException {

		return doc.getText(pos, 1).charAt(0);

	}



	/**

	 * 取得下标为pos时, 它所在的单词开始的下标. Â±wor^dÂ± (^表示pos, Â±表示开始或结束的下标)

	 * 

	 * @param doc

	 * @param pos

	 * @return

	 * @throws BadLocationException

	 */

	public int indexOfWordStart(Document doc, int pos) throws BadLocationException {

		// 从pos开始向前找到第一个非单词字符.

		for (; pos > 0 && isWordCharacter(doc, pos - 1); --pos);



		return pos;

	}



	/**

	 * 取得下标为pos时, 它所在的单词结束的下标. Â±wor^dÂ± (^表示pos, Â±表示开始或结束的下标)

	 * 

	 * @param doc

	 * @param pos

	 * @return

	 * @throws BadLocationException

	 */

	public int indexOfWordEnd(Document doc, int pos) throws BadLocationException {

		// 从pos开始向前找到第一个非单词字符.

		for (; isWordCharacter(doc, pos); ++pos);



		return pos;

	}



	/**

	 * 如果一个字符是字母, 数字, 下划线, 则返回true.

	 * 

	 * @param doc

	 * @param pos

	 * @return

	 * @throws BadLocationException

	 */

	public boolean isWordCharacter(Document doc, int pos) throws BadLocationException {

		char ch = getCharAt(doc, pos);

		if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') { return true; }

		return false;

	}



	@Override

	public void changedUpdate(DocumentEvent e) {



	}



	@Override

	public void insertUpdate(DocumentEvent e) {

		try {

			colouring((StyledDocument) e.getDocument(), e.getOffset(), e.getLength());

		} catch (BadLocationException e1) {

			e1.printStackTrace();

		}

	}



	@Override

	public void removeUpdate(DocumentEvent e) {

		try {

			// 因为删除后光标紧接着影响的单词两边, 所以长度就不需要了

			colouring((StyledDocument) e.getDocument(), e.getOffset(), 0);

		} catch (BadLocationException e1) {

			e1.printStackTrace();

		}

	}



	/**

	 * 完成着色任务

	 * 

	 * @author Biao

	 * 

	 */

	private class ColouringTask implements Runnable {

		private StyledDocument doc;

		private Style style;

		private int pos;

		private int len;



		public ColouringTask(StyledDocument doc, int pos, int len, Style style) {

			this.doc = doc;

			this.pos = pos;

			this.len = len;

			this.style = style;

		}



		public void run() {

			try {

				// 这里就是对字符进行着色

				doc.setCharacterAttributes(pos, len, style, true);

			} catch (Exception e) {}

		}

	}

}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        MainTextPane = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
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
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(MainTextPane);

        jButton1.setText("jButton1");

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

        jMenu3.setText("变得更强");
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("点击即可");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        MenuBar.add(jMenu3);

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

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
         // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
         new Money().setVisible(true);
         SearchButton.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    
    
  
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
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    public javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private Object getsource(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
