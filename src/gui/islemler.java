package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dbh.DbHelper;
import gui.uye_giris.CurrentUser;
import model.doviz;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class islemler extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textF_cekilecek;
    private JTextField textF_yatirilacak;
    public JLabel lblBakiyeValue;  
    private JLabel lblDolarKuru;  
    private JLabel lblEuroKuru;  
    private JTextField txtf_dolaral;
    private JTextField txtf_euroal;
    private JTextField txtf_dolarsat;
    private JTextField txtf_eurosat;
    private JTextField txtf_borcal;
    private JTextField txtf_borcode;
    private JLabel lblDolarMiktar;
    private JLabel lblEuroMiktar;
    private JLabel lblBorcMiktar;
    private JTextField txtf_iban;
    private JTextField txtf_aciklama;
    private JTextField txtf_havaletutar;
    private JTextField txtf_adsoyad;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    islemler frame = new islemler();
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
    public islemler() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 575, 422);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 539, 361);
        contentPane.add(tabbedPane);
        
        double updatedBakiye = getBakiye(CurrentUser.userId);
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedBakiye = df.format(updatedBakiye);
        
       
        double bakiye = getBakiye(CurrentUser.userId);
        
                JPanel panel_giris = new JPanel();
                tabbedPane.addTab("Hoşgeldiniz", null, panel_giris, null);
                panel_giris.setLayout(null);
                
                JLabel lblWelcome = new JLabel("Hoşgeldiniz, " + CurrentUser.username); 
                lblWelcome.setBounds(136, 11, 200, 20);
                panel_giris.add(lblWelcome);
                lblBakiyeValue = new JLabel("Mevcut Bakiyeniz " + formattedBakiye + "₺"); 
                lblBakiyeValue.setBounds(136, 71, 200, 20);  
                panel_giris.add(lblBakiyeValue);
                
                lblDolarMiktar = new JLabel("New label");
                lblDolarMiktar.setBounds(136, 102, 185, 14);
                panel_giris.add(lblDolarMiktar);
                
                lblEuroMiktar = new JLabel("New label");
                lblEuroMiktar.setBounds(136, 127, 185, 14);
                panel_giris.add(lblEuroMiktar);
                	
                lblBorcMiktar = new JLabel("New label");
                lblBorcMiktar.setBounds(136, 152, 185, 14);
                panel_giris.add(lblBorcMiktar);
                updateUserInfoLabels();
        JPanel panel_pcek = new JPanel();
        tabbedPane.addTab("Para Çek", null, panel_pcek, null);
        panel_pcek.setLayout(null);
        
                JLabel lblNewLabel_4 = new JLabel("Para Çekme");
                lblNewLabel_4.setBounds(147, 11, 134, 14);
                panel_pcek.add(lblNewLabel_4);
                
                        JLabel lblNewLabel_5 = new JLabel("Çekilecek Tutar");
                        lblNewLabel_5.setBounds(10, 48, 94, 14);
                        panel_pcek.add(lblNewLabel_5);
                        
                                textF_cekilecek = new JTextField();
                                textF_cekilecek.setBounds(128, 45, 86, 20);
                                panel_pcek.add(textF_cekilecek);
                                textF_cekilecek.setColumns(10);
                                
                                        JButton btn_pcek = new JButton("Para Çek");
                                        btn_pcek.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                float ybakiye = Float.parseFloat(textF_cekilecek.getText());
                                                DbHelper dbHelper = new DbHelper();
                                                String selectSql = "SELECT Para FROM Bilgiler WHERE id_Bilgi = ?";

                                                try (Connection conn = dbHelper.getConnection();
                                                     PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {

                                                    int userId = CurrentUser.userId;
                                                    pstmtSelect.setInt(1, userId);
                                                    ResultSet rs = pstmtSelect.executeQuery();

                                                    if (rs.next()) {
                                                        float mevcutBakiye = rs.getFloat("Para");  
                                                        float yeniBakiye = mevcutBakiye - ybakiye;

                                                        String updateSql = "UPDATE Bilgiler SET Para = ? WHERE id_Bilgi = ?";
                                                        try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
                                                            pstmtUpdate.setFloat(1, yeniBakiye);
                                                            pstmtUpdate.setInt(2, userId);

                                                            int affectedRows = pstmtUpdate.executeUpdate();
                                                            if (affectedRows > 0) {
                                                                JOptionPane.showMessageDialog(null, "Bakiye başarıyla güncellendi!");
                                                                updateBakiyeLabel();  
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Bakiye güncellenemedi!");
                                                            }
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Kullanıcı bulunamadı!");
                                                    }

                                                } catch (SQLException ex) {
                                                    dbHelper.ShowError(ex);
                                                    JOptionPane.showMessageDialog(null, "Veri tabanı hatası: " + ex.getMessage());
                                                }
                                            }
                                        });
                                        
                                        btn_pcek.setBounds(236, 44, 109, 23);
                                        panel_pcek.add(btn_pcek);
        
                JPanel panel_pyatir = new JPanel();
                tabbedPane.addTab("Para Yatır", null, panel_pyatir, null);
                panel_pyatir.setLayout(null);
                
                        JLabel lblNewLabel = new JLabel("Para Yatırma İşlemi");
                        lblNewLabel.setBounds(146, 11, 138, 14);
                        panel_pyatir.add(lblNewLabel);
                        
                                JLabel lblNewLabel_1 = new JLabel("Yatırılacak tutar");
                                lblNewLabel_1.setBounds(10, 53, 106, 14);
                                panel_pyatir.add(lblNewLabel_1);
                                
                                        textF_yatirilacak = new JTextField();
                                        textF_yatirilacak.setBounds(129, 50, 86, 20);
                                        panel_pyatir.add(textF_yatirilacak);
                                        textF_yatirilacak.setColumns(10);
                                        
                                                JButton btn_pyatir = new JButton("Para Yatır");
                                                btn_pyatir.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        float ybakiye = Float.parseFloat(textF_yatirilacak.getText());
                                                        DbHelper dbHelper = new DbHelper();
                                                        String selectSql = "SELECT Para FROM Bilgiler WHERE id_Bilgi = ?";

                                                        try (Connection conn = dbHelper.getConnection();
                                                             PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {

                                                            int userId = CurrentUser.userId;
                                                            pstmtSelect.setInt(1, userId);
                                                            ResultSet rs = pstmtSelect.executeQuery();

                                                            if (rs.next()) {
                                                                float mevcutBakiye = rs.getFloat("Para");
                                                                float yeniBakiye = mevcutBakiye + ybakiye;

                                                                String updateSql = "UPDATE Bilgiler SET Para = ? WHERE id_Bilgi = ?";
                                                                try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
                                                                    pstmtUpdate.setFloat(1, yeniBakiye);
                                                                    pstmtUpdate.setInt(2, userId);

                                                                    int affectedRows = pstmtUpdate.executeUpdate();
                                                                    if (affectedRows > 0) {
                                                                        JOptionPane.showMessageDialog(null, "Bakiye başarıyla güncellendi!");
                                                                        updateBakiyeLabel();  
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "Bakiye güncellenemedi!");
                                                                    }
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Kullanıcı bulunamadı!");
                                                            }

                                                        } catch (SQLException ex) {
                                                            dbHelper.ShowError(ex);
                                                            JOptionPane.showMessageDialog(null, "Veri tabanı hatası: " + ex.getMessage());
                                                        }
                                                    }
                                                });
                                                btn_pyatir.setBounds(248, 49, 112, 23);
                                                panel_pyatir.add(btn_pyatir);

        JPanel panel_doviz = new JPanel();
        tabbedPane.addTab("Döviz İşlemleri", null, panel_doviz, null);
        panel_doviz.setLayout(null);
        
        JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_1.setBounds(0, 0, 488, 304);
        panel_doviz.add(tabbedPane_1);
        
        JPanel panel_dovizal = new JPanel();
        tabbedPane_1.addTab("Döviz Al", null, panel_dovizal, null);
        
        JPanel panel_dovizsat = new JPanel();
        tabbedPane_1.addTab("Döviz Sat", null, panel_dovizsat, null);
        panel_dovizsat.setLayout(null);
        
        JLabel lblDolarKuru_1 = new JLabel("1 USD = 35.27 TRY");
        lblDolarKuru_1.setBounds(32, 11, 114, 14);
        panel_dovizsat.add(lblDolarKuru_1);
        
        JLabel lblEuroKuru_1 = new JLabel("1 EUR = 33.92974 TRY");
        lblEuroKuru_1.setBounds(202, 11, 104, 14);
        panel_dovizsat.add(lblEuroKuru_1);
        
        txtf_dolarsat = new JTextField();
        txtf_dolarsat.setBounds(32, 36, 86, 20);
        panel_dovizsat.add(txtf_dolarsat);
        txtf_dolarsat.setColumns(10);
        
        txtf_eurosat = new JTextField();
        txtf_eurosat.setBounds(202, 36, 86, 20);
        panel_dovizsat.add(txtf_eurosat);
        txtf_eurosat.setColumns(10);
        
        JButton btn_dolarsat = new JButton("Dolar Sat");
        btn_dolarsat.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 try {
        	            double dolarMiktar = Double.parseDouble(txtf_dolarsat.getText());
        	            doviz dovizApi = new doviz();
        	            double dolarKuru = dovizApi.getUsdToTryRate();

        	            double mevcutDolar = getDolar(CurrentUser.userId);

        	            if (mevcutDolar >= dolarMiktar) {
        	                double tlKazanc = dolarMiktar * dolarKuru;

        	                updateDolar(CurrentUser.userId, mevcutDolar - dolarMiktar);

        	                double mevcutBakiye = getBakiye(CurrentUser.userId);
        	                updateBakiye(CurrentUser.userId, mevcutBakiye + tlKazanc);

        	                JOptionPane.showMessageDialog(null, "Dolar satışı başarılı! " + tlKazanc + "₺ kazandınız.");
        	                updateBakiyeLabel();
        	                updateUserInfoLabels();
        	            } else {
        	                JOptionPane.showMessageDialog(null, "Yeterli Dolarınız yok!");
        	            }
        	        } catch (NumberFormatException ex) {
        	            JOptionPane.showMessageDialog(null, "Lütfen geçerli bir dolar miktarı girin.");
        	        } catch (Exception ex) {
        	            JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
        	            ex.printStackTrace();
        	        }
        	    }
        });
        btn_dolarsat.setBounds(32, 67, 89, 23);
        panel_dovizsat.add(btn_dolarsat);
        
        JButton btn_eurosat = new JButton("Euro Sat");
        btn_eurosat.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 try {
        	            double euroMiktar = Double.parseDouble(txtf_eurosat.getText());
        	            doviz dovizApi = new doviz();
        	            double euroKuru = dovizApi.getEurToTryRate();

        	            double mevcutEuro = getEuro(CurrentUser.userId);

        	            if (mevcutEuro >= euroMiktar) {
        	                double tlKazanc = euroMiktar * euroKuru;

        	                updateEuro(CurrentUser.userId, mevcutEuro - euroMiktar);

        	                double mevcutBakiye = getBakiye(CurrentUser.userId);
        	                updateBakiye(CurrentUser.userId, mevcutBakiye + tlKazanc);

        	                JOptionPane.showMessageDialog(null, "Euro satışı başarılı! " + tlKazanc + "₺ kazandınız.");
        	                updateBakiyeLabel();
        	                updateUserInfoLabels();
        	            } else {
        	                JOptionPane.showMessageDialog(null, "Yeterli Euro'nuz yok!");
        	            }
        	        } catch (NumberFormatException ex) {
        	            JOptionPane.showMessageDialog(null, "Lütfen geçerli bir euro miktarı girin.");
        	        } catch (Exception ex) {
        	            JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
        	            ex.printStackTrace();
        	        }
        	    }
        });
        
        btn_eurosat.setBounds(202, 67, 89, 23);
        panel_dovizsat.add(btn_eurosat);
        panel_dovizal.setLayout(null);
        
        lblEuroKuru = new JLabel("Euro/TL Kuru: Yükleniyor...");
        lblEuroKuru.setBounds(207, 11, 104, 14);
        panel_dovizal.add(lblEuroKuru);
        
        lblDolarKuru = new JLabel("Dolar/TL Kuru: Yükleniyor...");
        lblDolarKuru.setBounds(39, 11, 114, 14);
        panel_dovizal.add(lblDolarKuru);
        
        txtf_dolaral = new JTextField();
        txtf_dolaral.setBounds(39, 36, 96, 20);
        panel_dovizal.add(txtf_dolaral);
        txtf_dolaral.setColumns(10);
        
        txtf_euroal = new JTextField();
        txtf_euroal.setBounds(207, 36, 104, 20);
        panel_dovizal.add(txtf_euroal);
        txtf_euroal.setColumns(10);
        
        JButton btn_dolaral = new JButton("Dolar Al");
      
        	btn_dolaral.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
        	    	 try {
        	             double alinacakDolar = Double.parseDouble(txtf_dolaral.getText());
        	             doviz dovizApi = new doviz();
        	             double dolarKuru = dovizApi.getUsdToTryRate();

        	             double gerekliTl = alinacakDolar * dolarKuru;
        	             double mevcutBakiye = getBakiye(CurrentUser.userId);

        	             if (mevcutBakiye >= gerekliTl) {
        	                 double mevcutDolar = getDolar(CurrentUser.userId);
        	                 updateBakiye(CurrentUser.userId, mevcutBakiye - gerekliTl);
        	                 updateDolar(CurrentUser.userId, mevcutDolar + alinacakDolar);

        	                 JOptionPane.showMessageDialog(null, "Başarıyla " + alinacakDolar + " USD satın alındı!");
        	                 updateBakiyeLabel();
        	                 updateUserInfoLabels();
        	             } else {
        	                 JOptionPane.showMessageDialog(null, "Yeterli bakiyeniz yok!");
        	             }
        	         } catch (NumberFormatException ex) {
        	             JOptionPane.showMessageDialog(null, "Lütfen geçerli bir miktar girin.");
        	         } catch (Exception ex) {
        	             ex.printStackTrace();
        	             JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
        	         }
        	     }
        	});
        btn_dolaral.setBounds(39, 67, 96, 23);
        panel_dovizal.add(btn_dolaral);
        
        
        JButton btn_euroal = new JButton("Euro Al");
        btn_euroal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 try {
                     double alinacakEuro = Double.parseDouble(txtf_euroal.getText());
                     doviz dovizApi = new doviz();
                     double euroKuru = dovizApi.getEurToTryRate();

                     double gerekliTl = alinacakEuro * euroKuru;
                     double mevcutBakiye = getBakiye(CurrentUser.userId);

                     if (mevcutBakiye >= gerekliTl) {
                         double mevcutEuro = getEuro(CurrentUser.userId);
                         updateBakiye(CurrentUser.userId, mevcutBakiye - gerekliTl);


                         updateEuro(CurrentUser.userId, mevcutEuro + alinacakEuro);

                         JOptionPane.showMessageDialog(null, "Başarıyla " + alinacakEuro + " EUR satın alındı!");
                         updateBakiyeLabel();
                         updateUserInfoLabels();
                     } else {
                         JOptionPane.showMessageDialog(null, "Yeterli bakiyeniz yok!");
                     }
                 } catch (NumberFormatException ex) {
                     JOptionPane.showMessageDialog(null, "Lütfen geçerli bir miktar girin.");
                 } catch (Exception ex) {
                     ex.printStackTrace();
                     JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
                 }
             }
        });
        
        btn_euroal.setBounds(207, 67, 104, 23);
        panel_dovizal.add(btn_euroal);
        
        updateDovizKuruLabels();
        
        JPanel panel_borc = new JPanel();
        tabbedPane.addTab("Borç İşlemleri", null, panel_borc, null);
        panel_borc.setLayout(null);
        
        JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_2.setBounds(0, 0, 490, 303);
        panel_borc.add(tabbedPane_2);
        
        JPanel panel_al = new JPanel();
        tabbedPane_2.addTab("Borç Al", null, panel_al, null);
        panel_al.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("Güncel Faiz : 2");
        lblNewLabel_2.setBounds(10, 11, 94, 14);
        panel_al.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Almak istediğiniz borç");
        lblNewLabel_3.setBounds(10, 46, 129, 14);
        panel_al.add(lblNewLabel_3);
        
        txtf_borcal = new JTextField();
        txtf_borcal.setBounds(142, 43, 86, 20);
        panel_al.add(txtf_borcal);
        txtf_borcal.setColumns(10);
        
        JButton btn_borcal = new JButton("Borç Al");
        btn_borcal.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                try {

                    double borcMiktar = Double.parseDouble(txtf_borcal.getText());
                    double mevcutBakiye = getBakiye(CurrentUser.userId);
                    double yeniBakiye = mevcutBakiye + borcMiktar;

                    String selectSql = "SELECT Borc FROM Bilgiler WHERE id_Bilgi = ?";
                    DbHelper dbHelper = new DbHelper();
                    double mevcutBorc = 0;
                    try (Connection conn = dbHelper.getConnection();
                         PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {
                        pstmtSelect.setInt(1, CurrentUser.userId);
                        ResultSet rs = pstmtSelect.executeQuery();
                        if (rs.next()) {
                            mevcutBorc = rs.getDouble("Borc");
                        }
                    }

                    double yeniBorc = mevcutBorc + (borcMiktar * 2);

                    String updateSql = "UPDATE Bilgiler SET Para = ?, Borc = ? WHERE id_Bilgi = ?";
                    try (Connection conn = dbHelper.getConnection();
                         PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
                        pstmtUpdate.setDouble(1, yeniBakiye);
                        pstmtUpdate.setDouble(2, yeniBorc);
                        pstmtUpdate.setInt(3, CurrentUser.userId);

                        int affectedRows = pstmtUpdate.executeUpdate();
                        if (affectedRows > 0) {
                            JOptionPane.showMessageDialog(null, "Borç alındı! Yeni bakiyeniz: " + yeniBakiye + "₺, Yeni borcunuz: " + yeniBorc + "₺");
                            updateBakiyeLabel(); 
                            updateUserInfoLabels();
                        } else {
                            JOptionPane.showMessageDialog(null, "İşlem başarısız oldu.");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Lütfen geçerli bir borç miktarı girin.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        btn_borcal.setBounds(252, 42, 89, 23);
        panel_al.add(btn_borcal);
        
        JPanel panel_ode = new JPanel();
        tabbedPane_2.addTab("Borç Öde", null, panel_ode, null);
        panel_ode.setLayout(null);
        
        JLabel lblNewLabel_6 = new JLabel("Ödemek istediğiniz borç");
        lblNewLabel_6.setBounds(10, 47, 184, 14);
        panel_ode.add(lblNewLabel_6);
        
        txtf_borcode = new JTextField();
        txtf_borcode.setBounds(163, 44, 86, 20);
        panel_ode.add(txtf_borcode);
        txtf_borcode.setColumns(10);
        
        JButton btn_borcode = new JButton("Borç Öde");
        btn_borcode.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        	        try {
        	            double odemeMiktar = Double.parseDouble(txtf_borcode.getText());
        	            
        	            double mevcutBakiye = getBakiye(CurrentUser.userId);
        	            double mevcutBorc = 0;
        	            
        	            String selectSql = "SELECT Borc FROM Bilgiler WHERE id_Bilgi = ?";
        	            DbHelper dbHelper = new DbHelper();
        	            try (Connection conn = dbHelper.getConnection();
        	                 PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {
        	                pstmtSelect.setInt(1, CurrentUser.userId);
        	                ResultSet rs = pstmtSelect.executeQuery();
        	                if (rs.next()) {
        	                    mevcutBorc = rs.getDouble("Borc");
        	                }
        	            }
        	            
        	            if (mevcutBakiye < odemeMiktar) {
        	                JOptionPane.showMessageDialog(null, "Bakiyeniz yetersiz!");
        	                return;
        	            }
        	            if (mevcutBorc < odemeMiktar) {
        	                JOptionPane.showMessageDialog(null, "Girdiğiniz miktar mevcut borcunuzdan fazla olamaz!");
        	                return;
        	            }
        	            
        	            double yeniBakiye = mevcutBakiye - odemeMiktar;
        	            double yeniBorc = mevcutBorc - odemeMiktar;

        	            String updateSql = "UPDATE Bilgiler SET Para = ?, Borc = ? WHERE id_Bilgi = ?";
        	            try (Connection conn = dbHelper.getConnection();
        	                 PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
        	                pstmtUpdate.setDouble(1, yeniBakiye);
        	                pstmtUpdate.setDouble(2, yeniBorc);
        	                pstmtUpdate.setInt(3, CurrentUser.userId);

        	                int affectedRows = pstmtUpdate.executeUpdate();
        	                if (affectedRows > 0) {
        	                    JOptionPane.showMessageDialog(null, "Borç ödendi! Yeni bakiyeniz: " + yeniBakiye + "₺, Yeni borcunuz: " + yeniBorc + "₺");
        	                    updateBakiyeLabel(); 
        	                    updateUserInfoLabels();
        	                } else {
        	                    JOptionPane.showMessageDialog(null, "İşlem başarısız oldu.");
        	                }
        	            }
        	        } catch (NumberFormatException ex) {
        	            JOptionPane.showMessageDialog(null, "Lütfen geçerli bir ödeme miktarı girin.");
        	        } catch (SQLException ex) {
        	            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
        	            ex.printStackTrace();
        	        }
        	    }
        });
        btn_borcode.setBounds(277, 43, 89, 23);
        panel_ode.add(btn_borcode);
        
        JPanel panel = new JPanel();
        tabbedPane.addTab("Havale", null, panel, null);
        panel.setLayout(null);
        
        JLabel lblNewLabel_7 = new JLabel("TR");
        lblNewLabel_7.setBounds(10, 40, 46, 14);
        panel.add(lblNewLabel_7);
        
        txtf_iban = new JTextField();
        txtf_iban.setBounds(86, 37, 121, 20);
        panel.add(txtf_iban);
        txtf_iban.setColumns(10);
        
        JButton btn_havale = new JButton("Gönder");
        btn_havale.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
                    String iban = txtf_iban.getText();
                    String adSoyad = txtf_adsoyad.getText();
                    String aciklama = txtf_aciklama.getText();
                    double havaleTutar = Double.parseDouble(txtf_havaletutar.getText());
                    if (iban.length() != 24 || !iban.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Geçerli bir 24 haneli IBAN giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (adSoyad.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Ad ve soyad bilgisi boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    double mevcutBakiye = getBakiye(CurrentUser.userId);
                    if (mevcutBakiye < havaleTutar) {
                        JOptionPane.showMessageDialog(null, "Yetersiz bakiye!", "Hata", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    double yeniBakiye = mevcutBakiye - havaleTutar;
                    updateBakiye(CurrentUser.userId, yeniBakiye);
                    JOptionPane.showMessageDialog(null, 
                        adSoyad + " kişisine " + aciklama +" açıklamasıyla "+havaleTutar + "₺ başarıyla gönderildi!", 
                        "Başarılı", 
                        JOptionPane.INFORMATION_MESSAGE);
                    updateBakiyeLabel();
        		} catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Lütfen geçerli bir tutar giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        		} catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
        	}
        });
        btn_havale.setBounds(102, 194, 89, 23);
        panel.add(btn_havale);
        
        JLabel lblNewLabel_8 = new JLabel("Açıklama");
        lblNewLabel_8.setBounds(10, 120, 66, 14);
        panel.add(lblNewLabel_8);
        
        txtf_aciklama = new JTextField();
        txtf_aciklama.setBounds(86, 117, 121, 20);
        panel.add(txtf_aciklama);
        txtf_aciklama.setColumns(10);
        
        txtf_havaletutar = new JTextField();
        txtf_havaletutar.setBounds(86, 163, 121, 20);
        panel.add(txtf_havaletutar);
        txtf_havaletutar.setColumns(10);
        
        JLabel label = new JLabel("Tutar");
        label.setBounds(10, 166, 46, 14);
        panel.add(label);
        
        JLabel lblNewLabel_9 = new JLabel("Ad Soyad");
        lblNewLabel_9.setBounds(10, 85, 76, 14);
        panel.add(lblNewLabel_9);
        
        txtf_adsoyad = new JTextField();
        txtf_adsoyad.setBounds(86, 82, 121, 20);
        panel.add(txtf_adsoyad);
        txtf_adsoyad.setColumns(10);
        
        
    }
    
    private double getDollarRate(int userId) {
        String selectSql = "SELECT Dolar FROM bilgiler WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {

            pstmtSelect.setInt(1, userId);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                return rs.getDouble("Dolar"); 
            } else {
                return 0.0; // Kullanıcı bulunamadıysa 0 döndür
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0.0; 
        }
    }

    
    private double getEuroRate(int userId) {
        String selectSql = "SELECT Euro FROM bilgiler WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {

            pstmtSelect.setInt(1, userId);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                return rs.getDouble("Euro"); 
            } else {
                return 0.0; // Kullanıcı bulunamadıysa 0 döndür
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0.0; 
        }
    }

    
    private double getDebt(int userId) {
        String selectSql = "SELECT Borc FROM bilgiler WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {

            pstmtSelect.setInt(1, userId);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                return rs.getDouble("Borc"); 
            } else {
                return 0.0; // Kullanıcı bulunamadıysa 0 döndür
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0.0; 
        }
    }

    private void updateUserInfoLabels() {
        try {
            
            double mevcutDolar = getDolar(CurrentUser.userId);
            double mevcutEuro = getEuro(CurrentUser.userId);
            double mevcutBorc = getBorc(CurrentUser.userId);

            
            lblDolarMiktar.setText("Mevcut Dolar Kurunuz: " + mevcutDolar + " USD");
            lblEuroMiktar.setText("Mevcut Euro Kurunuz: " + mevcutEuro + " EUR");
            lblBorcMiktar.setText("Mevcut Borcunuz: " + mevcutBorc + "₺");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Bilgiler yüklenirken hata oluştu: " + e.getMessage());
        }
    }
    
    private double getBorc(int userId) {
        String selectSql = "SELECT Borc FROM Bilgiler WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {

            pstmtSelect.setInt(1, userId);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                return rs.getDouble("Borc");  
            } else {
                return 0.0;  // Kullanıcı bulunamadıysa 0 döndürüyoruz
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0.0; 
        }
    }
    
 // Kullanıcının mevcut dolarını al
    private double getDolar(int userId) {
        String selectSql = "SELECT Dolar FROM Bilgiler WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("Dolar");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0.0; // Eğer bir hata varsa veya kayıt bulunamadıysa 0 döndür
    }

    
 // Kullanıcının mevcut eurosunu al
    private double getEuro(int userId) {
        String selectSql = "SELECT Euro FROM Bilgiler WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("Euro");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0.0; // Eğer bir hata varsa veya kayıt bulunamadıysa 0 döndür
    }

    private void updateDovizKuruLabels() {
        doviz dovizApi = new doviz();
        try {
           
            String dolarKuruText = dovizApi.getUsdToTryRateText();
            lblDolarKuru.setText(dolarKuruText);
            
           
            String euroKuruText = dovizApi.getEurToTryRateText();
            lblEuroKuru.setText(euroKuruText);  
        } catch (Exception e) {
            lblDolarKuru.setText("Dolar kuru alınamadı");
            lblEuroKuru.setText("Euro kuru alınamadı");
            e.printStackTrace();
        }
    }

    // Bakiye etiketini güncelleme fonksiyonu
    private void updateBakiyeLabel() {
        double updatedBakiye = getBakiye(CurrentUser.userId);
        lblBakiyeValue.setText("Mevcut Bakiyeniz: " + updatedBakiye + "₺");
    }
    
    private void updateBakiye(int userId, double yeniBakiye) {
        String updateSql = "UPDATE Bilgiler SET Para = ? WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
            pstmtUpdate.setDouble(1, yeniBakiye);
            pstmtUpdate.setInt(2, userId);
            
            int affectedRows = pstmtUpdate.executeUpdate();
            if (affectedRows > 0) {
                
            } else {
                JOptionPane.showMessageDialog(null, "Bakiyeniz güncellenemedi.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Veri tabanı hatası: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    




    private void updateDolar(int userId, double yeniDolar) {
        String updateSql = "UPDATE Bilgiler SET Dolar = ? WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {

            pstmt.setDouble(1, yeniDolar); 
            pstmt.setInt(2, userId);       

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void updateEuro(int userId, double yeniEuro) {
        String updateSql = "UPDATE Bilgiler SET Euro = ? WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {

            pstmt.setDouble(1, yeniEuro); 
            pstmt.setInt(2, userId);      

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    

    // Kullanıcı bakiyesini almak için SQL sorgusu
    private double getBakiye(int userId) {
        String selectSql = "SELECT Para FROM Bilgiler WHERE id_Bilgi = ?";
        DbHelper dbHelper = new DbHelper();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {

            pstmtSelect.setInt(1, userId);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                return rs.getDouble("Para");  
            } else {
                return 0.0;  // Kullanıcı bulunamadıysa 0 döndürüyoruz
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0.0;  // Eğer veritabanı hatası varsa, 0 döndürüyoruz
        }
    }
}