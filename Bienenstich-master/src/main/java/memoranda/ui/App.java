package main.java.memoranda.ui;

import java.awt.*;
import java.util.Calendar;

import javax.swing.*;

import main.java.memoranda.EventsScheduler;
import main.java.memoranda.util.Configuration;

/**
* App class with version/build info, splash screen, web-urls, full/minimized mode,
*/
public class App {
	static AppFrame frame = null;

	public static final String GUIDE_URL = "https://asu-2201-ser316-19987.slack.com/archives/G0105SESRS5";
	public static final String BUGS_TRACKER_URL = "https://tree.taiga.io/project/amehlhase316-bienenstich/timeline";
	public static final String WEBSITE_URL = "https://github.com/amehlhase316/Bienenstich/tree/dev";

	private JFrame splash = null;

	/*========================================================================*/
	//Semantic Versioning used
 
	public static final String VERSION_INFO = " 0.1.1";
	public static final String BUILD_INFO = "Development build";

	/*========================================================================*/

	public static AppFrame getFrame() {
		return frame;
	}

	public void show() {
		if (frame.isVisible()) {
			frame.toFront();
			frame.requestFocus();
		} else
			init();
	}

	/**
	* App constructor
	*/
	public App(boolean fullmode) {
		super();
		if (fullmode)
			fullmode = !Configuration.get("START_MINIMIZED").equals("yes");
		/* DEBUG */
		if (!fullmode)
			System.out.println("Minimized mode");
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			showSplash();
		System.out.println(VERSION_INFO);

		// bienenstich ui customizations
		// set menu highlight colors
		UIManager.put("Menu.selectionBackground", Color.gray);
		UIManager.put("Menu.selectionForeground", Color.WHITE);
		UIManager.put("MenuItem.selectionBackground", Color.gray);
		UIManager.put("MenuItem.selectionForeground", Color.WHITE);
		UIManager.put("MenuItem.background", Color.darkGray);
		UIManager.put("MenuItem.foreground", Color.white);
//		UIManager.put("Menu.opaque", false);

		// Look and feel stuff to be removed
		// System.out.println(Configuration.get("LOOK_AND_FEEL"));
		// try {
		// 	if (Configuration.get("LOOK_AND_FEEL").equals("system"))
		// 		UIManager.setLookAndFeel(
		// 			UIManager.getSystemLookAndFeelClassName());
		// 	else if (Configuration.get("LOOK_AND_FEEL").equals("default"))
		// 		UIManager.setLookAndFeel(
		// 			UIManager.getCrossPlatformLookAndFeelClassName());
		// 	else if (
		// 		Configuration.get("LOOK_AND_FEEL").toString().length() > 0)
		// 		UIManager.setLookAndFeel(
		// 			Configuration.get("LOOK_AND_FEEL").toString());
		//
		// } catch (Exception e) {
		// 	new ExceptionDialog(e, "Error when initializing a pluggable look-and-feel. Default LF will be used.", "Make sure that specified look-and-feel library classes are on the CLASSPATH.");
		// }
		// end of the look and feel stuff

		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("")) {
			String fdow;
			if (Calendar.getInstance().getFirstDayOfWeek() == 2)
				fdow = "mon";
			else
				fdow = "sun";
			Configuration.put("FIRST_DAY_OF_WEEK", fdow);
			Configuration.saveConfig();
			/* DEBUG */
			System.out.println("[DEBUG] first day of week is set to " + fdow);
		}

		EventsScheduler.init();
		frame = new AppFrame();
		if (fullmode) {
			init();
		}
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			splash.dispose();
	}


	/**
	* Initializes the App Size, finds the java.version
	*/
	void init() {
		double JVMVer = Double.valueOf(System.getProperty("java.version").substring(0, 3)).doubleValue();
		// Startup frame size
		frame.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setExtendedState(Frame.NORMAL);
		// Hopefully this size is better on your systems too!
		frame.setSize(screenSize.width / 2, screenSize.height - 100);
//		frame.setUndecorated(false);
		frame.setVisible(true);
		frame.toFront();
		frame.requestFocus();
	}

	public static void closeWindow() {
		if (frame == null)
			return;
		frame.dispose();
	}

	/**
	 * Method showSplash.
	 */
	private void showSplash() {
		splash = new JFrame();
		ImageIcon spl = new ImageIcon(App.class.getResource("/ui/bienenstich_splash.png"));
		JLabel l = new JLabel();
		l.setSize(1000, 500);
		l.setIcon(spl);
		splash.getContentPane().add(l);
		splash.setSize(1000, 500);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		splash.setLocation(
			(screenSize.width - 1000) / 2,
			(screenSize.height - 500) / 2);
		splash.setUndecorated(true);
		splash.setVisible(true);
	}
}
