package com.madzera.happytree.demo.util;

import java.util.Arrays;
import java.util.List;

import com.madzera.happytree.demo.model.EManagementContextMenu;
import com.madzera.happytree.exception.TreeException;

public class ContextMenuAssembler {

	public static List<EManagementContextMenu> getContextMenuListTree()
			throws TreeException {
		return Arrays.asList(EManagementContextMenu.values());
	}
}
