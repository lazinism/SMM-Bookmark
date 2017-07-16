package com.lazinism.nokduro;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AutoBookmark {
	
	public static void main(String[] args){
		new Login();
	}
	String cookies;
	
	JFrame jf;
	JTextField jtf;
	
	public AutoBookmark(String cookies, String id) {
		this.cookies = cookies;
		this.jf = new JFrame("���� �ϸ�ũ ��ϱ� - �α��� �Ϸ�("+id+")");
		setcenter(jf);
		jf.setSize(400, 100);
		jf.setLayout(new GridLayout(2, 1));
		jtf = new JTextField();
		JButton jb = new JButton("�ϸ�ũ ���");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(jb)){
					String mapcode = jtf.getText();
					String surl = "https://supermariomakerbookmark.nintendo.net/courses/" + mapcode;
					int res = 0;
					try {
						res = postURL(surl, "");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if(res == 404){
						JOptionPane.showMessageDialog(null, "���� �������� �ʽ��ϴ�.", "���� �ϸ�ũ ��ϱ� - ����", JOptionPane.ERROR_MESSAGE);
					}
					else{
						surl = "https://supermariomakerbookmark.nintendo.net/courses/" + mapcode + "/play_at_later";
						try {
							res = postURL(surl, "");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						if(res == 500){
							System.out.println("[BOOKMARK] �ϸ�ũ ����");
						}
						else if(res == 200){
							System.out.println("[BOOKMARK] �ϸ�ũ ����");
						}
					}
				}
			}
		});
		
	}

	private void setcenter(JFrame frame){
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ�� ������	
	    Dimension dimen1 = frame.getSize(); // ������ ������
	    int xpos = (int)(dimen.getWidth()/2-dimen1.getWidth()/2); 
	    int ypos = (int)(dimen.getHeight()/2-dimen1.getHeight()/2);
	    frame.setLocation(xpos,ypos);
	}
	
	public int postURL(String surl, String param) throws IOException{
		URL url = new URL(surl);
		HttpsURLConnection conn =  (HttpsURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if (cookies != null) {
        	conn.addRequestProperty("Cookie", cookies);
        }
        
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(param);
		wr.flush();
		wr.close();
		String cookieTemp = conn.getHeaderField("Set-Cookie");
        if(cookieTemp != null){
        	cookies = cookieTemp;
        }
        int responseCode = conn.getResponseCode();
        System.out.println("[LOGIN] POST �õ� ��... | " + param +" --> "+surl);
        return responseCode;
	}
	
}

class Login {
	JFrame jf;
	JTextField idf;
	JPasswordField pwf;
	public Login() {
		jf = new JFrame("���� �ϸ�ũ ��ϱ� - �α���");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(400, 100);
		jf.setLayout(new GridLayout(2, 1));
		JPanel idpw = new JPanel();
		idpw.setLayout(new GridLayout(1, 2));
		idf = new JTextField("���̵�");
		pwf = new JPasswordField("��й�ȣ");
		idpw.add(idf);idpw.add(pwf);
		jf.add(idpw);
		JButton confirm = new JButton("�α���");
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(confirm)){
					doLogin();
				}
			}
		});
		jf.add(confirm);
		setcenter(jf);
		jf.setVisible(true);
	}
	private void setcenter(JFrame frame){
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ�� ������	
	    Dimension dimen1 = frame.getSize(); // ������ ������
	    int xpos = (int)(dimen.getWidth()/2-dimen1.getWidth()/2); 
	    int ypos = (int)(dimen.getHeight()/2-dimen1.getHeight()/2);
	    frame.setLocation(xpos,ypos);
	}
	
	private void doLogin(){
		String authurl = "https://supermariomakerbookmark.nintendo.net/users/auth/nintendo";
		String param;
		String id = idf.getText();
		String pw = new String(pwf.getPassword());
		int res;
		param = "lang=ja-JP&nintendo_authenticate&nintendo_authorize&scope&username="+id+"%password="+pw+"";
		try {
			res = postURL(authurl, param);
			if(res == 303){
				new AutoBookmark(cookies, id);
				jf.dispose();
			}
			else{
				System.out.println("[LOGIN] �α��� ����.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	String cookies;
	public int postURL(String surl, String param) throws IOException{
		URL url = new URL(surl);
		HttpsURLConnection conn =  (HttpsURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if (cookies != null) {
        	conn.addRequestProperty("Cookie", cookies);
        }
        
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(param);
		wr.flush();
		wr.close();
		String cookieTemp = conn.getHeaderField("Set-Cookie");
        if(cookieTemp != null){
        	cookies = cookieTemp;
        }
        int responseCode = conn.getResponseCode();
        System.out.println("[LOGIN] POST �õ� ��... | " + param +" --> "+surl);
        return responseCode;
	}
	
}
