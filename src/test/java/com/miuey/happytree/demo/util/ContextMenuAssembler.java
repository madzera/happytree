package com.miuey.happytree.demo.util;

import java.util.Arrays;
import java.util.List;

import com.miuey.happytree.demo.model.EManagementContextMenu;
import com.miuey.happytree.exception.TreeException;

public class ContextMenuAssembler {

	public static List<EManagementContextMenu> getContextMenuListTree()
			throws TreeException {
		return Arrays.asList(EManagementContextMenu.values());
		
	}
}
