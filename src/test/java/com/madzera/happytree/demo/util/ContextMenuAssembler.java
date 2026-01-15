package com.madzera.happytree.demo.util;

import java.util.Arrays;
import java.util.List;
import com.madzera.happytree.demo.model.EManagementContextMenu;

public class ContextMenuAssembler {

	public static List<EManagementContextMenu> getContextMenuListTree() {
		return Arrays.asList(EManagementContextMenu.values());
	}
}
