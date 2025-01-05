package gui;

import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dbh.DbHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class uye_kayit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtf_ad;
	private JTextField txtf_soyad;
	private JTextField txtf_kullanici;
	private JTextField txtf_mail;
	private JPasswordField pass_txtf;
	
	public void Insert() throws SQLException{
		Connection connect = null;
		DbHelper db = new DbHelper();
		PreparedStatement statement = null;
		try {

			connect = db.getConnection();
			String sql = "insert into bilgiler(Ad, Soyad, Email, Kullanici_Adi, Sifre)" + "values(?,?,?,?,?)";
			statement = connect.prepareStatement(sql);
			statement.setString(1, txtf_ad.getText());
			statement.setString(2, txtf_soyad.getText());
			statement.setString(3, txtf_mail.getText());
			statement.setString(4, txtf_kullanici.getText());
			statement.setString(5, pass_txtf.getText());
			statement.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Kayıt Başarıyla Eklendi");
			this.setVisible(false);
			
		}catch (SQLException exception) {
			db.ShowError(exception);
		}
		finally {
			statement.close();
			connect.close();
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uye_kayit frame = new uye_kayit();
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
	public uye_kayit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Üye Kayıt Formu");
		lblNewLabel.setLabelFor(this);
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setBounds(130, 11, 122, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ad :");
		lblNewLabel_1.setBounds(8, 43, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Soyad :");
		lblNewLabel_2.setBounds(8, 75, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Email :");
		lblNewLabel_3.setBounds(8, 144, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Kullanıcı Adı :");
		lblNewLabel_4.setBounds(8, 108, 104, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Şifre :");
		lblNewLabel_5.setBounds(8, 179, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		txtf_ad = new JTextField();
		txtf_ad.setBounds(143, 40, 128, 20);
		contentPane.add(txtf_ad);
		txtf_ad.setColumns(10);
		
		txtf_soyad = new JTextField();
		txtf_soyad.setBounds(143, 72, 128, 20);
		contentPane.add(txtf_soyad);
		txtf_soyad.setColumns(10);
		
		txtf_kullanici = new JTextField();
		txtf_kullanici.setBounds(143, 105, 128, 20);
		contentPane.add(txtf_kullanici);
		txtf_kullanici.setColumns(10);
		
		txtf_mail = new JTextField();
		txtf_mail.setBounds(143, 141, 128, 20);
		contentPane.add(txtf_mail);
		txtf_mail.setColumns(10);
		
		pass_txtf = new JPasswordField();
		pass_txtf.setBounds(143, 172, 128, 20);
		contentPane.add(pass_txtf);
		
		JButton btn_kayit = new JButton("KAYDOL");
		btn_kayit.addActionListener(new ActionListener() {
			

			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				if (txtf_ad.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ad boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				if (txtf_soyad.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Soyad boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				if (txtf_kullanici.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kullanıcı adı boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				if (txtf_mail.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mail boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				if (!txtf_mail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
				    JOptionPane.showMessageDialog(null, "Yanlış email formatı", "Hata", JOptionPane.ERROR_MESSAGE);
				    return;
				}
				if (pass_txtf.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Şifre boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                                
				try {
					Insert();
				} catch (SQLException e1) {
					Logger.getLogger(uye_kayit.class.getName()).log(Level.SEVERE, null, e1);
				}
				uye_giris uyegiris = new uye_giris();
				uyegiris.setVisible(true);
				

			}
		});
		btn_kayit.setBounds(32, 227, 225, 23);
		contentPane.add(btn_kayit);
		
		JCheckBox chckbxshowpswrd = new JCheckBox("Şifreyi Göster");
		chckbxshowpswrd.setBounds(301, 171, 127, 23);
		contentPane.add(chckbxshowpswrd);
		chckbxshowpswrd.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (chckbxshowpswrd.isSelected()) {
	                	pass_txtf.setEchoChar((char) 0); // Şifreyi göster
	                } else {
	                	pass_txtf.setEchoChar('*'); // Şifreyi gizle
	                }
	            }
	        });
	}
}
