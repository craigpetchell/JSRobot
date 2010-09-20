package com.ephox.jsrobot;

import java.awt.datatransfer.*;
import java.io.*;

public class ClipboardHelper implements Transferable, ClipboardOwner {

	private DataFlavor flavor;
	private String content;
	
	public ClipboardHelper(String contentType, String content) throws ClassNotFoundException {
		this.flavor = new DataFlavor(contentType + ";class=java.lang.String");
		this.content = content;
	}
	
	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { flavor };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(this.flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (!flavor.equals(this.flavor)) {
			throw new UnsupportedFlavorException(flavor);
		}
		return content;
	}

}
