package com.miuey.happytree.cases.context_menu;

import java.util.Arrays;
import java.util.List;

import com.miuey.happytree.exception.TreeException;

public class ContextMenuAssembler {

	public static List<EManagementContextMenu> getContextMenuListTree()
			throws TreeException {
		return Arrays.asList(EManagementContextMenu.values());
		
	}
}
