package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.kullanici_dao;
import dbh.DbHelper;
import model.kullanici;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;

public class uye_giris extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField jtex_kad;
	private JPasswordField jpass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uye_giris frame = new uye_giris();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public uye_giris() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Kullanıcı Adı :");
		lblNewLabel.setBounds(10, 78, 89, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Şifre :");
		lblNewLabel_1.setBounds(10, 122, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		jtex_kad = new JTextField();
		jtex_kad.setBounds(112, 75, 86, 20);
		contentPane.add(jtex_kad);
		jtex_kad.setColumns(10);
		JButton btn_giris = new JButton("Giriş");
		btn_giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kullanici_dao kullanici_dao=new kullanici_dao();
				String kullanici_adi =jtex_kad.getText();
				String Sifre =jpass.getText();
				kullanici Kullanici=kullanici_dao.login(kullanici_adi, Sifre);
				// Ad ve Soyad kontrolü
                if (kullanici_adi.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kullanıcı adı boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Sifre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Şifre bilgisi boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				if(Kullanici!=null) {
					JOptionPane.showConfirmDialog(null, "Hoşgeldin "+Kullanici.getKullanici_adi());
					CurrentUser.userId = getUserId(kullanici_adi);
					CurrentUser.username = Kullanici.getKullanici_adi();
					setVisible(false);
					islemler islemler = new islemler();
	                islemler.setVisible(true);
	                
				}
				else {
					JOptionPane.showMessageDialog(null, "Giriş başarısız! Kullanıcı adı veya Şifre hatalı","Başarısız Giriş",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	
		btn_giris.setBounds(109, 171, 89, 23);
		contentPane.add(btn_giris);
		
		jpass = new JPasswordField();
		jpass.setBounds(109, 119, 89, 20);
		contentPane.add(jpass);
		
		JLabel lblNewLabel_2 = new JLabel("Üye giriş formu");
		lblNewLabel_2.setBounds(170, 25, 97, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btn_kayit = new JButton("Kayıt Ol");
		btn_kayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false); 
		        uye_kayit kayitEkrani = new uye_kayit(); // Yeni pencereyi oluştur
		        kayitEkrani.setVisible(true); 
			}
		});
		btn_kayit.setBounds(218, 171, 89, 23);
		contentPane.add(btn_kayit);
		
		JCheckBox chckbxshowpswrd = new JCheckBox("Şifreyi Göster");
		chckbxshowpswrd.setBounds(222, 118, 142, 23);
		contentPane.add(chckbxshowpswrd);
		chckbxshowpswrd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chckbxshowpswrd.isSelected()) {
                	jpass.setEchoChar((char) 0); // Şifreyi göster
                } else {
                	jpass.setEchoChar('*'); // Şifreyi gizle
                }
            }
        });
	}
	public class CurrentUser {
	    protected static String username;
		public static int userId; // Kullanıcı ID'sini burada saklanıyor
	}
	 // Kullanıcı adı ile kullanıcı ID'sini almak için bir metod
	private int getUserId(String username) {
		String selectSql = "SELECT id_Bilgi FROM bilgiler WHERE Kullanici_adi = ?";
	    DbHelper dbHelper = new DbHelper();
	    try (Connection conn = dbHelper.getConnection();
	         PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {
	        
	        pstmtSelect.setString(1, username);
	        ResultSet rs = pstmtSelect.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt("id_Bilgi"); 
	        } else {
	            return -1; // Kullanıcı bulunamadı
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        return -1; // Eger veritabanı hatası varsa
	    }
	}
	
}
