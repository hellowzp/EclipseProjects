/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package editor;

import java.util.ResourceBundle;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;


/**
 * The example java editor plug-in class.
 * 
 * @since 3.0
 */
public class JavaScriptEditorPlugin extends AbstractUIPlugin {

	public final static String JAVA_PARTITIONING= "__java_example_partitioning"; 

	public static final String PLUGIN_ID= "org.eclipse.ui.examples.javaeditor"; 

	private static JavaScriptEditorPlugin fgInstance;

	private JSPartitionScanner fPartitionScanner;

	private ColorManager fColorProvider;
	
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("editor.ActionConfigure");

//	private JavaCodeScanner fCodeScanner;
//
//	private JavaDocScanner fDocScanner;

	/**
	 * Creates a new plug-in instance.
	 */
	public JavaScriptEditorPlugin() {
		fgInstance= this;
	}

	/**
	 * Returns the default plug-in instance.
	 * 
	 * @return the default plug-in instance
	 */
	public static JavaScriptEditorPlugin getDefault() {
		return fgInstance;
	}
	
	public static ResourceBundle getActionResourceBundle(){
		return resourceBundle;
	}

	/**
	 * Return a scanner for creating Java partitions.
	 * 
	 * @return a scanner for creating Java partitions
	 */
	public JSPartitionScanner getJavaPartitionScanner() {
		if (fPartitionScanner == null)
			fPartitionScanner= new JSPartitionScanner();
		return fPartitionScanner;
	}

	/**
	 * Returns the singleton Java code scanner.
	 * 
	 * @return the singleton Java code scanner
	 */
//	public RuleBasedScanner getJavaCodeScanner() {
//		if (fCodeScanner == null)
//			fCodeScanner= new JavaCodeScanner(getJavaColorProvider());
//		return fCodeScanner;
//	}

	/**
	 * Returns the singleton Java color provider.
	 * 
	 * @return the singleton Java color provider
	 */
	public ColorManager getJavaColorProvider() {
		if (fColorProvider == null)
			fColorProvider= new ColorManager();
		return fColorProvider;
	}

	/**
	 * Returns the singleton Javadoc scanner.
	 * 
	 * @return the singleton Javadoc scanner
	 */
//	public RuleBasedScanner getJavaDocScanner() {
//		if (fDocScanner == null)
//			fDocScanner= new JavaDocScanner(fColorProvider);
//		return fDocScanner;
//	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Java editor example: internal error", e)); //$NON-NLS-1$
	}

}
