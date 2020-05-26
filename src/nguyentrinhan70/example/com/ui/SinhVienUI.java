package nguyentrinhan70.example.com.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SinhVienUI extends JFrame {
	Connection conn = null;
	
	DefaultTableModel dtm;
	JTable tblSinhVien;
	
	JTextField txtMa, txtTen, txtTuoi;
	JButton btnLuu;
	public SinhVienUI(String title){
		super(title);
		addControls();
		addEvents();
		hienThiDanhSachSinhVien();
		
	}

	public void addControls() {
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		
		dtm = new DefaultTableModel();
		dtm.addColumn("Mã sinh viên");
		dtm.addColumn("Tên sinh viên");
		dtm.addColumn("Tuổi sinh viên");
		tblSinhVien = new JTable(dtm);
		
		JScrollPane sc = new JScrollPane(tblSinhVien,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		con.add(sc, BorderLayout.CENTER);
		
		JPanel pnChiTiet = new JPanel();
		pnChiTiet.setLayout(new BoxLayout(pnChiTiet, BoxLayout.Y_AXIS));
		con.add(pnChiTiet, BorderLayout.SOUTH);
		
		JPanel pnMa = new JPanel();
		pnMa.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblMa = new JLabel("Mã: ");
		txtMa = new JTextField(20);
		pnMa.add(lblMa);
		pnMa.add(txtMa);
		pnChiTiet.add(pnMa);
		
		JPanel pnTen = new JPanel();
		pnTen.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTen = new JLabel("Tên: ");
		txtTen = new JTextField(20);
		pnTen.add(lblTen);
		pnTen.add(txtTen);
		pnChiTiet.add(pnTen);
		
		JPanel pnTuoi=new JPanel();
		pnTuoi.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTuoi=new JLabel("Tuổi:");
		txtTuoi=new JTextField(20);
		pnTuoi.add(lblTuoi);
		pnTuoi.add(txtTuoi);
		pnChiTiet.add(pnTuoi);
		
		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		btnLuu = new JButton("Lưu mới sinh viên");
		pnButton.add(btnLuu);
		pnChiTiet.add(pnButton);
		lblMa.setPreferredSize(lblTuoi.getPreferredSize());
		lblTen.setPreferredSize(lblTuoi .getPreferredSize());

	}

	public void addEvents() {
		// TODO Auto-generated method stub
		btnLuu.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLyLuuMoiSinhVien();
				
			}
		});
		
	}
	protected void xuLyLuuMoiSinhVien() {
		// TODO Auto-generated method stub
				if(conn==null)return;
				try
				{
					Statement statement=conn.createStatement();
					String sqlInsert="insert into sinhvien(ma,ten,tuoi) "
							+ "values('"+txtMa.getText()+"','"+txtTen.getText()+"',"+txtTuoi.getText()+")";
					int kq=statement.executeUpdate(sqlInsert);
					if(kq<0)
					{
						JOptionPane.showMessageDialog(null, "ThÃªm má»›i sinh viÃªn tháº¥t báº¡i");
					}
					else
					{
						hienThiDanhSachSinhVien();
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
	}

	public void showWindow(){
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	public void hienThiDanhSachSinhVien(){
		try{
			String dataBase = "csdl/dbSinhVien.accdb";
			String strConn = "jdbc:ucanaccess://"+dataBase;
			conn= DriverManager.getConnection(strConn);
			
			if(conn!=null){
			java.sql.Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from sinhvien");
			dtm.setRowCount(0);//xóa dữ liệu cũ
			while(resultSet.next()){
				String ma =resultSet.getString("Ma");
				String ten = resultSet.getString("Ten");
				int tuoi =resultSet.getInt("Tuoi");
				Object []arr = {ma,ten,tuoi};
				dtm.addRow(arr);
			}
			
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
	}

}
