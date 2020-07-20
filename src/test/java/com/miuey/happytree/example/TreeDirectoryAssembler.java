package com.miuey.happytree.example;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This test class is used to assemble a tree of {@link Directory} objects.
 * 
 * <p>It consists of a tree that will be used as an example in the development
 * of test cases. The tree in question will be of objects of type
 * {@link Directory} and the objective is to simulate a directory structure in a
 * file system.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public final class TreeDirectoryAssembler {

	
	private TreeDirectoryAssembler() {}
	
	
	/**
	 * Build a {@link Directory} tree to represents a file system and return it.
	 * 
	 * <p>Below is the following structure that will be used for this tree:</p>
	 * 
	 * <ul>
	 * 	<li>Program Files</li>
	 * 		<ul>
	 * 			<li>Adobe</li>
	 * 				<ul>
	 * 					<li>Reader</li>
	 * 						<ul>
	 * 							<li>reader.exe</li>
	 * 						</ul>
	 * 					<li>Photoshop</li>
	 * 						<ul>
	 * 							<li>photoshop.exe</li>
	 * 						</ul>
	 * 					<li>Dreamweaver</li>
	 * 						<ul>
	 * 							<li>dreamweaver.exe</li>
	 * 						</ul>
	 * 				</ul>
	 * 			<li>Realtek</li>
	 * 				<ul>
	 * 					<li>drivers</li>
	 * 						<ul>
	 * 							<li>bin</li>
	 * 						</ul>
	 * 					<li>sdk</li>
	 * 						<ul>
	 * 							<li>files</li>
	 * 						</ul>
	 * 					<li>readme.txt</li>
	 * 				</ul>
	 * 			<li>Winamp</li>
	 * 				<ul>
	 * 					<li>winamp.exe</li>
	 * 				</ul>
	 * 			<li>VLC</li>
	 * 				<ul>
	 * 					<li>recorded</li>
	 * 						<ul>
	 * 							<li>4959344545.mp4</li>
	 * 							<li>5093049239.mp4</li>
	 * 						</ul>
	 * 				</ul>
	 * 			<li>Office</li>
	 * 				<ul>
	 * 					<li>Excel</li>
	 * 						<ul>
	 * 							<li>excel.exe</li>
	 * 						</ul>
	 * 					<li>Word</li>
	 * 						<ul>
	 * 							<li>word.exe</li>
	 * 						</ul>
	 * 				</ul>
	 * 		</ul>
	 * 	<li>Devel</li>
	 * 		<ul>
	 * 			<li>sdk_dev</li>
	 * 				<ul>
	 * 					<li>jdk1.6</li>
	 * 				</ul>
	 * 			<li>ide</li>
	 * 				<ul>
	 * 					<li>eclipse</li>
	 * 						<ul>
	 * 							<li>eclipse.exe</li>
	 * 						</ul>
	 * 					<li>netbeans</li>
	 * 						<ul>
	 * 							<li>netbeans.exe</li>
	 * 						</ul>
	 * 				</ul>
	 * 			<li>projects</li>
	 * 				<ul>
	 * 					<li>happytree</li>
	 * 				</ul>
	 * 			<li>database</li>
	 * 		</ul>
	 * 	<li>Users</li>
	 * 		<ul>
	 * 			<li>diego</li>
	 * 				<ul>
	 * 					<li>tmp</li>
	 * 				</ul>
	 * 			<li>adminstrator</li>
	 * 		</ul>
	 * </ul>
	 * @return
	 */
	public static Collection<Directory> getDirectoryTree() {
		Collection<Directory> folders = new ArrayList<Directory>();
		
		/*
		 * Fist level.
		 */
		Directory programFiles = new Directory(42345, 0, "Program Files");
		Directory devel = new Directory(93832, 0, "Devel");
		Directory users = new Directory(38923, 0, "Users");
		
		/*
		 * Program Files.
		 */
		Directory adobe = new Directory(24935, 42345, "Adobe");
			Directory reader = new Directory(403940, 24935, "Reader");
				Directory readerExe = new Directory(8493845, 403940, "reader.exe");
			Directory photoshop = new Directory(909443, 24935, "Photoshop");
				Directory psExe = new Directory(4950243, 909443, "photoshop.exe");
			Directory dreamweaver = new Directory(502010, 24935, "Dremweaver");
				Directory dwExe = new Directory(8935844, 502010, "dreamweaver.exe");
		Directory realtek = new Directory(94034, 42345, "Realtek");
			Directory drivers = new Directory(220332, 94034, "drivers");
				Directory bin = new Directory(7753032, 220332, "bin");
			Directory sdk = new Directory(113009, 94034, "sdk");
				Directory files = new Directory(8484934, 113009, "files");
			Directory readme = new Directory(495833, 94034, "readme.txt");
		Directory winamp = new Directory(32099, 42345, "Winamp");
			Directory winampExe = new Directory(395524, 32099, "winamp.exe");
		Directory vlc = new Directory(10239, 42345, "VLC");
			Directory recorded = new Directory(848305, 10239, "recorded");
				Directory rec1 = new Directory(3840200, 848305, "4959344545.mp4");
				Directory rec2 = new Directory(1038299, 848305, "5093049239.mp4");
		Directory office = new Directory(53024, 42345, "Office");
			Directory excel = new Directory(843566, 53024, "Excel");
				Directory excelExe = new Directory(3964602, 843566, "excel.exe");
			Directory word = new Directory(674098, 53024, "Word");
				Directory wordExe = new Directory(4611329, 674098, "word.exe");
				
		/**
		 * Devel.
		 */
		Directory sdkDev = new Directory(84709, 93832, "sdk_dev");
			Directory jdk = new Directory(983533, 84709, "jdk1.6");
		Directory ide = new Directory(13823, 93832, "ide");
			Directory eclipse = new Directory(583852, 13823, "eclipse");
				Directory eclipseExe = new Directory(8483742, 583852, "eclipse.exe");
			Directory netbeans = new Directory(482043, 13823, "netbeans");
				Directory netbeansExe = new Directory(4859304, 482043, "netbeans.exe");
		Directory projects = new Directory(93209, 93832, "projects");
			Directory happytree = new Directory(859452, 93209, "happytree");
		Directory database = new Directory(45930, 93832, "database");
		
		/*
		 * Users.
		 */
		Directory foo = new Directory(48224, 38923, "foo");
			Directory tmp = new Directory(583950, 48224, "tmp");
		Directory administrator = new Directory(47592, 38923, "administrator");
		
		folders.add(eclipseExe);folders.add(winampExe);
		folders.add(rec2);folders.add(wordExe);
		folders.add(office);folders.add(adobe);
		folders.add(winamp);folders.add(readerExe);
		folders.add(readme);folders.add(rec1);
		folders.add(photoshop);folders.add(dwExe);
		folders.add(netbeans);folders.add(files);
		folders.add(drivers);folders.add(jdk);
		folders.add(netbeansExe);folders.add(psExe);
		folders.add(realtek);folders.add(excel);
		folders.add(database);folders.add(administrator);
		folders.add(recorded);folders.add(devel);
		folders.add(foo);folders.add(programFiles);
		folders.add(vlc);folders.add(dreamweaver);
		folders.add(projects);folders.add(users);
		folders.add(tmp);folders.add(happytree);
		folders.add(eclipse);folders.add(word);
		folders.add(sdk);folders.add(bin);
		folders.add(reader);folders.add(sdkDev);
		folders.add(ide);folders.add(excelExe);
		
		return folders;
	}
	
	public static Collection<Directory> getSimpleDirectoryTree() {
		Collection<Directory> folders = new ArrayList<Directory>();
		
		/*
		 * Fist level.
		 */
		Directory windows = new Directory(1, 0, "Windows");
		Directory system = new Directory(100, 1, "System");
		Directory system32 = new Directory(1000, 1, "System32");
		Directory drivers = new Directory(1076, 1000, "drivers");
		
		folders.add(system32);
		folders.add(windows);
		folders.add(drivers);
		folders.add(system);
		
		return folders;
	}
}
