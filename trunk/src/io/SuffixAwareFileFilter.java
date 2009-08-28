package io;

import java.io.File;

abstract class SuffixAwareFilter extends javax.swing.filechooser.FileFilter
{
	public boolean accept(File f)
	{
		return f.isDirectory();
	}
}
