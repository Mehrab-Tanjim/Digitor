package GUI;


import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		//////////the whole app is on a single thread//////////
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {				
				new Mainframe();
			}			
		});
	}
}
