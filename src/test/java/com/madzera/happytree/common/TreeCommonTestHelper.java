package com.madzera.happytree.common;

import com.madzera.happytree.Element;
import com.madzera.happytree.demo.model.Directory;

public class TreeCommonTestHelper {
    
    protected TreeCommonTestHelper() {
    }
    

    @SuppressWarnings("unchecked")
	protected <T> void applyUpperCaseDirectoryName(Element<T> element) {
		Object obj = element.unwrap();
		if (obj != null) {
			Directory directory = (Directory) obj;
			directory.setName(directory.getName().toUpperCase());
			element.wrap((T) directory);
		}
	}

	protected <T> boolean directoryNameStartsWithPhoto(Element<T> element) {
		Object obj = element.unwrap();
		if (obj != null) {
			Directory directory = (Directory) obj;
			return directory.getName().startsWith("Photo")
					|| directory.getName().startsWith("photo");
		}
		return false;
	}
}