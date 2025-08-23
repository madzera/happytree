package com.madzera.happytree.demo.util;

import java.util.ArrayList;
import java.util.Collection;

import com.madzera.happytree.demo.model.Directory;
import com.madzera.happytree.demo.model.Metadata;

/**
 * This test class is used to assemble a tree of {@link Directory} and
 * {@link Metadata} objects.
 * 
 * <p>It consists of a tree that will be used as an example in the development
 * of test cases. The tree in question will be of objects of type
 * {@link Directory} and the objective is to simulate a directory structure in a
 * file system.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 */
public final class TreeAssembler {

	
	private TreeAssembler() {}
	
	
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
	 * 								<ul>
	 * 									<li>entry</li>
	 * 								</ul>
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
	Directory programFiles = new Directory(42345L, 0L, "Program Files");
	Directory devel = new Directory(93832L, 0L, "Devel");
	Directory users = new Directory(38923L, 0L, "Users");
		
		/*
		 * Program Files.
		 */
	Directory adobe = new Directory(24935L, 42345L, "Adobe");
			Directory reader = new Directory(403940L, 24935L, "Reader");
				Directory readerExe = new Directory(8493845L, 403940L, "reader.exe");
			Directory photoshop = new Directory(909443L, 24935L, "Photoshop");
				Directory psExe = new Directory(4950243L, 909443L, "photoshop.exe");
			Directory dreamweaver = new Directory(502010L, 24935L, "Dremweaver");
				Directory dwExe = new Directory(8935844L, 502010L, "dreamweaver.exe");
	Directory realtek = new Directory(94034L, 42345L, "Realtek");
			Directory drivers = new Directory(220332L, 94034L, "drivers");
				Directory bin = new Directory(7753032L, 220332L, "bin");
					/*
					 * This element has the same id than the another element in
					 * another tree (Simple Tree).
					 */
					Directory entry = new Directory(77530344L, 7753032L, "entry");
			Directory sdk = new Directory(113009L, 94034L, "sdk");
				Directory files = new Directory(8484934L, 113009L, "files");
			Directory readme = new Directory(495833L, 94034L, "readme.txt");
	Directory winamp = new Directory(32099L, 42345L, "Winamp");
			Directory winampExe = new Directory(395524L, 32099L, "winamp.exe");
	Directory vlc = new Directory(10239L, 42345L, "VLC");
			Directory recorded = new Directory(848305L, 10239L, "recorded");
				Directory rec1 = new Directory(3840200L, 848305L, "4959344545.mp4");
				Directory rec2 = new Directory(1038299L, 848305L, "5093049239.mp4");
		Directory office = new Directory(53024L, 42345L, "Office");
			Directory excel = new Directory(843566L, 53024L, "Excel");
				Directory excelExe = new Directory(3964602L, 843566L, "excel.exe");
			Directory word = new Directory(674098L, 53024L, "Word");
				Directory wordExe = new Directory(4611329L, 674098L, "word.exe");
				
		/**
		 * Devel.
		 */
		Directory sdkDev = new Directory(84709L, 93832L, "sdk_dev");
			Directory jdk = new Directory(983533L, 84709L, "jdk1.6");
		Directory ide = new Directory(13823L, 93832L, "ide");
			Directory eclipse = new Directory(583852L, 13823L, "eclipse");
				Directory eclipseExe = new Directory(8483742L, 583852L, "eclipse.exe");
			Directory netbeans = new Directory(482043L, 13823L, "netbeans");
				Directory netbeansExe = new Directory(4859304L, 482043L, "netbeans.exe");
		Directory projects = new Directory(93209L, 93832L, "projects");
			Directory happytree = new Directory(859452L, 93209L, "happytree");
		Directory database = new Directory(45930L, 93832L, "database");
		
		/*
		 * Users.
		 */
		Directory foo = new Directory(48224L, 38923L, "foo");
			Directory tmp = new Directory(583950L, 48224L, "tmp");
		Directory administrator = new Directory(47592L, 38923L, "administrator");
		
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
		folders.add(entry);
		
		return folders;
	}
	
	public static Collection<Directory> getSimpleDirectoryTree() {
		Collection<Directory> folders = new ArrayList<Directory>();
		
		/*
		 * First level.
		 */
		Directory windows = new Directory(1L, 0L, "Windows");
		Directory system = new Directory(100L, 1L, "System");
		Directory system32 = new Directory(1000L, 1L, "System32");
		Directory drivers = new Directory(1076L, 1000L, "drivers");
		//To simulate duplicate entries error (exists in both trees).
		Directory entry = new Directory(77530344L, 1076L, "Entry");
		
		folders.add(system32);
		folders.add(windows);
		folders.add(drivers);
		folders.add(system);
		folders.add(entry);
		
		return folders;
	}
	
	public static Collection<Metadata> getMetadataTree() {
		Collection<Metadata> metadata = new ArrayList<Metadata>();
		
		Metadata creation = new Metadata("creation", null, "Creation");
		Metadata permission = new Metadata("permission", null, "Permission");
		
		Metadata date = new Metadata("date", null, "Date");
		Metadata user = new Metadata("user", null, "User");
		
		Metadata type = new Metadata("type", null, "Type");
		Metadata owner = new Metadata("owner", null, "Owner");
		
		metadata.add(creation);
		metadata.add(type);
		metadata.add(user);
		metadata.add(permission);
		metadata.add(date);
		metadata.add(owner);
		
		return metadata;
	}
}
